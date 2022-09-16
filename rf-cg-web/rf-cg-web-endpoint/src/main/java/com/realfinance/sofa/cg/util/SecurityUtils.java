package com.realfinance.sofa.cg.util;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取当前上下文的用户ID
     * @return 返回用户ID 或者 {@code null}
     */
    public static Integer getPrincipalId() {
        Authentication authentication = getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        if (authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }
        try {
            return Integer.parseInt(authentication.getName());
        } catch (Exception e) {
            return null;
        }
    }
}
