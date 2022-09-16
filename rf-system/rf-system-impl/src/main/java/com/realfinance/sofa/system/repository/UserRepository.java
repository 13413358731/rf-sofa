package com.realfinance.sofa.system.repository;

import com.realfinance.sofa.system.domain.Role;
import com.realfinance.sofa.system.domain.RoleGroup;
import com.realfinance.sofa.system.domain.Tenant;
import com.realfinance.sofa.system.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepositoryImplementation<User, Integer> {

    /**
     * 查询用户授权信息视图
     * 关联查询角色和关联用户组角色
     * @param username
     * @return
     */
    @EntityGraph(value = "User{tenant,department,roles,roleGroups{roles}}", type = EntityGraph.EntityGraphType.FETCH)
    @Query("SELECT u FROM User u WHERE u.username = :username AND u.tenant = :tenant")
    Optional<User> findUserAuthInfoGraph(String username, Tenant tenant);

    @EntityGraph(value = "User{roles,roleGroups{roles}}", type = EntityGraph.EntityGraphType.FETCH)
    @Query("SELECT u FROM User u WHERE u.id = :id")
    Optional<User> findUserWithRolesAndRoleGroups(Integer id);

    Optional<User> findByTenantAndUsername(Tenant tenant, String username);

    Page<User> findByRoles(Role roles, Pageable pageable);
    @Query("SELECT u FROM User u JOIN u.roles r WHERE r = :roles AND (u.username LIKE :filter OR u.realname LIKE :filter)")
    Page<User> findByRolesAndFilter(Role roles, String filter, Pageable pageable);
    Page<User> findByRoleGroups(RoleGroup roleGroups, Pageable pageable);
    @Query("SELECT u FROM User u JOIN u.roleGroups rg WHERE rg = :roleGroups AND (u.username LIKE :filter OR u.realname LIKE :filter)")
    Page<User> findByRoleGroupsAndFilter(RoleGroup roleGroups, String filter, Pageable pageable);

    boolean existsByTenantAndUsername(Tenant tenant, String username);

    boolean existsByIdAndDepartmentIdIn(Integer id, Collection<Integer> departmentIds);

    // TODO: 2021/3/2 使用了视图后 查询时有left join会导致limit 出现问题（排序问题，分页数据不是整体按顺序的，翻页可能出现重复数据） 使用分页需要指定order by
    @Override
    @EntityGraph(value = "User{tenant,department}", type = EntityGraph.EntityGraphType.FETCH)
    Page<User> findAll(Specification<User> spec, Pageable pageable);

    @Override
    @EntityGraph(value = "User{tenant,department}", type = EntityGraph.EntityGraphType.FETCH)
    Page<User> findAll(Pageable pageable);
}
