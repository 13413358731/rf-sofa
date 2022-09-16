package com.realfinance.sofa.common.datascope;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.realfinance.sofa.common.rpc.constants.RpcContextConstants;
import com.realfinance.sofa.common.rpc.util.RpcUtils;
import com.realfinance.sofa.system.model.MenuDataRules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class DataScopeUtils {

    private static final Logger log = LoggerFactory.getLogger(DataScopeUtils.class);

    private static final TypeReference<MenuDataRules> DATA_RULES_TYPE_REFERENCE = new TypeReference<MenuDataRules>() {
    };
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static void installSkipValidateTenantId(boolean skip) {
        if (log.isDebugEnabled()) {
            log.debug("设置跳过校验TenantId：{}",skip);
        }
        RpcUtils.putRequestBaggage(RpcContextConstants.SKIP_VALIDATE_TENANT_ID_REQUEST_BAGGAGE_KEY,String.valueOf(skip));
    }


    public static boolean loadSkipValidateTenantId() {
        Optional<String> skipValidateTenantId = RpcUtils.getRequestBaggage(RpcContextConstants.SKIP_VALIDATE_TENANT_ID_REQUEST_BAGGAGE_KEY);
        return skipValidateTenantId.map(Boolean::parseBoolean).orElse(false);
    }

    public static void installPrincipalId(Integer principalId) {
        RpcUtils.setPrincipalId(Objects.requireNonNull(principalId));
    }

    public static Optional<Integer> loadPrincipalId() {
        return RpcUtils.getPrincipalId();
    }

    public static void installDepartmentId(Integer departmentId) {
        RpcUtils.putRequestBaggage(RpcContextConstants.PRINCIPAL_DEPARTMENT_ID_REQUEST_BAGGAGE_KEY, Objects.requireNonNull(departmentId).toString());
    }

    public static Optional<Integer> loadDepartmentId() {
        return RpcUtils.getRequestBaggage(RpcContextConstants.PRINCIPAL_DEPARTMENT_ID_REQUEST_BAGGAGE_KEY).map(Integer::valueOf);
    }

    public static Optional<String> loadDepartmentCode() {
        return RpcUtils.getRequestBaggage(RpcContextConstants.PRINCIPAL_DEPARTMENT_CODE_REQUEST_BAGGAGE_KEY).map(String::valueOf);
    }
    public static void installTenantId(String tenantId) {
        RpcUtils.setTenantId(Objects.requireNonNull(tenantId));
    }

    public static String loadTenantId() {
        return RpcUtils.getTenantId().orElseThrow(() -> new RuntimeException("缺少TenantId"));
    }

    /**
     * 检查是否能访问该租户的数据
     * @param tenantIdToCheck 租户ID
     */
    public static void checkTenantCanAccess(String tenantIdToCheck) {
        Assert.notNull(tenantIdToCheck,"TenantId can not be null");
        if (!DataScopeUtils.loadSkipValidateTenantId()) {
            if (!Objects.equals(DataScopeUtils.loadTenantId(), tenantIdToCheck)) {
                throw new DataAccessForbiddenException("无权访问数据");
            }
        }
    }

    public static void installMenuDataRules(MenuDataRules menuDataRules) {
        try {
            RpcUtils.putRequestBaggage(RpcContextConstants.MENU_DATA_RULES_JSON_REQUEST_BAGGAGE_KEY, OBJECT_MAPPER.writeValueAsString(menuDataRules));
        } catch (JsonProcessingException e) {
            if (log.isErrorEnabled()) {
                log.error("安装菜单数据规则失败：{}",menuDataRules);
            }
            throw new RuntimeException("安装菜单数据规则失败", e);
        }
    }

    public static Optional<MenuDataRules> loadMenuDataRules() {
        Optional<String> menuDataRulesJson = RpcUtils.getRequestBaggage(RpcContextConstants.MENU_DATA_RULES_JSON_REQUEST_BAGGAGE_KEY);
        return menuDataRulesJson.filter(e -> !e.isBlank())
                .<MenuDataRules>map(json -> {
                    try {
                        return OBJECT_MAPPER.readValue(json, DATA_RULES_TYPE_REFERENCE);
                    } catch (IOException e) {
                        if (log.isErrorEnabled()) {
                            log.error("解析菜单数据规则失败：{}",json);
                        }
                        throw new RuntimeException("解析数据权限失败");
                    }
                });
    }

    public static void cleanMenuDataRules() {
        RpcUtils.removeRequestBaggage(RpcContextConstants.MENU_DATA_RULES_JSON_REQUEST_BAGGAGE_KEY);
    }
}
