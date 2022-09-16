package com.realfinance.sofa.cg.controller.system;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.cg.model.system.TenantQueryCriteriaRequest;
import com.realfinance.sofa.cg.model.system.TenantSaveRequest;
import com.realfinance.sofa.cg.model.system.TenantVo;
import com.realfinance.sofa.cg.service.mapstruct.TenantMapper;
import com.realfinance.sofa.system.facade.SystemQueryFacade;
import com.realfinance.sofa.system.facade.TenantMngFacade;
import com.realfinance.sofa.system.model.TenantDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Set;

@RestController
@Tag(name = "法人管理")
@RequestMapping("/system/tenantmng")
public class TenantMngController {

    private static final Logger log = LoggerFactory.getLogger(TenantMngController.class);

    public static final String MENU_CODE_ROOT = "tenantmng";
    public static final String MENU_CODE_VIEW = MENU_CODE_ROOT + ":view";
    public static final String MENU_CODE_SAVE = MENU_CODE_ROOT + ":save";
    public static final String MENU_CODE_DELETE = MENU_CODE_ROOT + ":delete";

    @SofaReference(interfaceType = TenantMngFacade.class, uniqueId = "${service.rf-system.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private TenantMngFacade tenantMngFacade;

    @SofaReference(interfaceType = SystemQueryFacade.class, uniqueId = "${service.rf-system.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private SystemQueryFacade systemQueryFacade;

    @Resource
    private TenantMapper tenantMapper;

    @GetMapping("list")
    @Operation(summary = "查询法人列表")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.system.TenantMngController).MENU_CODE_VIEW)")
    public ResponseEntity<Page<TenantVo>> list(TenantQueryCriteriaRequest queryCriteria,
                                               Pageable pageable) {
        Page<TenantDto> result = tenantMngFacade.list(queryCriteria,pageable);
        return ResponseEntity.ok(result.map(tenantMapper::tenantDto2TenantVo));
    }

    @PostMapping("save")
    @Operation(summary = "保存法人")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.system.TenantMngController).MENU_CODE_SAVE)")
    public ResponseEntity<String> save(@Validated @RequestBody TenantSaveRequest tenantSaveRequest) {
        String id = tenantMngFacade.save(tenantMapper.tenantSaveRequest2TenantSaveDto(tenantSaveRequest));
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("delete")
    @Operation(summary = "删除法人")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.system.TenantMngController).MENU_CODE_DELETE)")
    public ResponseEntity<?> delete(@Parameter(description = "法人编码（ID）") @RequestParam Set<String> id) {
        tenantMngFacade.delete(id);
        return ResponseEntity.ok().build();
    }
}
