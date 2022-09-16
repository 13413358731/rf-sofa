package com.realfinance.sofa.system.test.facade;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.common.rpc.constants.RpcContextConstants;
import com.realfinance.sofa.common.rpc.util.RpcUtils;
import com.realfinance.sofa.system.facade.DepartmentMngFacade;
import com.realfinance.sofa.system.model.DepartmentSaveDto;
import com.realfinance.sofa.system.model.TenantSmallDto;
import com.realfinance.sofa.system.test.base.AbstractTestBase;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;


public class DepartmentMngFacadeTest extends AbstractTestBase {

    private static final Logger log = LoggerFactory.getLogger(DepartmentMngFacadeTest.class);

    @SofaReference(interfaceType = DepartmentMngFacade.class, uniqueId = "${service.rf-system.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private DepartmentMngFacade departmentMngFacade;

    @Before
    public void setup() {
        log.info("setup");
        testHelper.setRpcContext(1);   // 设置为超级管理员
        log.info("setup completed");
    }

    @Test
    public void test() {
        departmentMngFacade.delete(Collections.singleton(11));
    }

    @Test
    public void test3() {
        DepartmentSaveDto departmentSaveDto = new DepartmentSaveDto();
        departmentSaveDto.setCategory("1");
        departmentSaveDto.setCode("testcode");
        departmentSaveDto.setEnabled(true);
        departmentSaveDto.setType("SUB");
        departmentSaveDto.setSort(1);
        departmentSaveDto.setTenant("SYSTEM_TENANT");
        departmentSaveDto.setName("测试新增");
        Integer save = departmentMngFacade.save(departmentSaveDto);
    }

}
