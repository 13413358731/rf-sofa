package com.realfinance.sofa.system.repository;

import com.realfinance.sofa.system.domain.Menu;
import com.realfinance.sofa.system.domain.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface MenuRepository extends JpaRepositoryImplementation<Menu, Integer> {

    boolean existsByCode(String code);
    List<Menu> findByType(Menu.Type type, Sort sort);

    /**
     * 根据角色code集合查询菜单code集合
     * @param roles 角色
     * @return
     */
    @Query("SELECT m.code FROM #{#entityName} m INNER JOIN m.roles r WHERE r IN :roles")
    Set<String> findMenuCodesByRoles(Set<Role> roles);

    List<Menu> findByParentIsNull(Sort sort);
    @EntityGraph(value = "Menu{parent}", type = EntityGraph.EntityGraphType.FETCH)
    List<Menu> findByParent(Menu parent);
    @EntityGraph(value = "Menu{parent}", type = EntityGraph.EntityGraphType.FETCH)
    List<Menu> findByParent(Menu parent, Sort sort);
    int countByParent(Menu parent);
    @EntityGraph(value = "Menu{parent}", type = EntityGraph.EntityGraphType.FETCH)
    Optional<Menu> findByCode(String code);

    @Override
    @EntityGraph(value = "Menu{parent}", type = EntityGraph.EntityGraphType.FETCH)
    Page<Menu> findAll(Specification<Menu> spec, Pageable pageable);

    @Override
    @EntityGraph(value = "Menu{parent}", type = EntityGraph.EntityGraphType.FETCH)
    Page<Menu> findAll(Pageable pageable);


}
