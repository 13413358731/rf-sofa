package com.realfinance.sofa.system.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.realfinance.sofa.common.datascope.DataScopeUtils;
import com.realfinance.sofa.system.domain.*;
import com.realfinance.sofa.system.facade.SystemQueryFacade;
import com.realfinance.sofa.system.model.*;
import com.realfinance.sofa.system.repository.*;
import com.realfinance.sofa.system.service.mapstruct.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.realfinance.sofa.system.util.ExceptionUtils.businessException;
import static com.realfinance.sofa.system.util.ExceptionUtils.entityNotFound;
import static com.realfinance.sofa.system.util.QueryCriteriaUtils.toSpecification;

@Service
@SofaService(interfaceType = SystemQueryFacade.class, uniqueId = "${service.rf-system.id}",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "jvm")
        })
@Transactional(readOnly = true)
public class SystemQueryImpl implements SystemQueryFacade {

    private static final Logger log = LoggerFactory.getLogger(SystemQueryImpl.class);

    private final JdbcTemplate jdbcTemplate;
    private final EntityManager entityManager;
    private final TenantRepository tenantRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RoleGroupRepository roleGroupRepository;
    private final MenuRepository menuRepository;
    private final DepartmentRepository departmentRepository;
    private final MenuDataRuleRepository menuDataRuleRepository;
    private final UserSmallMapper userSmallMapper;
    private final UserMapper userMapper;
    private final RoleSmallMapper roleSmallMapper;
    private final MenuMapper menuMapper;
    private final TenantSmallMapper tenantSmallMapper;
    private final DepartmentSmallMapper departmentSmallMapper;
    private final DepartmentSmallTreeMapper departmentSmallTreeMapper;
    private final MenuSmallTreeMapper menuSmallTreeMapper;
    private final MenuDataRuleSmallMapper menuDataRuleSmallMapper;
    private final TenantMapper tenantMapper;

    public SystemQueryImpl(JdbcTemplate jdbcTemplate,
                           EntityManager entityManager,
                           TenantRepository tenantRepository,
                           UserRepository userRepository,
                           RoleRepository roleRepository,
                           RoleGroupRepository roleGroupRepository,
                           MenuRepository menuRepository,
                           DepartmentRepository departmentRepository,
                           MenuDataRuleRepository menuDataRuleRepository,
                           UserSmallMapper userSmallMapper,
                           UserMapper userMapper,
                           RoleSmallMapper roleSmallMapper,
                           MenuMapper menuMapper,
                           TenantSmallMapper tenantSmallMapper,
                           DepartmentSmallMapper departmentSmallMapper,
                           DepartmentSmallTreeMapper departmentSmallTreeMapper,
                           MenuSmallTreeMapper menuSmallTreeMapper,
                           MenuDataRuleSmallMapper menuDataRuleSmallMapper,
                           TenantMapper tenantMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.entityManager = entityManager;
        this.tenantRepository = tenantRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.roleGroupRepository = roleGroupRepository;
        this.menuRepository = menuRepository;
        this.departmentRepository = departmentRepository;
        this.menuDataRuleRepository = menuDataRuleRepository;
        this.userSmallMapper = userSmallMapper;
        this.userMapper = userMapper;
        this.roleSmallMapper = roleSmallMapper;
        this.menuMapper = menuMapper;
        this.tenantSmallMapper = tenantSmallMapper;
        this.departmentSmallMapper = departmentSmallMapper;
        this.departmentSmallTreeMapper = departmentSmallTreeMapper;
        this.menuSmallTreeMapper = menuSmallTreeMapper;
        this.menuDataRuleSmallMapper = menuDataRuleSmallMapper;
        this.tenantMapper = tenantMapper;
    }

    @Override
    public List<TenantDto> queryTenants() {
        LocalDateTime now = LocalDateTime.now();
        List<Tenant> tenants = tenantRepository.findByStartTimeBeforeAndEndTimeAfterAndEnabled(now, now, Boolean.TRUE);
        return tenantMapper.toDtoList(tenants);
    }


    @Override
    public List<MenuDto> queryMenus() {
        List<Menu> all = menuRepository.findAll(Sort.by("sort"));
        return menuMapper.toDtoList(all);
    }

