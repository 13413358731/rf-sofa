package com.realfinance.sofa.cg.sup.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Tag(name = "当前用户接口")
@RequestMapping("/cg/sup/principal")
public class PrincipalController {

    @Resource
    private ObjectMapper objectMapper;

    @GetMapping("userinfo")
    @Operation(summary = "查询用户信息")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> userInfo(Authentication authentication) {
        ObjectNode result = objectMapper.convertValue(authentication.getPrincipal(), ObjectNode.class);
        result.remove("authorities");
        ArrayNode authorities = result.putArray("authorities");
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            authorities.add(authority.getAuthority());
        }
        return ResponseEntity.ok(result);
    }
}
