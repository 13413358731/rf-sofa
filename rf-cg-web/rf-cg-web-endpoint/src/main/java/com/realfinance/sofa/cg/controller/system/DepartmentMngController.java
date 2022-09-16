package com.realfinance.sofa.cg.controller.system;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.cg.model.system.DepartmentSaveRequest;
import com.realfinance.sofa.cg.model.system.DepartmentVo;
import com.realfinance.sofa.cg.model.system.TenantQueryCriteriaRequest;
import com.realfinance.sofa.cg.model.system.TenantVo;
import com.realfinance.sofa.cg.service.mapstruct.DepartmentMapper;
import com.realfinance.sofa.cg.service.mapstruct.TenantMapper;
import com.realfinance.sofa.system.facade.DepartmentMngFacade;
import com.realfinance.sofa.system.facade.SystemQueryFacade;
import com.realfinance.sofa.system.model.DepartmentDto;
import com.realfinance.sofa.system.model.TenantSmallDto;
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
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@Tag(name = "部门管理")
@RequestMapping("/system/departmentmng")
public class DepartmentMngController {

    private static final Logger log = LoggerFactory.getLogger(DepartmentMngController.class);

    public static final String MENU_CODE_ROOT = "departmentmng";
    public static final String MENU_CODE_VIEW = MENU_CODE_ROOT + ":view";
    public static final String MENU_CODE_SAVE = MENU_CODE_ROOT + ":save";
    public static final String MENU_CODE_DELETE = MENU_CODE_ROOT + ":delete";

    @SofaReference(interfaceType = DepartmentMngFacade.class, uniqueId = "${service.rf-system.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private DepartmentMngFacade departmentMngFacade;
    @SofaReference(interfaceType = SystemQueryFacade.class, uniqueId = "${service.rf-system.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private SystemQueryFacade systemQueryFacade;

    @Resource
    private DepartmentMapper departmentMapper;
    @Resource
    private TenantMapper tenantMapper;

    @GetMapping("listfirstlevel")
    @Operation(summary = "查询第一级部门")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.system.DepartmentMngController).MENU_CODE_VIEW)")
    public ResponseEntity<List<DepartmentVo>> listFirstLevel() {
        List<DepartmentDto> result = departmentMngFacade.listFirstLevel();
        return ResponseEntity.ok(result.stream().map(departmentMapper::departmentDto2DepartmentVo).collect(Collectors.toList()));
    }

    @GetMapping("listbyparentid")
    @Operation(summary = "根据父节点ID查询部门列表")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.system.DepartmentMngController).MENU_CODE_VIEW)")
    public ResponseEntity<List<DepartmentVo>> listByParentId(@Parameter(description = "上级部门ID") @RequestParam(required = false) Integer parentId) {
        List<DepartmentDto> result = departmentMngFacade.listByParentId(parentId);
        return ResponseEntity.ok(result.stream().map(departmentMapper::departmentDto2DepartmentVo).collect(Collectors.toList()));
    }

    @GetMapping("querytenantrefer")
    @Operation(summary = "查询法人参照", description = "非系统租户只返回当前登录的法人")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<TenantVo>> queryTenantRefer(TenantQueryCriteriaRequest queryCriteria,
                                                           Pageable pageable) {
        Page<TenantSmallDto> result = systemQueryFacade.queryTenantRefer(queryCriteria,pageable);
        return ResponseEntity.ok(result.map(tenantMapper::tenantSmallDto2TenantVo));
    }

    @PostMapping("save")
    @Operation(summary = "保存部门")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.system.DepartmentMngController).MENU_CODE_SAVE)")
    public ResponseEntity<Integer> save(@Validated @RequestBody DepartmentSaveRequest departmentSaveRequest) {
        Integer id = departmentMngFacade.save(departmentMapper.departmentSaveRequest2DepartmentSaveDto(departmentSaveRequest));
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("delete")
    @Operation(summary = "删除部门")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.system.DepartmentMngController).MENU_CODE_DELETE)")
    public ResponseEntity<?> delete(@Parameter(description = "部门ID") @RequestParam Set<Integer> id) {
        departmentMngFacade.delete(id);
        return ResponseEntity.ok().build();
    }
}
