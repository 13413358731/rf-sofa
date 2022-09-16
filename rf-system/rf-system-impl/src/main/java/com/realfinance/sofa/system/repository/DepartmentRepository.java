package com.realfinance.sofa.system.repository;

import com.realfinance.sofa.system.domain.Department;
import com.realfinance.sofa.system.domain.Tenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.Collection;
import java.util.List;

public interface DepartmentRepository extends JpaRepositoryImplementation<Department, Integer> {

    boolean existsByTenantAndCode(Tenant tenant, String code);

    List<Department> findByTenantAndCodeIn(Tenant tenant, Collection<String> codes);

    List<Department> findByParentIsNull(Sort sort);
    @EntityGraph(value = "Department{parent,tenant}", type = EntityGraph.EntityGraphType.FETCH)
    List<Department> findByParent(Department parent);
    @EntityGraph(value = "Department{parent,tenant}", type = EntityGraph.EntityGraphType.FETCH)
    List<Department> findByParent(Department parent, Sort sort);
    int countByParent(Department parent);

    @Override
    @EntityGraph(value = "Department{parent,tenant}", type = EntityGraph.EntityGraphType.FETCH)
    List<Department> findAll();

    @Override
    @EntityGraph(value = "Department{parent,tenant}", type = EntityGraph.EntityGraphType.FETCH)
    List<Department> findAll(Specification<Department> spec);

    @Override
    @EntityGraph(value = "Department{parent,tenant}", type = EntityGraph.EntityGraphType.FETCH)
    Page<Department> findAll(Specification<Department> spec, Pageable pageable);

    @Override
    @EntityGraph(value = "Department{parent,tenant}", type = EntityGraph.EntityGraphType.FETCH)
    Page<Department> findAll(Pageable pageable);

    List<Department> findByCodeIn(Collection<String> departmentCodeIn);
}