    @Override
    public List<Integer> queryDepartmentIdPath(Integer departmentId) {
        Objects.requireNonNull(departmentId);
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> entityNotFound(Department.class, "id", departmentId));
        String codePath = department.getCodePath();
        if (codePath == null) {
            if (log.isErrorEnabled()) {
                log.error("Department[{}] codePath is null.", department.getId());
            }
            throw businessException("部门数据异常，部门ID：" + department.getId());
        }
        String[] codes = codePath.split("/");
        return departmentRepository.findByTenantAndCodeIn(department.getTenant(), Arrays.asList(codePath.split("/")))
                .stream()
                // 重新按codes数组的顺序排序
                .sorted(Comparator.comparing(e -> {
                    for (int i = 0; i < codes.length; i++) {
                        if (codes[i].equals(e.getCode())) {
                            return i;
                        }
                    }
                    throw businessException("部门数据异常，部门ID：" + departmentId);
                }))
                .map(Department::getId)
                .collect(Collectors.toList());
    }

    @Override
    public UserSmallDto queryUserSmallDto(Integer userId) {
        Objects.requireNonNull(userId);
        return userRepository.findById(userId)
                .map(userSmallMapper::toDto)
                .orElse(null);
    }

    @Override
    public TenantSmallDto queryTenantSmallDto(String tenantId) {
        Objects.requireNonNull(tenantId);
        return tenantRepository.findById(tenantId)
                .map(tenantSmallMapper::toDto)
                .orElse(null);
    }

    @Override
    public DepartmentSmallDto queryDepartmentSmallDto(Integer departmentId) {
        Objects.requireNonNull(departmentId);
        return departmentRepository.findById(departmentId)
                .map(departmentSmallMapper::toDto)
                .orElse(null);
    }

    @Override
    public RoleSmallDto queryRoleSmallDto(Integer roleId) {
        Objects.requireNonNull(roleId);
        return roleRepository.findById(roleId)
                .map(roleSmallMapper::toDto)
                .orElse(null);
    }

    @Override
    public Page<TenantSmallDto> queryTenantRefer(TenantQueryCriteria queryCriteria, Pageable pageable) {
        Objects.requireNonNull(pageable);
        if (!DataScopeUtils.loadSkipValidateTenantId()) {
            queryCriteria.setId(DataScopeUtils.loadTenantId());
        }
        return tenantRepository.findAll(toSpecification(queryCriteria), pageable).map(tenantSmallMapper::toDto);
    }

    @Override
    public Page<UserDto> queryUserRefer(UserQueryCriteria queryCriteria, Pageable pageable) {
        Objects.requireNonNull(pageable);
        if (!DataScopeUtils.loadSkipValidateTenantId()) {
            queryCriteria.setTenantId(DataScopeUtils.loadTenantId());
        }
        return userRepository.findAll(toSpecification(queryCriteria), pageable).map(userMapper::toDto);
    }

    @Override
    public Page<UserDto> queryAllUsersRefer(UserQueryCriteria queryCriteria, Pageable pageable) {
        Objects.requireNonNull(pageable);
        /*if (!DataScopeUtils.loadSkipValidateTenantId()) {
            queryCriteria.setTenantId(DataScopeUtils.loadTenantId());
        }*/
        return userRepository.findAll(toSpecification(queryCriteria), pageable).map(userMapper::toDto);
    }

    @Override
    public Page<RoleSmallDto> queryRoleRefer(RoleQueryCriteria queryCriteria, Pageable pageable) {
        Objects.requireNonNull(pageable);
        if (!DataScopeUtils.loadSkipValidateTenantId()) {
            queryCriteria.setTenantId(DataScopeUtils.loadTenantId());
        }
        return roleRepository.findAll(toSpecification(queryCriteria), pageable).map(roleSmallMapper::toDto);
    }

    @Override
    public List<DepartmentSmallTreeDto> queryDepartmentRefer() {
        // 查询
        Department example = new Department();
        example.setEnabled(true);
        if (!DataScopeUtils.loadSkipValidateTenantId()) {
            // 非系统租户只能查询当前租户下的部门树
            example.setTenant(tenantRepository.getOne(DataScopeUtils.loadTenantId()));
        }
        List<Department> all = departmentRepository.findAll(Example.of(example), Sort.by("sort"));
        // 转换树结构
        Map<Integer, DepartmentSmallTreeDto> dtoMap = all.stream().map(departmentSmallTreeMapper::toDto)
                .collect(Collectors.toMap(DepartmentSmallDto::getId, e -> e));
        List<DepartmentSmallTreeDto> result = new ArrayList<>();
        for (Department department : all) {
            if (department.getType() == Department.Type.FIRST_LEVEL) {
                result.add(dtoMap.get(department.getId()));
            }
            if (department.getParent() != null) {
                DepartmentSmallTreeDto parent = dtoMap.get(department.getParent().getId());
                if (parent != null) {
                    parent.getChildren().add(dtoMap.get(department.getId()));
                }
            }
        }
        return result;
    }

    @Override
    public Page<DepartmentSmallDto> queryDepartmentRefer(DepartmentQueryCriteria queryCriteria, Pageable pageable) {
        Objects.requireNonNull(pageable);
        if (!DataScopeUtils.loadSkipValidateTenantId()) {
            queryCriteria.setTenantId(DataScopeUtils.loadTenantId());
        }
        return departmentRepository.findAll(toSpecification(queryCriteria), pageable).map(departmentSmallMapper::toDto);
    }

    @Override
    public MenuDataRules queryMenuDataRules(String tenantId, Collection<String> roleCodes, String menuCode) {
        Objects.requireNonNull(tenantId);
        Objects.requireNonNull(roleCodes);
        Objects.requireNonNull(menuCode);
        // 查询菜单
        Menu menu = menuRepository.findByCode(menuCode)
                .orElseThrow(() -> entityNotFound(Menu.class, "code", menuCode));
        if (!menu.getHasMenuDataRule()) {
            // 如果菜单下没有数据规则，短路返回
            return new MenuDataRules(menuCode, false);
        }
        // 查询角色
        Set<Role> roles = roleRepository.findByTenantAndCodeInAndEnabled(tenantRepository.getOne(tenantId), roleCodes, true);
        // TODO: 2020/12/9 如果角色太多，这里循环查询次数可能影响效率
        MenuDataRules result = new MenuDataRules(menuCode, true);
        for (Role role : roles) {
            Set<MenuDataRule> enabledMenuDataRules = menuDataRuleRepository.findByRolesAndMenuAndEnabled(role, menu, true);
            Set<MenuDataRuleSmallDto> rules = menuDataRuleSmallMapper.toDtoSet(enabledMenuDataRules);
            result.addRoleRules(new MenuDataRules.RoleRules(role.getCode(), rules));
        }
        if (log.isDebugEnabled()) {
            log.debug("查询数据规则，tenantId：{}，roleCodes：{}, menuCode: {}，result：{}",
                    tenantId, roleCodes, menuCode, result);
        }
        return result.optimize();
    }

    @Override
    public Boolean checkUsernameExist(String tenantId, String username) {
        Objects.requireNonNull(tenantId);
        Objects.requireNonNull(username);
        return userRepository.existsByTenantAndUsername(tenantRepository.getOne(tenantId), username);
    }

    @Override
    public Boolean checkRoleCodeExist(String tenantId, String roleCode) {
        Objects.requireNonNull(tenantId);
        Objects.requireNonNull(roleCode);
        return roleRepository.existsByTenantAndCode(tenantRepository.getOne(tenantId), roleCode);
    }

    @Override
    public Boolean checkRoleGroupCodeExist(String tenantId, String roleGroupCode) {
        Objects.requireNonNull(tenantId);
        Objects.requireNonNull(roleGroupCode);
        return roleGroupRepository.existsByTenantAndCode(tenantRepository.getOne(tenantId), roleGroupCode);
    }

    @Override
    public Boolean checkTenantIdExist(String tenantId) {
        Objects.requireNonNull(tenantId);
        return tenantRepository.existsById(tenantId);
    }

    @Override
    public Boolean checkDepartmentCodeExist(String tenantId, String departmentCode) {
        Objects.requireNonNull(tenantId);
        Objects.requireNonNull(departmentCode);
        return departmentRepository.existsByTenantAndCode(tenantRepository.getOne(tenantId), departmentCode);
    }

    @Override
    public Boolean checkMenuCodeExist(String menuCode) {
        Objects.requireNonNull(menuCode);
        return menuRepository.existsByCode(menuCode);
    }

    @Override
    public Boolean checkUserInDepartment(Integer userId, Collection<Integer> departmentIds) {
        Objects.requireNonNull(userId);
        Objects.requireNonNull(departmentIds);
        Integer departmentId = userRepository.findById(userId)
                .map(User::getDepartment)
                .map(Department::getId).orElse(null);
        if (departmentId == null) {
            return false;
        }
        return departmentIds.contains(departmentId);
    }

    @Override
    public Boolean checkUserInRole(Integer userId, Collection<Integer> roleIds) {
        Objects.requireNonNull(userId);
        Objects.requireNonNull(roleIds);
        User user = userRepository.findUserWithRolesAndRoleGroups(userId).orElse(null);
        if (user == null) {
            return false;
        }
        Set<Integer> roles = Stream.concat(user.getRoles().stream(),
                user.getRoleGroups().stream()
                        .flatMap(e -> e.getRoles().stream()))
                .filter(Role::getEnabled)
                .map(Role::getId)
                .collect(Collectors.toSet());
        if (roles.isEmpty()) {
            return false;
        }
        return roleIds.stream().anyMatch(roles::contains);
    }

    @Override
    public Collection<Integer> findRoleCodeInToUserIdIn(Collection<String> roleCodeIn) {
        List<Set<User>> users = new ArrayList<>();
        List<Role> roles = roleRepository.findByCodeIn(roleCodeIn);
        users.addAll(roleGroupRepository.findByRolesIn(roles).stream().map(RoleGroup::getUsers).collect(Collectors.toList()));
        users.addAll(roles.stream().map(Role::getUsers).collect(Collectors.toList()));

        List<Integer> ids = new ArrayList<>();
        for (Set<User> user : users) {
            ids.addAll(user.stream().map(User::getId).collect(Collectors.toList()));
        }
        return ids;
    }

    @Override
    public Collection<Integer> findDepartmentCodeInToUserIdIn(@NotNull Collection<String> departmentCodeIn) {
        List<Department> departments = departmentRepository.findByCodeIn(departmentCodeIn);
        List<Set<User>> users = departments.stream().map(Department::getUsers).collect(Collectors.toList());
        List<Integer> ids = new ArrayList<>();
        for (Set<User> user : users) {
            ids.addAll(user.stream().map(User::getId).collect(Collectors.toList()));
        }
        return ids;
    }

    @Override
    public String findDepartmentCodeToUserId(@NotNull Integer userId) {
        return userRepository.findById(userId).get().getDepartment().getCode();
    }
}
