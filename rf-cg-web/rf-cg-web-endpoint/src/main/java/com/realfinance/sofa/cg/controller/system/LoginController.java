package com.realfinance.sofa.cg.controller.system;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.cg.model.system.TenantVo;
import com.realfinance.sofa.cg.service.mapstruct.TenantMapper;
import com.realfinance.sofa.system.facade.SystemQueryFacade;
import com.realfinance.sofa.system.model.TenantDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Tag(name = "登录界面")
@RequestMapping("/system/login")
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @SofaReference(interfaceType = SystemQueryFacade.class, uniqueId = "${service.rf-system.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private SystemQueryFacade systemQueryFacade;

    @Resource
    private TenantMapper tenantMapper;

    @Operation(summary = "查询所有可用的法人")
    @GetMapping("tenants")
    public ResponseEntity<List<TenantVo>> queryTenants() {
        List<TenantDto> tenants = systemQueryFacade.queryTenants();
        return ResponseEntity.ok(tenants.stream().map(tenantMapper::tenantDto2TenantVo).collect(Collectors.toList()));
    }

    @Operation(summary = "获取验证码", description = "接口未实现")
    @GetMapping("captcha")
    public ResponseEntity<?> captcha() {
        // TODO: 2020/10/25
        return ResponseEntity.ok("接口未实现");
    }
}
