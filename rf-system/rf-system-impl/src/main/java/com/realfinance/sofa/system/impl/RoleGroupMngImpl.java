package com.realfinance.sofa.system.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.realfinance.sofa.common.datascope.DataScopeUtils;
import com.realfinance.sofa.system.domain.Role;
import com.realfinance.sofa.system.domain.RoleGroup;
import com.realfinance.sofa.system.domain.Tenant;
import com.realfinance.sofa.system.domain.User;
import com.realfinance.sofa.system.facade.RoleGroupMngFacade;
import com.realfinance.sofa.system.model.*;
import com.realfinance.sofa.system.repository.RoleGroupRepository;
import com.realfinance.sofa.system.repository.RoleRepository;
import com.realfinance.sofa.system.repository.UserRepository;
import com.realfinance.sofa.system.service.mapstruct.RoleGroupMapper;
import com.realfinance.sofa.system.service.mapstruct.RoleGroupSaveMapper;
import com.realfinance.sofa.system.service.mapstruct.RoleMapper;
import com.realfinance.sofa.system.service.mapstruct.UserMapper;
import com.realfinance.sofa.system.util.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.realfinance.sofa.common.datascope.DataScopeUtils.checkTenantCanAccess;
import static com.realfinance.sofa.system.util.ExceptionUtils.businessException;
import static com.realfinance.sofa.system.util.ExceptionUtils.entityNotFound;
import static com.realfinance.sofa.system.util.QueryCriteriaUtils.toSpecification;

@Service
@SofaService(interfaceType = RoleGroupMngFacade.class, uniqueId = "${service.rf-system.id}",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "jvm")
        })
@Transactional(readOnly = true)
public class RoleGroupMngImpl implements RoleGroupMngFacade {

