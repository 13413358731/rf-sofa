package com.realfinance.sofa.system.repository;

import com.realfinance.sofa.system.domain.Role;
import com.realfinance.sofa.system.domain.RoleGroup;
import com.realfinance.sofa.system.domain.Tenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.Collection;
import java.util.List;

public interface RoleGroupRepository extends JpaRepositoryImplementation<RoleGroup,Integer> {

    boolean existsByTenantAndCode(Tenant tenant, String code);

    @Override
    @EntityGraph(value = "RoleGroup{tenant}", type = EntityGraph.EntityGraphType.FETCH)
    Page<RoleGroup> findAll(Specification<RoleGroup> spec, Pageable pageable);

    @Override
    @EntityGraph(value = "RoleGroup{tenant}", type = EntityGraph.EntityGraphType.FETCH)
    Page<RoleGroup> findAll(Pageable pageable);


    List<RoleGroup> findByRolesIn(List<Role> roles);
}
