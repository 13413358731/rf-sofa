package com.realfinance.sofa.cg.sup.test.datarule;

import com.realfinance.sofa.cg.sup.domain.Supplier;
import com.realfinance.sofa.cg.sup.repository.SupplierRepository;
import com.realfinance.sofa.cg.sup.test.base.AbstractTestBase;
import com.realfinance.sofa.common.datascope.DataScopeUtils;
import com.realfinance.sofa.common.datascope.JpaQueryHelper;
import com.realfinance.sofa.system.model.MenuDataRuleSmallDto;
import com.realfinance.sofa.system.model.MenuDataRules;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;

@Transactional
public class SupplierTest extends AbstractTestBase {

    @Autowired
    private JpaQueryHelper jpaQueryHelper;

    @Autowired
    private SupplierRepository supplierRepository;

    @Test
    public void test1() {
        DataScopeUtils.installPrincipalId(20);
        DataScopeUtils.installTenantId("SYSTEM_TENANT");
        MenuDataRules menuDataRules = new MenuDataRules();
        menuDataRules.setMenuCode("testcode");
        menuDataRules.setHasMenuDataRule(true);
        ArrayList<MenuDataRules.RoleRules> roleRulesList = new ArrayList<>();
        MenuDataRules.RoleRules roleRules = new MenuDataRules.RoleRules();
        roleRules.setRoleCode("testrole");
        MenuDataRuleSmallDto menuDataRuleSmallDto = new MenuDataRuleSmallDto();
        menuDataRuleSmallDto.setId(1);
        menuDataRuleSmallDto.setRuleValue("5,12");
        menuDataRuleSmallDto.setRuleConditions("NOT_IN");
        menuDataRuleSmallDto.setRuleAttribute("supplierLabels.id");
        roleRules.setRules(Collections.singletonList(menuDataRuleSmallDto));
        roleRulesList.add(roleRules);
        menuDataRules.setRoleRulesCollection(roleRulesList);
        DataScopeUtils.installMenuDataRules(menuDataRules);
        Page<Supplier> result = supplierRepository.findAll(jpaQueryHelper.dataRuleSpecification(), PageRequest.of(0, 20));
        System.out.println(result);
    }

    @Test
    public void test2() {
        DataScopeUtils.installPrincipalId(20);
        DataScopeUtils.installTenantId("SYSTEM_TENANT");
        MenuDataRules menuDataRules = new MenuDataRules();
        menuDataRules.setMenuCode("testcode");
        menuDataRules.setHasMenuDataRule(true);
        ArrayList<MenuDataRules.RoleRules> roleRulesList = new ArrayList<>();
        MenuDataRules.RoleRules roleRules = new MenuDataRules.RoleRules();
        roleRules.setRoleCode("testrole");
        MenuDataRuleSmallDto menuDataRuleSmallDto = new MenuDataRuleSmallDto();
        menuDataRuleSmallDto.setId(1);
        menuDataRuleSmallDto.setRuleValue("$entity.supplierLabels.name$ in ('asd','basd')");
        menuDataRuleSmallDto.setRuleConditions("SQL");
        menuDataRuleSmallDto.setRuleAttribute("");
        roleRules.setRules(Collections.singletonList(menuDataRuleSmallDto));
        roleRulesList.add(roleRules);
        menuDataRules.setRoleRulesCollection(roleRulesList);
        DataScopeUtils.installMenuDataRules(menuDataRules);
        Page<Supplier> result = supplierRepository.findAll(jpaQueryHelper.dataRuleSpecification(), PageRequest.of(0, 20));
        System.out.println(result);
    }

    @Test
    public void test3() {
        DataScopeUtils.installPrincipalId(20);
        DataScopeUtils.installTenantId("SYSTEM_TENANT");
        MenuDataRules menuDataRules = new MenuDataRules();
        menuDataRules.setMenuCode("testcode");
        menuDataRules.setHasMenuDataRule(true);
        ArrayList<MenuDataRules.RoleRules> roleRulesList = new ArrayList<>();
        MenuDataRules.RoleRules roleRules = new MenuDataRules.RoleRules();
        roleRules.setRoleCode("testrole");
        MenuDataRuleSmallDto menuDataRuleSmallDto = new MenuDataRuleSmallDto();
        menuDataRuleSmallDto.setId(1);
        menuDataRuleSmallDto.setRuleValue("$entity.supplierLabels.id$ not in (SELECT a.id FROM Supplier as a LEFT JOIN SupplierLabel as b on a.id = b.id where b.name = '123')");
        menuDataRuleSmallDto.setRuleConditions("SQL");
        menuDataRuleSmallDto.setRuleAttribute("");
        roleRules.setRules(Collections.singletonList(menuDataRuleSmallDto));
        roleRulesList.add(roleRules);
        menuDataRules.setRoleRulesCollection(roleRulesList);
        DataScopeUtils.installMenuDataRules(menuDataRules);
        Page<Supplier> result = supplierRepository.findAll(jpaQueryHelper.dataRuleSpecification(), PageRequest.of(0, 20));
        System.out.println(result);
    }
}
