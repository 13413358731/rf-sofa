package com.realfinance.sofa.system.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.realfinance.sofa.common.datascope.DataScopeUtils;
import com.realfinance.sofa.system.domain.User;
import com.realfinance.sofa.system.facade.UserMngFacade;
import com.realfinance.sofa.system.model.UserDetailsDto;
import com.realfinance.sofa.system.model.UserDto;
import com.realfinance.sofa.system.model.UserQueryCriteria;
import com.realfinance.sofa.system.model.UserSaveDto;
import com.realfinance.sofa.system.repository.UserRepository;
import com.realfinance.sofa.system.service.mapstruct.UserDetailsMapper;
import com.realfinance.sofa.system.service.mapstruct.UserMapper;
import com.realfinance.sofa.system.service.mapstruct.UserSaveMapper;
import com.realfinance.sofa.system.util.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.realfinance.sofa.common.datascope.DataScopeUtils.checkTenantCanAccess;
import static com.realfinance.sofa.system.util.ExceptionUtils.entityNotFound;
import static com.realfinance.sofa.system.util.QueryCriteriaUtils.toSpecification;

@Service
@SofaService(interfaceType = UserMngFacade.class, uniqueId = "${service.rf-system.id}",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "jvm")
        })
@Transactional(readOnly = true)
public class UserMngImpl implements UserMngFacade {

    private static final Logger log = LoggerFactory.getLogger(UserMngImpl.class);

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserSaveMapper userSaveMapper;
    private final UserDetailsMapper userDetailsMapper;
    private final PasswordEncoder passwordEncoder;

    public UserMngImpl(UserRepository userRepository,
                       UserMapper userMapper,
                       UserSaveMapper userSaveMapper,
                       UserDetailsMapper userDetailsMapper,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userSaveMapper = userSaveMapper;
        this.userDetailsMapper = userDetailsMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Page<UserDto> list(UserQueryCriteria queryCriteria, Pageable pageable) {
        Objects.requireNonNull(pageable);
        if (!DataScopeUtils.loadSkipValidateTenantId()) {
            queryCriteria.setTenantId(DataScopeUtils.loadTenantId());
        }
        Page<User> result = userRepository.findAll(toSpecification(queryCriteria), pageable);
        System.out.println(result);

        return result.map(userMapper::toDto);
    }

    @Override
    public UserDetailsDto getDetailsById(Integer id) {
        Objects.requireNonNull(id);
        // TODO: 2020/10/29 这个查询需要优化，使用EntityGraph优化
        Optional<User> entity = userRepository.findById(id);
        entity.ifPresent(user -> checkTenantCanAccess(user.getTenant().getId()));
        return entity.map(userDetailsMapper::toDto).orElse(null);
    }

    @Override
    public List<UserDetailsDto> getDetailsByIds(Set<Integer> ids) {
        List<User> allById = userRepository.findAllById(ids);
        return userDetailsMapper.toDtoList(allById);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer save(UserSaveDto saveDto) {
        Objects.requireNonNull(saveDto);
        checkTenantCanAccess(saveDto.getTenant());

        User user;
        if (saveDto.getId() == null) { // 新增
            user = userSaveMapper.toEntity(saveDto);
            // 检查用户名是否已存在
            if (userRepository.existsByTenantAndUsername(user.getTenant(),user.getUsername())) {
                throw ExceptionUtils.businessException("用户名已存在");
            }
            user.setPassword(passwordEncoder.encode("123456")); // TODO 默认密码
        } else { // 修改
            user = userRepository.findById(saveDto.getId())
                    .orElseThrow(() -> entityNotFound(User.class,"id",saveDto.getId()));
            // 检查用户租户是否修改
            if (!user.getTenant().getId().equals(saveDto.getTenant())) {
                throw ExceptionUtils.businessException("Tenant不能修改");
            }
            // 如果用户名修改，检查用户名是否已存在
            if (!Objects.equals(saveDto.getUsername(),user.getUsername())
                    && userRepository.existsByTenantAndUsername(user.getTenant(),saveDto.getUsername())) {
                throw ExceptionUtils.businessException("用户名已存在");
            }
            userSaveMapper.updateEntity(user,saveDto);
        }
        // 检查用户的租户和用户所属部门租户是否相等
        if (user.getDepartment() != null && user.getDepartment().getTenant() != user.getTenant()) {
            throw ExceptionUtils.businessException("必须关联相同Tenant的部门");
        }
        try {
            User saved = userRepository.saveAndFlush(user);
            return saved.getId();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("保存失败",e);
            }
            throw ExceptionUtils.businessException("保存失败：" + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(Integer id, String password, String newPassword, String newPassword2) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(newPassword);
        Objects.requireNonNull(newPassword2);
        if (newPassword.length() < 6 || newPassword.length() > 20) {
            throw ExceptionUtils.businessException("密码长度必须在[6,20]范围内");
        }
        if (!newPassword.equals(newPassword2)) {
            throw ExceptionUtils.businessException("两次密码不相同");
        }
        Optional<User> entity = userRepository.findById(id);
        if (entity.isEmpty()) {
            throw entityNotFound(User.class,"id",id);
        }
        entity.ifPresent(user -> {
            if (password != null && !passwordEncoder.matches(password, user.getPassword())) {
                throw ExceptionUtils.businessException("旧密码错误");
            }
            if (passwordEncoder.matches(newPassword, user.getPassword())) {
                throw ExceptionUtils.businessException("新密码与旧密码相同");
            }
            user.setPassword(passwordEncoder.encode(newPassword));
            try {
                userRepository.saveAndFlush(user);
            } catch (Exception e) {
                if (log.isErrorEnabled()) {
                    log.error("修改密码失败",e);
                }
                throw ExceptionUtils.businessException("修改密码失败：" + e.getMessage());
            }
        });
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Integer> ids) {
        Objects.requireNonNull(ids);
        List<User> toDelete = userRepository.findAllById(ids).stream()
                .peek(e -> checkTenantCanAccess(e.getTenant().getId()))
                .collect(Collectors.toList());
        try {
            userRepository.deleteAll(toDelete);
            userRepository.flush();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("删除失败",e);
            }
            throw ExceptionUtils.businessException("删除失败：" + e.getMessage());
        }
    }
}
