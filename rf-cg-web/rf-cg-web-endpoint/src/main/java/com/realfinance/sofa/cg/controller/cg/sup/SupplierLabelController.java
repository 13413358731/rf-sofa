package com.realfinance.sofa.cg.controller.cg.sup;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.cg.model.cg.CgSupplierLabelTypeVo;
import com.realfinance.sofa.cg.model.cg.CgSupplierLabelVo;
import com.realfinance.sofa.cg.service.mapstruct.CgSupplierLabelMapper;
import com.realfinance.sofa.cg.sup.facade.CgSupplierLabelFacade;
import com.realfinance.sofa.cg.sup.facade.CgSupplierLabelTypeFacade;
import com.realfinance.sofa.cg.sup.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Tag(name = "供应商标签管理")
@RequestMapping("/cg/sup/supplierlabel")
public class SupplierLabelController {

    private static final Logger log = LoggerFactory.getLogger(SupplierLabelController.class);

    public static final String MENU_CODE_ROOT = "supplierlabel";
    public static final String MENU_CODE_VIEW = MENU_CODE_ROOT + ":view";
    public static final String MENU_CODE_SAVE = MENU_CODE_ROOT + ":save";
    public static final String MENU_CODE_DELETE = MENU_CODE_ROOT + ":delete";
    public static final String MENU_CODE_TYPEMNG = MENU_CODE_ROOT + ":typemng"; // 标签类型管理权限

    @SofaReference(interfaceType = CgSupplierLabelFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgSupplierLabelFacade cgSupplierLabelFacade;
    @SofaReference(interfaceType = CgSupplierLabelTypeFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgSupplierLabelTypeFacade cgSupplierLabelTypeFacade;

    @Resource
    private CgSupplierLabelMapper cgSupplierLabelMapper;

    @GetMapping("/listtype")
    @Operation(summary = "查询标签类型")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.sup.SupplierLabelController).MENU_CODE_VIEW)")
    public ResponseEntity<Page<CgSupplierLabelTypeVo>> listTypes(@Parameter(description = "过滤条件") @RequestParam(required = false) String filter,
                                                                 Pageable pageable) {
        Page<CgSupplierLabelTypeDto> result = cgSupplierLabelTypeFacade.list(filter, pageable);
        return ResponseEntity.ok(result.map(cgSupplierLabelMapper::toVo));
    }

    @PostMapping("savetype")
    @Operation(summary = "保存标签类型")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.sup.SupplierLabelController).MENU_CODE_TYPEMNG)")
    public ResponseEntity<Integer> saveType(@Validated(CgSupplierLabelTypeVo.Save.class)
                                            @RequestBody
                                            @Schema(implementation = CgSupplierLabelTypeVo.Save.class) CgSupplierLabelTypeVo vo) {
        CgSupplierLabelTypeSaveDto saveDto = cgSupplierLabelMapper.toSaveDto(vo);
        Integer id = cgSupplierLabelTypeFacade.save(saveDto);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("deleteType")
    @Operation(summary = "删除标签类型")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.sup.SupplierLabelController).MENU_CODE_TYPEMNG)")
    public ResponseEntity<?> deleteType(@Parameter(description = "采购供应商标签类型ID") @RequestParam Set<Integer> supplierLabelTypeId) {
        cgSupplierLabelTypeFacade.delete(supplierLabelTypeId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("listfirstlevel")
    @Operation(summary = "查询第一级标签")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.sup.SupplierLabelController).MENU_CODE_VIEW)")
    public ResponseEntity<List<CgSupplierLabelVo>> listFirstLevel(@Parameter(description = "采购供应商标签类型ID") @RequestParam Integer supplierLabelTypeId) {
        CgSupplierLabelQueryCriteria queryCriteria = new CgSupplierLabelQueryCriteria();
        queryCriteria.setSupplierLabelTypeId(supplierLabelTypeId);
        queryCriteria.setParentIsNull(true);
        List<CgSupplierLabelDto> result = cgSupplierLabelFacade.list(queryCriteria);
        return ResponseEntity.ok(result.stream().map(cgSupplierLabelMapper::toVo).collect(Collectors.toList()));
    }

    @GetMapping("listbyparentid")
    @Operation(summary = "根据父节点ID查询标签")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.sup.SupplierLabelController).MENU_CODE_VIEW)")
    public ResponseEntity<List<CgSupplierLabelVo>> listByParentId(@Parameter(description = "上级标签ID") @RequestParam(value = "parentId",required = false) Integer parentId) {
        CgSupplierLabelQueryCriteria queryCriteria = new CgSupplierLabelQueryCriteria();
        queryCriteria.setParentId(parentId);
        List<CgSupplierLabelDto> result = cgSupplierLabelFacade.list(queryCriteria);
        return ResponseEntity.ok(result.stream().map(cgSupplierLabelMapper::toVo).collect(Collectors.toList()));
    }

    @PostMapping("save")
    @Operation(summary = "保存标签")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.sup.SupplierLabelController).MENU_CODE_SAVE)")
    public ResponseEntity<Integer> save(@Validated(CgSupplierLabelVo.Save.class)
                                        @RequestBody
                                        @Schema(implementation = CgSupplierLabelVo.Save.class) CgSupplierLabelVo vo) {
        CgSupplierLabelSaveDto saveDto = cgSupplierLabelMapper.toSaveDto(vo);
        Integer id = cgSupplierLabelFacade.save(saveDto);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("delete")
    @Operation(summary = "删除标签")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.sup.SupplierLabelController).MENU_CODE_DELETE)")
    public ResponseEntity<?> delete(@Parameter(description = "标签ID") @RequestParam Set<Integer> id) {
        cgSupplierLabelFacade.delete(id);
        return ResponseEntity.ok().build();
    }
}
