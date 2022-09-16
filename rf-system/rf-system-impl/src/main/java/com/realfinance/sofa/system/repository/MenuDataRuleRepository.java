package com.realfinance.sofa.system.repository;

import com.realfinance.sofa.system.domain.Menu;
import com.realfinance.sofa.system.domain.MenuDataRule;
import com.realfinance.sofa.system.domain.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.List;
import java.util.Set;

public interface MenuDataRuleRepository extends JpaRepositoryImplementation<MenuDataRule, Integer> {

    long countByMenu(Menu menu);

    List<MenuDataRule> findByMenu(Menu menu);

    Set<MenuDataRule> findByRolesAndMenuAndEnabled(Role role, Menu menu, Boolean enabled);

    @Override
    @EntityGraph(value = "MenuDataRule{menu}", type = EntityGraph.EntityGraphType.FETCH)
    Page<MenuDataRule> findAll(Specification<MenuDataRule> spec, Pageable pageable);

    @Override
    @EntityGraph(value = "MenuDataRule{menu}", type = EntityGraph.EntityGraphType.FETCH)
    Page<MenuDataRule> findAll(Pageable pageable);
}
