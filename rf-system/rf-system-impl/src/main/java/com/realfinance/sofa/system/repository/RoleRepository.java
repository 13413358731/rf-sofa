package com.realfinance.sofa.system.repository;

import com.realfinance.sofa.system.domain.Role;
import com.realfinance.sofa.system.domain.RoleGroup;
import com.realfinance.sofa.system.domain.Tenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface RoleRepository extends JpaRepositoryImplementation<Role, Integer> {

    Set<Role> findByTenantAndCodeInAndEnabled(Tenant tenant, Collection<String> codes, Boolean enabled);

    boolean existsByTenantAndCode(Tenant tenant, String code);
    @Override
    @EntityGraph(value = "Role{tenant}", type = EntityGraph.EntityGraphType.FETCH)
    Page<Role> findAll(Specification<Role> spec, Pageable pageable);

    @Override
    @EntityGraph(value = "Role{tenant}", type = EntityGraph.EntityGraphType.FETCH)
    Page<Role> findAll(Pageable pageable);

    Page<Role> findByRoleGroups(RoleGroup roleGroups, Pageable pageable);
    @Query("SELECT r FROM Role r JOIN r.roleGroups rg WHERE rg = :roleGroups AND (r.code LIKE :filter OR r.name LIKE :filter)")
    Page<Role> findByRoleGroupsAndFilter(RoleGroup roleGroups, String filter, Pageable pageable);

    List<Role> findByCodeIn(Collection<String> roleCodeIn);
}
