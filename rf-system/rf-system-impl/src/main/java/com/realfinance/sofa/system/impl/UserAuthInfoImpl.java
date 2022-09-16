package com.realfinance.sofa.system.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.realfinance.sofa.system.domain.Role;
import com.realfinance.sofa.system.domain.Tenant;
import com.realfinance.sofa.system.domain.User;
import com.realfinance.sofa.system.facade.UserAuthInfoFacade;
import com.realfinance.sofa.system.model.UserAuthInfoDto;
import com.realfinance.sofa.system.repository.MenuRepository;
import com.realfinance.sofa.system.repository.TenantRepository;
import com.realfinance.sofa.system.repository.UserRepository;
import com.realfinance.sofa.system.service.mapstruct.TenantSmallMapper;
import com.realfinance.sofa.system.service.mapstruct.UserMapper;
import com.realfinance.sofa.system.util.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.realfinance.sofa.system.util.ExceptionUtils.entityNotFound;

@Service
@SofaService(interfaceType = UserAuthInfoFacade.class, uniqueId = "${service.rf-system.id}",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "jvm")
        })
@Transactional(readOnly = true)
public class UserAuthInfoImpl implements UserAuthInfoFacade {

    private static final Logger log = LoggerFactory.getLogger(UserAuthInfoImpl.class);

    private final TenantRepository tenantRepository;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;
    private final UserMapper userMapper;
    private final TenantSmallMapper tenantSmallMapper;
    private final PasswordEncoder passwordEncoder;

    public UserAuthInfoImpl(TenantRepository tenantRepository,
                            UserRepository userRepository,
                            MenuRepository menuRepository,
                            UserMapper userMapper,
                            TenantSmallMapper tenantSmallMapper,
                            PasswordEncoder passwordEncoder) {
        this.tenantRepository = tenantRepository;
        this.userRepository = userRepository;
        this.menuRepository = menuRepository;
        this.userMapper = userMapper;
        this.tenantSmallMapper = tenantSmallMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserAuthInfoDto getUserAuthInfo(String tenantId, String username) {
        Objects.requireNonNull(tenantId);
        Objects.requireNonNull(username);
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> ExceptionUtils.entityNotFound(Tenant.class, "id", tenantId));
        if (!tenant.isValid()) {
            throw ExceptionUtils.businessException("租户[" + tenantId + "]不可用");
        }
        return userRepository.findUserAuthInfoGraph(username, tenant)
                .map(user -> {
                    // 查询用户所有启用的角色code
                    Set<Role> roles = Stream.concat(user.getRoles().stream(),
                            user.getRoleGroups().stream()
                                    .flatMap(e -> e.getRoles().stream()))
                            .filter(Role::getEnabled)
                            .collect(Collectors.toSet());
                    Set<String> roleCodes = roles.stream().map(Role::getCode).collect(Collectors.toSet());
                    // 查询菜单
                    Set<String> menuCodes = roles.isEmpty() ? Collections.emptySet()
                            : menuRepository.findMenuCodesByRoles(roles);
                    if (log.isTraceEnabled()) {
                        log.trace("查询用户认证信息, userId: {}, 角色编码: {}, 菜单编码: {}",
                                user.getId(),
                                String.join(",",roleCodes),
                                String.join(",",menuCodes));
                    }
                    //
                    UserAuthInfoDto userAuthInfoDto = new UserAuthInfoDto();
                    userAuthInfoDto.setTenant(tenantSmallMapper.toDto(tenant));
                    userAuthInfoDto.setUser(userMapper.toDto(user));
                    userAuthInfoDto.setPassword(user.getPassword());
                    userAuthInfoDto.setRoleCodes(roleCodes);
                    userAuthInfoDto.setMenuCodes(menuCodes);
                    return userAuthInfoDto;
                }).orElse(null);
    }

    @Override
    public Boolean checkPassword(String tenantId, String username, String password) {
        Objects.requireNonNull(tenantId);
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);
        User user = userRepository.findByTenantAndUsername(tenantRepository.getOne(tenantId),username)
                .orElseThrow(() -> entityNotFound(User.class, "username", username));
        return passwordEncoder.matches(password,user.getPassword());
    }
}
