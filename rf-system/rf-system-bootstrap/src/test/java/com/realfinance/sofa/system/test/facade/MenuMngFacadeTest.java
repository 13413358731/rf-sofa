package com.realfinance.sofa.system.test.facade;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.common.rpc.constants.RpcContextConstants;
import com.realfinance.sofa.common.rpc.util.RpcUtils;
import com.realfinance.sofa.system.facade.MenuMngFacade;
import com.realfinance.sofa.system.model.*;
import com.realfinance.sofa.system.test.base.AbstractTestBase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;

public class MenuMngFacadeTest extends AbstractTestBase {

    private static final Logger log = LoggerFactory.getLogger(MenuMngFacadeTest.class);

    @SofaReference(interfaceType = MenuMngFacade.class, uniqueId = "${service.rf-system.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private MenuMngFacade menuMngFacade;

    @Before
    public void setup() {
        log.info("setup");
        testHelper.setRpcContext(1);   // 设置为超级管理员
        log.info("setup completed");
    }


    /**
     * 测试查询一级菜单
     */
    @Test
    public void test1() {
        List<MenuDto> firstLevel = menuMngFacade.listFirstLevel();
        Assert.assertFalse(firstLevel.isEmpty());
        Assert.assertThat(firstLevel.stream().map(MenuDto::getType).collect(Collectors.toList()),
                everyItem(is("FIRST_LEVEL")));
    }

    /**
     * 测试根据父ID查询
     */
    @Test
    public void test2() {
        List<MenuDto> menus = menuMngFacade.listByParentId(2);
        Assert.assertFalse(menus.isEmpty());
        Assert.assertThat(menus.stream().map(MenuDto::getParent).map(MenuSmallDto::getId)
                .collect(Collectors.toList()), everyItem(is(2)));
    }

    /**
     * 测试查询菜单数据规则
     */
    @Test
    public void test3() {
        List<MenuDataRuleDto> menuDataRules = menuMngFacade.listMenuDataRule(33);
        Assert.assertFalse(menuDataRules.isEmpty());
    }

    /**
     * 测试保存菜单
     */
    @Test
    public void test4() {
        // 新增
        MenuSaveDto menuSaveDto = new MenuSaveDto();
        menuSaveDto.setCode("testCreate");
        menuSaveDto.setName("测试新增菜单");
        menuSaveDto.setComponent("xxx");
        menuSaveDto.setHidden(false);
        menuSaveDto.setIcon("xxx");
        menuSaveDto.setKeepAlive(false);
        menuSaveDto.setSort(11);
        menuSaveDto.setParent(34);
        menuSaveDto.setType("SUB");
        Integer id = menuMngFacade.save(menuSaveDto);
        Optional<MenuDto> created = menuMngFacade.listByParentId(34)
                .stream().filter(e -> Objects.equals(id, e.getId())).findAny();
        Assert.assertTrue(created.isPresent());
        Assert.assertNotNull(created.get().getParent());
        Assert.assertNotNull(created.get().getCodePath());
        Assert.assertThat(created.get().getParent().getId(),is(34));
        // 修改
        menuSaveDto.setId(id);
        menuSaveDto.setParent(35); // 父节点修改为35
        menuMngFacade.save(menuSaveDto);
        menuMngFacade.listByParentId(34);
        Optional<MenuDto> updated = menuMngFacade.listByParentId(35)
                .stream().filter(e -> Objects.equals(id, e.getId())).findAny();
        Assert.assertTrue(updated.isPresent());
        Assert.assertNotNull(updated.get().getParent());
        Assert.assertThat(updated.get().getParent().getId(),is(35));
        Assert.assertNotNull(updated.get().getCodePath());
        // 判断path属性是否改变
        Assert.assertNotEquals(created.get().getCodePath(),updated.get().getCodePath());
    }

    /**
     * 测试保存数据规则
     */
    @Test
    public void test5() {
        MenuDataRuleSaveDto menuDataRuleSaveDto = new MenuDataRuleSaveDto();
        menuDataRuleSaveDto.setEnabled(true);
        menuDataRuleSaveDto.setMenu(33);
        menuDataRuleSaveDto.setRuleAttribute("time");
        menuDataRuleSaveDto.setRuleConditions("gt");
        menuDataRuleSaveDto.setRuleValue("2010-12-12 00:00:00");
        menuDataRuleSaveDto.setRuleName("测试新增菜单数据规则");
        Integer id = menuMngFacade.saveMenuDataRule(menuDataRuleSaveDto);
        List<MenuDataRuleDto> menuDataRules = menuMngFacade.listMenuDataRule(33);
        Optional<MenuDataRuleDto> created = menuDataRules.stream().filter(e -> Objects.equals(id, e.getId())).findAny();
        Assert.assertTrue(created.isPresent());
    }

    /**
     * 测试删除数据规则
     */
    @Test
    public void test6() {
        menuMngFacade.deleteMenuDataRule(Collections.singleton(1));
        List<MenuDataRuleDto> menuDataRule = menuMngFacade.listMenuDataRule(33);
        Assert.assertThat(menuDataRule.stream().map(MenuDataRuleDto::getId)
                        .collect(Collectors.toList()),
                everyItem(not(1)));
    }

    /**
     * 测试删除菜单
     */
    @Test
    public void  test7() {
        // TODO: 2020/10/29
        menuMngFacade.delete(Collections.singleton(32));
    }
}
