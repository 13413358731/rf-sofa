package com.realfinance.sofa.common.rpc.constants;

public interface RpcContextConstants {
    // 租户ID
    String TENANT_ID_REQUEST_BAGGAGE_KEY = "tenant.id";
    // 登陆人ID
    String PRINCIPAL_ID_REQUEST_BAGGAGE_KEY = "principal.id";
    // 登陆人部门ID
    String PRINCIPAL_DEPARTMENT_ID_REQUEST_BAGGAGE_KEY = "principal.department.id";
    //登录人部门CODE
    String PRINCIPAL_DEPARTMENT_CODE_REQUEST_BAGGAGE_KEY = "principal.department.code";
    // 登录人角色CODE
    String PRINCIPAL_ROLE_CODE_REQUEST_BAGGAGE_KEY = "principal.role.code";
    // 当前操作菜单CODE
    String OPERATION_MENU_CODE_REQUEST_BAGGAGE_KEY = "operation.menu.code";

    // 菜单数据规则
    String MENU_DATA_RULES_JSON_REQUEST_BAGGAGE_KEY = "datarule.menuDataRules";
    // 是否跳过校验租户ID
    String SKIP_VALIDATE_TENANT_ID_REQUEST_BAGGAGE_KEY = "datarule.skipValidateTenantId";

}
