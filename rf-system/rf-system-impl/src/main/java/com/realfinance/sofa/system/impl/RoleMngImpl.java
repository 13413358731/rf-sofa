package com.realfinance.sofa.system.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.realfinance.sofa.common.datascope.DataScopeUtils;
import com.realfinance.sofa.system.domain.*;
import com.realfinance.sofa.system.facade.RoleMngFacade;
import com.realfinance.sofa.system.model.*;
import com.realfinance.sofa.system.repository.MenuDataRuleRepository;
import com.realfinance.sofa.system.repository.MenuRepository;
import com.realfinance.sofa.system.repository.RoleRepository;
import com.realfinance.sofa.system.repository.UserRepository;
import com.realfinance.sofa.system.service.mapstruct.MenuDataRuleMapper;
import com.realfinance.sofa.system.service.mapstruct.RoleMapper;
import com.realfinance.sofa.system.service.mapstruct.RoleSaveMapper;
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
@SofaService(interfaceType = RoleMngFacade.class, uniqueId = "${service.rf-system.id}",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "jvm")
        })
@Transactional(readOnly = true)
public class RoleMngImpl implements RoleMngFacade {

    private static final Logger log = LoggerFactory.getLogger(RoleMngImpl.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final MenuRepository menuRepository;
    private final MenuDataRuleRepository menuDataRuleRepository;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final RoleSaveMapper roleSaveMapper;
    private final MenuDataRuleMapper menuDataRuleMapper;

    public RoleMngImpl(UserRepository userRepository,
                       RoleRepository roleRepository,
                       MenuRepository menuRepository,
                       MenuDataRuleRepository menuDataRuleRepository,
                       UserMapper userMapper,
                       RoleMapper roleMapper,
                       RoleSaveMapper roleSaveMapper,
                       MenuDataRuleMapper menuDataRuleMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.menuRepository = menuRepository;
        this.menuDataRuleRepository = menuDataRuleRepository;
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
        this.roleSaveMapper = roleSaveMapper;
        this.menuDataRuleMapper = menuDataRuleMapper;
    }

    @Override
    public Page<RoleDto> list(RoleQueryCriteria queryCriteria, Pageable pageable) {
        Objects.requireNonNull(pageable);
        if (!DataScopeUtils.loadSkipValidateTenantId()) {
            queryCriteria.setTenantId(DataScopeUtils.loadTenantId());
        }
        Page<RoleDto> result = roleRepository.findAll(toSpecification(queryCriteria), pageable)
                .map(roleMapper::toDto);
        return result;
    }

    @Override
    public Page<UserDto> listUsersById(Integer id, String filter, Pageable pageable) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(pageable);
        Optional<Role> role = roleRepository.findById(id);
        if (role.isEmpty()) {
            throw entityNotFound(Role.class,"id",id);
        }
        if (filter == null) {
            return userRepository.findByRoles(role.get(), pageable)
                    .map(userMapper::toDto);
        } else {
            return userRepository.findByRolesAndFilter(role.get(),
                    "%" + filter + "%" ,pageable)
                    .map(userMapper::toDto);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addUsers(Integer id, Set<Integer> userIds) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(userIds);
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> entityNotFound(Role.class,"id",id));
        Tenant tenant = role.getTenant();
        Set<User> users = role.getUsers();
        userRepository.findAllById(userIds).forEach(user -> {
            if (!Objects.equals(tenant.getId(), user.getTenant().getId())) {
                throw businessException("不能关联用户，userId：" + user.getId());
            }
            users.add(user);
        });
        try {
            roleRepository.saveAndFlush(role);
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
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> entityNotFound(Role.class,"id",id));
        Set<User> users = role.getUsers();
        users.removeIf(u -> userIds.contains(u.getId()));
        try {
            roleRepository.saveAndFlush(role);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("删除关联用户失败",e);
            }
            throw ExceptionUtils.businessException("删除关联用户失败：" + e.getMessage());
        }
    }

    @Override
    public Set<Integer> listMenuIdsById(Integer id) {
        Objects.requireNonNull(id);
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> entityNotFound(Role.class,"id",id));
        return role.getMenus().stream().map(Menu::getId).collect(Collectors.toSet());

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMenus(Integer id, Set<Integer> menuIds) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(menuIds);
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> entityNotFound(Role.class,"id",id));
        // 当前关联的菜单
        Set<Integer> currentMenuIds = role.getMenus().stream().map(Menu::getId).collect(Collectors.toSet());
        menuIds.stream()
                .filter(e -> !currentMenuIds.contains(e))
                .map(menuRepository::getOne)
                .forEach(role.getMenus()::add);
        role.getMenus().removeIf(next -> !menuIds.contains(next.getId()));
        try {
            roleRepository.saveAndFlush(role);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("更新关联菜单失败",e);
            }
            throw ExceptionUtils.businessException("更新关联菜单失败：" + e.getMessage());
        }
    }

    @Override
    public List<MenuDataRuleDto> listMenuDataRule(Integer menuId) {
        Objects.requireNonNull(menuId);
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> entityNotFound(Menu.class, "id", menuId));
        return menuDataRuleMapper.toDtoList(menuDataRuleRepository.findByMenu(menu));
    }

    @Override
    public Set<Integer> listMenuDataRuleIdsByIdAndMenuId(Integer id, Integer menuId) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(menuId);
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> entityNotFound(Role.class,"id",id));
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> entityNotFound(Menu.class, "id", menuId));
        // 角色关联菜单下的数据规则
        Set<Integer> currentMenuDataRuleIds =  role.getMenuDataRules().stream()
                .filter(e -> Objects.equals(menu, e.getMenu()))
                .map(MenuDataRule::getId).collect(Collectors.toSet());
        return currentMenuDataRuleIds;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMenuDataRules(Integer id, Integer menuId, Set<Integer> menuDataRuleIds) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(menuId);
        Objects.requireNonNull(menuDataRuleIds);
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> entityNotFound(Role.class,"id",id));
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> entityNotFound(Menu.class, "id", menuId));
        if (menu.getMenuDataRules() == null || menu.getMenuDataRules().isEmpty()) {
            return;
        }
        // 菜单下所有数据规则
        Set<Integer> allMenuDataRuleIds = menu.getMenuDataRules().stream().map(MenuDataRule::getId).collect(Collectors.toSet());
        // 传入参数与所有数据取交集
        menuDataRuleIds.retainAll(allMenuDataRuleIds);
        // 角色关联菜单下的数据规则
        Set<Integer> currentMenuDataRuleIds =  role.getMenuDataRules().stream()
                .filter(e -> Objects.equals(menu, e.getMenu()))
                .map(MenuDataRule::getId).collect(Collectors.toSet());
        // 添加新增的
        menuDataRuleIds.stream()
                .filter(e -> !currentMenuDataRuleIds.contains(e))
                .map(menuDataRuleRepository::getOne)
                .forEach(role.getMenuDataRules()::add);
        // 过滤删除的
        role.getMenuDataRules().removeIf(next -> !menuDataRuleIds.contains(next.getId()));
        try {
            roleRepository.saveAndFlush(role);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("更新关联菜单数据规则失败",e);
            }
            throw ExceptionUtils.businessException("更新关联菜单数据规则失败：" + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer save(RoleSaveDto saveDto) {
        Objects.requireNonNull(saveDto);
        checkTenantCanAccess(saveDto.getTenant());
        Role role;
        if (saveDto.getId() == null) { // 新增
            role = roleSaveMapper.toEntity(saveDto);
            // 检查角色编码是否已存在
            if (roleRepository.existsByTenantAndCode(role.getTenant(),role.getCode())) {
                throw businessException("角色编码已存在");
            }
        } else { // 修改
            role = roleRepository.findById(saveDto.getId())
                    .orElseThrow(() -> entityNotFound(Role.class,"id",saveDto.getId()));
            // 检查用户租户是否修改
            if (!role.getTenant().getId().equals(saveDto.getTenant())) {
                throw ExceptionUtils.businessException("Tenant不能修改");
            }
            // 如果编码修改，检查角色编码是否已存在
            if (!Objects.equals(saveDto.getCode(),role.getCode())
                    && roleRepository.existsByTenantAndCode(role.getTenant(),saveDto.getCode())) {
                throw businessException("角色编码已存在");
            }
            roleSaveMapper.updateEntity(role,saveDto);
        }

        try {
            Role saved = roleRepository.saveAndFlush(role);
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
        List<Role> toDelete = roleRepository.findAllById(ids).stream()
                .peek(e -> checkTenantCanAccess(e.getTenant().getId()))
                .collect(Collectors.toList());
        try {
            roleRepository.deleteAll(toDelete);
            roleRepository.flush();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("删除失败",e);
            }
            throw ExceptionUtils.businessException("删除失败：" + e.getMessage());
        }
    }
}