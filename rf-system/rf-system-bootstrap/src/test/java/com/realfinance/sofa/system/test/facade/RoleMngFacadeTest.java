package com.realfinance.sofa.system.test.facade;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.common.rpc.constants.RpcContextConstants;
import com.realfinance.sofa.common.rpc.util.RpcUtils;
import com.realfinance.sofa.system.facade.RoleMngFacade;
import com.realfinance.sofa.system.model.RoleDto;
import com.realfinance.sofa.system.model.UserDto;
import com.realfinance.sofa.system.test.base.AbstractTestBase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.*;

public class RoleMngFacadeTest extends AbstractTestBase {

    private static final Logger log = LoggerFactory.getLogger(RoleMngFacadeTest.class);

    @SofaReference(interfaceType = RoleMngFacade.class, uniqueId = "${service.rf-system.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private RoleMngFacade roleMngFacade;

    @Before
    public void setup() {
        log.info("setup");
        testHelper.setRpcContext(1);   // 设置为超级管理员
        log.info("setup completed");
    }

    /**
     * 测试列表查询
     */
    @Test
    public void test1() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<RoleDto> all = roleMngFacade.list(null, pageRequest);
        Assert.assertFalse(all.getContent().isEmpty());
        // TODO: 2020/11/2 条件查询

    }

    /**
     * 测试查询角色下的用户
     */
    @Test
    public void test2() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<UserDto> users = roleMngFacade.listUsersById(1, null,pageRequest);
        Assert.assertFalse(users.getContent().isEmpty());
    }

    /**
     * 测试添加角色用户关联
     */
    @Test
    public void test3() {
        roleMngFacade.addUsers(1, Stream.of(2,3).collect(Collectors.toSet()));
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<UserDto> users = roleMngFacade.listUsersById(1, null,pageRequest);
        Assert.assertFalse(users.getContent().isEmpty());
        Assert.assertThat(users.getContent().stream().map(UserDto::getId).collect(Collectors.toList()),
                hasItems(2,3));
    }

    /**
     * 测试删除角色用户关联
     */
    @Test
    public void test4() {
        roleMngFacade.removeUsers(1,Stream.of(1).collect(Collectors.toSet()));
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<UserDto> users = roleMngFacade.listUsersById(1, null, pageRequest);
        Assert.assertThat(users.getContent().stream().map(UserDto::getId).collect(Collectors.toList()),
                not(hasItem(1)));
    }

    /**
     * 测试查询角色关联的菜单ID集合
     */
    @Test
    public void test5() {
        Set<Integer> menuIds = roleMngFacade.listMenuIdsById(4);
        Assert.assertFalse(menuIds.isEmpty());
    }

    /**
     * 测试更新角色菜单关联
     */
    @Test
    public void test6() {
        roleMngFacade.updateMenus(4,Stream.of(1,6).collect(Collectors.toSet()));
        Set<Integer> menuIds = roleMngFacade.listMenuIdsById(4);
        Assert.assertTrue(menuIds.size() == 2);
        Assert.assertEquals(menuIds,Stream.of(1,6).collect(Collectors.toSet()));
    }

    @Test
    public void test7() {
        Set<Integer> menuDataRuleIds = roleMngFacade.listMenuDataRuleIdsByIdAndMenuId(1, 33);
        Assert.assertTrue(menuDataRuleIds.isEmpty());
    }

    /**
     * 测试更新角色菜单数据规则关联
     */
    @Test
    public void test8() {
        roleMngFacade.updateMenuDataRules(1,33,Stream.of(1).collect(Collectors.toSet()));
        Set<Integer> menuDataRuleIds = roleMngFacade.listMenuDataRuleIdsByIdAndMenuId(1, 33);
        Assert.assertTrue(menuDataRuleIds.size() == 1);
    }

    /**
     * 测试保存角色
     */
    @Test
    public void test9() {
        // TODO: 2020/11/2

    }

    /**
     * 测试删除角色
     */
    @Test
    public void test10() {
        // TODO: 2020/11/2
    }

}
