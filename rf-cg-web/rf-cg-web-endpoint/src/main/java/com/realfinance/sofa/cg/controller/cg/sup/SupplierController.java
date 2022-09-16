package com.realfinance.sofa.cg.controller.cg.sup;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.cg.model.cg.CgSupplierLabelTreeVo;
import com.realfinance.sofa.cg.model.cg.CgSupplierLabelTypeVo;
import com.realfinance.sofa.cg.model.cg.CgSupplierQueryCriteriaRequest;
import com.realfinance.sofa.cg.model.cg.CgSupplierVo;
import com.realfinance.sofa.cg.service.mapstruct.CgSupplierLabelMapper;
import com.realfinance.sofa.cg.service.mapstruct.CgSupplierMapper;
import com.realfinance.sofa.cg.sup.facade.CgSupplierFacade;
import com.realfinance.sofa.cg.sup.facade.CgSupplierLabelFacade;
import com.realfinance.sofa.cg.sup.facade.CgSupplierLabelTypeFacade;
import com.realfinance.sofa.cg.sup.model.*;
import com.realfinance.sofa.cg.util.DataRuleHelper;
import com.realfinance.sofa.flow.facade.FlowFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@RestController
@Tag(name = "供应商库")
@RequestMapping("/cg/sup/supplierbase")
public class SupplierController {

    private static final Logger log = LoggerFactory.getLogger(SupplierController.class);

    public static final String MENU_CODE_ROOT = "supplierbase";
    public static final String MENU_CODE_VIEW = MENU_CODE_ROOT + ":view";

    @SofaReference(interfaceType = CgSupplierFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgSupplierFacade cgSupplierFacade;
    @SofaReference(interfaceType = CgSupplierLabelFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgSupplierLabelFacade cgSupplierLabelFacade;
    @SofaReference(interfaceType = CgSupplierLabelTypeFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgSupplierLabelTypeFacade cgSupplierLabelTypeFacade;
    @SofaReference(interfaceType = FlowFacade.class, uniqueId = "${service.rf-flow.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private FlowFacade flowFacade;

    @Resource
    private DataRuleHelper dataRuleHelper;

    @Resource
    private CgSupplierMapper cgSupplierMapper;
    @Resource
    private CgSupplierLabelMapper cgSupplierLabelMapper;

    @GetMapping("list")
    @Operation(summary = "查询供应商列表")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.sup.SupplierController).MENU_CODE_VIEW)")
    public ResponseEntity<Page<CgSupplierVo>> list(CgSupplierQueryCriteriaRequest queryCriteria,
                                                   Pageable pageable) {
        dataRuleHelper.installDataRule(MENU_CODE_VIEW);
        Page<CgSupplierDto> result = cgSupplierFacade.list(queryCriteria, pageable);
        return ResponseEntity.ok(result.map(cgSupplierMapper::toVo));
    }

    @GetMapping("getdetailsbyid")
    @Operation(summary = "查询供应商详情")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.sup.SupplierController).MENU_CODE_VIEW)")
    public ResponseEntity<CgSupplierVo> getDetailsById(@Parameter(description = "供应商ID") @RequestParam Integer id) {
        dataRuleHelper.installDataRule(MENU_CODE_VIEW);
        CgSupplierDetailsDto result = cgSupplierFacade.getDetailsById(id);
        return ResponseEntity.ok(cgSupplierMapper.toVo(result));
    }

    @GetMapping("querysupplierlabeltyperefer")
    @Operation(summary = "查询标签类型")
    // TODO: 2020/12/15 权限
    public ResponseEntity<Page<CgSupplierLabelTypeVo>> querySupplierLabelTypeRefer(@Parameter(description = "过滤条件") @RequestParam(required = false) String filter,
                                                                                   Pageable pageable) {
        Page<CgSupplierLabelTypeDto> result = cgSupplierLabelTypeFacade.list(filter, pageable);
        return ResponseEntity.ok(result.map(cgSupplierLabelMapper::toVo));
    }

    @GetMapping("querysupplierlabelrefer")
    @Operation(summary = "查询标签")
    // TODO: 2020/12/15 权限
    public ResponseEntity<List<CgSupplierLabelTreeVo>> querySupplierLabelRefer(@Parameter(description = "供应商标签类型ID") @RequestParam Integer supplierLabelTypeId) {
        CgSupplierLabelQueryCriteria queryCriteria = new CgSupplierLabelQueryCriteria();
        queryCriteria.setSupplierLabelTypeId(supplierLabelTypeId);
        List<CgSupplierLabelDto> all = cgSupplierLabelFacade.list(queryCriteria);
        List<CgSupplierLabelTreeVo> result = cgSupplierLabelMapper.buildSmallTree(all);
        return ResponseEntity.ok(result);
    }


    @PostMapping("addsupplierlabels")
    @Operation(summary = "添加标签关联")
    // TODO: 2020/12/15 权限
    public ResponseEntity<?> addSupplierLabels(@Parameter(description = "供应商ID") @RequestParam Integer id,
                                               @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "标签ID集合") @RequestBody Set<Integer> supplierLabelId) {
        cgSupplierFacade.addSupplierLabels(id,supplierLabelId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("removesupplierlabels")
    @Operation(summary = "删除标签关联")
    // TODO: 2020/12/15 权限
    public ResponseEntity<?> removeSupplierLabels(@Parameter(description = "供应商ID") @RequestParam Integer id,
                                                  @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "标签ID集合") @RequestBody Set<Integer> supplierLabelId) {
        cgSupplierFacade.removeSupplierLabels(id,supplierLabelId);
        return ResponseEntity.ok().build();
    }

}