    private static final Logger log = LoggerFactory.getLogger(RoleGroupMngImpl.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RoleGroupRepository roleGroupRepository;
    private final RoleGroupMapper roleGroupMapper;
    private final RoleGroupSaveMapper roleGroupSaveMapper;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;

    public RoleGroupMngImpl(UserRepository userRepository,
                            RoleRepository roleRepository,
                            RoleGroupRepository roleGroupRepository,
                            RoleGroupMapper roleGroupMapper,
                            RoleGroupSaveMapper roleGroupSaveMapper,
                            UserMapper userMapper,
                            RoleMapper roleMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.roleGroupRepository = roleGroupRepository;
        this.roleGroupMapper = roleGroupMapper;
        this.roleGroupSaveMapper = roleGroupSaveMapper;
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
    }

    @Override
    public Page<RoleGroupDto> list(RoleGroupQueryCriteria queryCriteria, Pageable pageable) {
        Objects.requireNonNull(pageable);
        if (!DataScopeUtils.loadSkipValidateTenantId()) {
            queryCriteria.setTenantId(DataScopeUtils.loadTenantId());
        }
        Page<RoleGroupDto> result = roleGroupRepository.findAll(toSpecification(queryCriteria), pageable)
                .map(roleGroupMapper::toDto);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer save(RoleGroupSaveDto saveDto) {
        Objects.requireNonNull(saveDto);
        checkTenantCanAccess(saveDto.getTenant());
        RoleGroup roleGroup;
        if (saveDto.getId() == null) { // 新增
            roleGroup = roleGroupSaveMapper.toEntity(saveDto);
            // 检查角色组编码是否已存在
            if (roleGroupRepository.existsByTenantAndCode(roleGroup.getTenant(),roleGroup.getCode())) {
                throw businessException("角色组编码已存在");
            }
        } else { // 修改
            roleGroup = roleGroupRepository.findById(saveDto.getId())
                    .orElseThrow(() -> entityNotFound(RoleGroup.class, "id", saveDto.getId()));
            // 检查用户租户是否修改
            if (!roleGroup.getTenant().getId().equals(saveDto.getTenant())) {
                throw ExceptionUtils.businessException("Tenant不能修改");
            }
            // 如果编码改变，检查角色组编码是否已存在
            if (!Objects.equals(saveDto.getCode(),roleGroup.getCode())
                    && roleGroupRepository.existsByTenantAndCode(roleGroup.getTenant(),saveDto.getCode())) {
                throw businessException("角色组编码已存在");
            }
            roleGroupSaveMapper.updateEntity(roleGroup,saveDto);
        }

        try {
            RoleGroup saved = roleGroupRepository.saveAndFlush(roleGroup);
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
    public void delete(Set<Integer> ids) {
        Objects.requireNonNull(ids);
        List<RoleGroup> toDelete = roleGroupRepository.findAllById(ids).stream()
                .peek(e -> checkTenantCanAccess(e.getTenant().getId()))
                .collect(Collectors.toList());
        try {
            roleGroupRepository.deleteAll(toDelete);
            roleGroupRepository.flush();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("删除失败",e);
            }
            throw ExceptionUtils.businessException("删除失败：" + e.getMessage());
        }
    }

    @Override
    public Page<UserDto> listUsersById(Integer id, String filter, Pageable pageable) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(pageable);
        Optional<RoleGroup> roleGroup = roleGroupRepository.findById(id);
        if (roleGroup.isEmpty()) {
            throw entityNotFound(RoleGroup.class,"id",id);
        }
        if (filter == null) {
            return userRepository.findByRoleGroups(roleGroup.get(), pageable)
                    .map(userMapper::toDto);
        } else {
            return userRepository.findByRoleGroupsAndFilter(roleGroup.get(),
                    "%" + filter + "%", pageable)
                    .map(userMapper::toDto);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addUsers(Integer id, Set<Integer> userIds) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(userIds);
        RoleGroup roleGroup = roleGroupRepository.findById(id)
                .orElseThrow(() -> entityNotFound(RoleGroup.class, "id", id));
        Tenant tenant = roleGroup.getTenant();
        Set<User> users = roleGroup.getUsers();
        userRepository.findAllById(userIds).forEach(user -> {
            if (!Objects.equals(tenant.getId(), user.getTenant().getId())) {
                throw businessException("不能关联用户，userId：" + user.getId());
            }
            users.add(user);
        });
        try {
            roleGroupRepository.saveAndFlush(roleGroup);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("关联用户失败",e);
            }
            throw ExceptionUtils.businessException("关联用户失败：" + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeUsers(Integer id, Set<Integer> userIds) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(userIds);
        RoleGroup roleGroup = roleGroupRepository.findById(id)
                .orElseThrow(() -> entityNotFound(RoleGroup.class, "id", id));
        Set<User> users = roleGroup.getUsers();
        users.removeIf(u -> userIds.contains(u.getId()));
        try {
            roleGroupRepository.saveAndFlush(roleGroup);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("删除关联用户失败",e);
            }
            throw ExceptionUtils.businessException("删除关联用户失败：" + e.getMessage());
        }
    }

    @Override
    public Page<RoleDto> listRolesById(Integer id, String filter, Pageable pageable) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(pageable);
        Optional<RoleGroup> roleGroup = roleGroupRepository.findById(id);
        if (roleGroup.isEmpty()) {
            throw entityNotFound(RoleGroup.class,"id",id);
        }
        if (filter == null) {
            return roleRepository.findByRoleGroups(roleGroup.get(), pageable)
                    .map(roleMapper::toDto);
        } else {
            return roleRepository.findByRoleGroupsAndFilter(roleGroup.get(),
                    "%" + filter + "%", pageable)
                    .map(roleMapper::toDto);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addRoles(Integer id, Set<Integer> roleIds) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(roleIds);
        RoleGroup roleGroup = roleGroupRepository.findById(id)
                .orElseThrow(() -> entityNotFound(RoleGroup.class, "id", id));
        Tenant tenant = roleGroup.getTenant();
        Set<Role> roles = roleGroup.getRoles();
        roleRepository.findAllById(roleIds).forEach(role -> {
            if (!Objects.equals(tenant.getId(), role.getTenant().getId())) {
                throw businessException("不能关联角色，roleId：" + role.getId());
            }
            roles.add(role);
        });
        try {
            roleGroupRepository.saveAndFlush(roleGroup);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("关联角色失败",e);
            }
            throw ExceptionUtils.businessException("关联角色失败：" + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeRoles(Integer id, Set<Integer> roleIds) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(roleIds);
        RoleGroup roleGroup = roleGroupRepository.findById(id)
                .orElseThrow(() -> entityNotFound(RoleGroup.class, "id", id));
        Set<Role> roles = roleGroup.getRoles();
        roles.removeIf(r -> roleIds.contains(r.getId()));
        try {
            roleGroupRepository.saveAndFlush(roleGroup);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("删除关联角色失败",e);
            }
            throw ExceptionUtils.businessException("删除关联角色失败：" + e.getMessage());
        }
    }


}
