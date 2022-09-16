package com.realfinance.sofa.cg.util;

import com.alipay.sofa.rpc.context.RpcInvokeContext;
import com.realfinance.sofa.cg.security.AuthInfo;
import com.realfinance.sofa.cg.security.Department;
import com.realfinance.sofa.common.datascope.DataScopeUtils;
import com.realfinance.sofa.system.model.SystemConstants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

import javax.management.relation.Role;
import java.util.List;

public class RpcUtils {

    private final static Logger log = LoggerFactory.getLogger(RpcUtils.class);

    public static void putRequestBaggage(Authentication authentication) {
        if (log.isTraceEnabled()) {
            log.trace("设置安全认证到RPC上下文：{}",authentication);
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof AuthInfo) {
            RpcInvokeContext context = RpcInvokeContext.getContext();
            putTenantId(context, (AuthInfo) principal);
            putPrincipalId(context, (AuthInfo) principal);
            putRrincipalDepartmentId(context, (AuthInfo) principal);
            putRrincipalDepartmentCode(context, (AuthInfo) principal);
            // 登录到系统租户不校验数据的租户ID
            if (StringUtils.equals(((AuthInfo) principal).getTenantId(), SystemConstants.SYSTEM_TENANT_ID)) {
                DataScopeUtils.installSkipValidateTenantId(true);
            }
        }
    }

    private static void putTenantId(RpcInvokeContext context, AuthInfo authInfo) {
        com.realfinance.sofa.common.rpc.util.RpcUtils.setTenantId(authInfo.getTenantId());
    }

    private static void putPrincipalId(RpcInvokeContext context, AuthInfo authInfo) {
        com.realfinance.sofa.common.rpc.util.RpcUtils.setPrincipalId(authInfo.getUser().getId());
    }

    private static void putRrincipalDepartmentId(RpcInvokeContext context, AuthInfo authInfo) {
        Department department = authInfo.getUser().getDepartment();
        if (department != null) {
            com.realfinance.sofa.common.rpc.util.RpcUtils.setPrincipalDepartmentId(department.getId());
        }
    }

    private static void putRrincipalDepartmentCode(RpcInvokeContext context, AuthInfo authInfo) {
        Department department = authInfo.getUser().getDepartment();
        if (department != null) {
            com.realfinance.sofa.common.rpc.util.RpcUtils.setPrincipalDepartmentCode(department.getCode());
        }
    }
}
