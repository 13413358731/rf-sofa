package com.realfinance.sofa.system.test.facade;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.common.rpc.constants.RpcContextConstants;
import com.realfinance.sofa.common.rpc.util.RpcUtils;
import com.realfinance.sofa.system.facade.RoleGroupMngFacade;
import com.realfinance.sofa.system.model.RoleGroupDto;
import com.realfinance.sofa.system.test.base.AbstractTestBase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public class RoleGroupMngFacadeTest extends AbstractTestBase {
    private static final Logger log = LoggerFactory.getLogger(RoleGroupMngFacadeTest.class);

    @SofaReference(interfaceType = RoleGroupMngFacade.class, uniqueId = "${service.rf-system.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private RoleGroupMngFacade roleGroupMngFacade;

    @Before
    public void setup() {
        log.info("setup");
        testHelper.setRpcContext(1);   // 设置为超级管理员
        log.info("setup completed");
    }

    /**
     * 测试角色组查询
     */
    @Test
    public void test1() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<RoleGroupDto> all = roleGroupMngFacade.list(null, pageRequest);
        Assert.assertFalse(all.getContent().isEmpty());
    }
}
