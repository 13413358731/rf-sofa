package com.realfinance.sofa.cg.controller.flow;

import com.alibaba.fastjson.JSONObject;
import com.realfinance.sofa.cg.security.AuthInfo;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@Hidden
@RequestMapping("/flow/modeler-app")
public class AccountController {
    private static final String ACCESS_IDM = "access-idm";
    private static final String ACCESS_MODELER = "access-modeler";
    private static final String ACCESS_ADMIN = "access-admin";
    private static final String ACCESS_TASK = "access-task";
    private static final String ACCESS_REST_API = "access-rest-api";
    private static final List<String> PRIVILEGES = Stream.of(ACCESS_IDM,ACCESS_MODELER,ACCESS_ADMIN,ACCESS_TASK,ACCESS_REST_API).collect(Collectors.toList());

    /**
     * GET /rest/account -> get the current user.
     */
    @GetMapping(value = "/rest/account", produces = "application/json")
    public JSONObject getAccount(Authentication authentication) {
        JSONObject result = new JSONObject();
        result.put("id",authentication.getName());
        AuthInfo principal = (AuthInfo) authentication.getPrincipal();

        result.put("firstName", principal.getUser().getUsername());
        result.put("lastName", principal.getUser().getRealname());
        result.put("email",principal.getUser().getEmail());
        result.put("fullName",principal.getUser().getUsername() + " " + principal.getUser().getRealname());
        result.put("tenantId",principal.getTenant().getId());
//        result.put("groups",);
        result.put("privileges",PRIVILEGES); // 所有权限
        return result;
    }
}
