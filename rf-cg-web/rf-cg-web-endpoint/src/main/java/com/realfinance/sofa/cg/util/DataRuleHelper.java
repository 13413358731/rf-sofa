package com.realfinance.sofa.cg.util;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.cg.security.AuthInfo;
import com.realfinance.sofa.common.datascope.DataScopeUtils;
import com.realfinance.sofa.system.facade.SystemQueryFacade;
import com.realfinance.sofa.system.model.MenuDataRules;
import com.realfinance.sofa.system.model.SystemConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 这个组件可以安装数据规则
 */
@Component
public class DataRuleHelper {

    private static final String MENU_DATA_RULES_PREFIX = "MENU_DATA_RULES:";

    @SofaReference(interfaceType = SystemQueryFacade.class, uniqueId = "${service.rf-system.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private SystemQueryFacade systemQueryFacade;

    @Autowired
    private HttpServletRequest httpServletRequest;

    /**
     * 安装菜单数据规则
     * @param menuCode
     */
    @PreAuthorize("isAuthenticated()")
    public void installDataRule(String menuCode) {
        AuthInfo principal = getAuthInfo();
        if (StringUtils.equals(SystemConstants.SYSTEM_TENANT_ID, principal.getTenant().getId())) {
            // 系统租户下用户不安装菜单数据规则，可以看到所有数据
        } else {
            MenuDataRules fromSession = getFromSession(menuCode, principal);
            DataScopeUtils.installMenuDataRules(fromSession);
        }
    }

    public void cleanDataRule() {
        DataScopeUtils.cleanMenuDataRules();
    }

    private MenuDataRules getFromSession(String menuCode, AuthInfo principal) {
        String key = MENU_DATA_RULES_PREFIX + menuCode;
        HttpSession session = httpServletRequest.getSession(false);
        if (session == null) {
            throw new RuntimeException("会话不存在");
        }
        MenuDataRules attribute = (MenuDataRules) session.getAttribute(key);
        if (attribute == null) {
            MenuDataRules menuDataRules = systemQueryFacade.queryMenuDataRules(principal.getTenantId(), principal.getRoleCodes(), menuCode);
            if (menuDataRules == null) {
                throw new RuntimeException("数据规则查询失败");
            }
            session.setAttribute(key,menuDataRules);
            attribute = menuDataRules;
        }
        return attribute;
    }

    private AuthInfo getAuthInfo() {
        return (AuthInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
