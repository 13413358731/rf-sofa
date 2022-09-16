package com.realfinance.sofa.system.test.facade;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.system.facade.UserAuthInfoFacade;
import com.realfinance.sofa.system.facade.UserMngFacade;
import com.realfinance.sofa.system.model.*;
import com.realfinance.sofa.system.test.base.AbstractTestBase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class UserMngFacadeTest extends AbstractTestBase {

    private static final Logger log = LoggerFactory.getLogger(UserMngFacadeTest.class);

    @SofaReference(interfaceType = UserMngFacade.class, uniqueId = "${service.rf-system.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private UserMngFacade userMngFacade;

    @SofaReference(interfaceType = UserAuthInfoFacade.class, uniqueId = "${service.rf-system.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private UserAuthInfoFacade userAuthInfoFacade;

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
        Page<UserDto> all = userMngFacade.list(null, pageRequest);
        Assert.assertFalse(all.getContent().isEmpty());
        // 按照部门查询
        Integer testDepartmentId = 1;
        UserQueryCriteria queryByDepartment = new UserQueryCriteria();
        queryByDepartment.setDepartmentId(testDepartmentId);
        Page<UserDto> byDepartment = userMngFacade.list(queryByDepartment, pageRequest);
        assertThat(byDepartment).isNotEmpty();
        assertThat(byDepartment)
                .extracting(UserDto::getDepartment)
                .extracting(DepartmentSmallDto::getId)
                .allMatch(testDepartmentId::equals);
        // 按照租户查询
        String testTenantId = "sdebank";
        UserQueryCriteria queryByTenant = new UserQueryCriteria();
        queryByTenant.setTenantId(testTenantId);
        Page<UserDto> byTenant = userMngFacade.list(queryByTenant, pageRequest);
        assertThat(byTenant).isNotEmpty();
        assertThat(byTenant)
                .extracting(UserDto::getTenant)
                .extracting(TenantSmallDto::getId)
                .allMatch(testTenantId::equals);
        // username模糊查询
        String testUsernameLike = "test";
        UserQueryCriteria queryByUsernameLike = new UserQueryCriteria();
        queryByUsernameLike.setUsernameLike(testUsernameLike);
        Page<UserDto> byUsernameLike = userMngFacade.list(queryByUsernameLike, pageRequest);
        assertThat(byUsernameLike).isNotEmpty();
        assertThat(byUsernameLike)
                .extracting(UserDto::getUsername)
                .allMatch(e -> e.contains(testUsernameLike));
    }

    /**
     * 测试用户详情查询
     */
    @Test
    public void test2() {
        UserDetailsDto admin = userMngFacade.getDetailsById(1);
        Assert.assertEquals("realfinance",admin.getUsername());
        Assert.assertNotNull(admin.getTenant());
        Assert.assertEquals("SYSTEM_TENANT",admin.getTenant().getId());
    }

    /**
     * 测试保存用户
     */
    @Test
    public void test3() {
        // 新增
        UserSaveDto saveDto = new UserSaveDto();
        saveDto.setUsername("testCreateUser");
        saveDto.setRealname("测试新增用户");
        saveDto.setEmail("413528001@qq.com");
        saveDto.setEnabled(true);
        saveDto.setClassification("A");
        saveDto.setMobile("+8613539454000");
        saveDto.setDepartment(1);
        saveDto.setTenant("SYSTEM_TENANT");
        saveDto.setRoles(Collections.singleton(2));
        Integer id = userMngFacade.save(saveDto);
        Assert.assertNotNull(id);
        UserDetailsDto created = userMngFacade.getDetailsById(id);
        Assert.assertNotNull(created);
        Assert.assertEquals(saveDto.getUsername(),created.getUsername());
        Assert.assertEquals(saveDto.getEmail(),created.getEmail());
        Assert.assertEquals(saveDto.getEnabled(),created.getEnabled());
        Assert.assertNotNull(created.getDepartment());
        Assert.assertEquals(saveDto.getDepartment(),created.getDepartment().getId());
        Assert.assertNotNull(created.getRoles());
        Assert.assertEquals(saveDto.getRoles().size(),created.getRoles().size());
        // 修改
        saveDto.setId(id);
        saveDto.setUsername("testUpdateUsername");
        saveDto.setEnabled(false);
        saveDto.setRoles(Stream.of(2,3).collect(Collectors.toSet()));
        // TODO: 2020/10/29 测试部门 租户也修改
        userMngFacade.save(saveDto);
        UserDetailsDto updated = userMngFacade.getDetailsById(id);
        Assert.assertEquals(saveDto.getUsername(),updated.getUsername());
        Assert.assertEquals(saveDto.getEnabled(),updated.getEnabled());
        Assert.assertNotNull(updated.getRoles());
        Assert.assertEquals(saveDto.getRoles().size(),updated.getRoles().size());
    }

    /**
     * 测试修改密码
     */
    @Test
    public void test4() {
        userMngFacade.resetPassword(13,"123456","newpassword","newpassword");
        Boolean check = userAuthInfoFacade.checkPassword("sdebank", "testuser10", "newpassword");
        Assert.assertTrue(check);
    }


    /**
     * 测试删除用户
     */
    @Test
    public void test5() {
        Integer testDeleteUserId = 3;
        UserDetailsDto uesr = userMngFacade.getDetailsById(testDeleteUserId);
        Assert.assertNotNull(uesr);
        userMngFacade.delete(Collections.singleton(testDeleteUserId));
        UserDetailsDto deleted = userMngFacade.getDetailsById(testDeleteUserId);
        Assert.assertNull(deleted);
    }
}
