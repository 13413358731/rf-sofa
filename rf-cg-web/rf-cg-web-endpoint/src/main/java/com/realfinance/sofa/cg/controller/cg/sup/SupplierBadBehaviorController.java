package com.realfinance.sofa.cg.controller.cg.sup;


import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.cg.model.cg.*;
import com.realfinance.sofa.cg.service.mapstruct.CgSupplierBadBehaviorMapper;
import com.realfinance.sofa.cg.service.mapstruct.CgSupplierMapper;
import com.realfinance.sofa.cg.sup.facade.CgSupplierBadBehaviorFacade;
import com.realfinance.sofa.cg.sup.facade.CgSupplierExaminationFacade;
import com.realfinance.sofa.cg.sup.facade.CgSupplierFacade;
import com.realfinance.sofa.cg.sup.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Set;

@RestController
@Tag(name = "供应商不良行为")
@RequestMapping("/cg/sup/supplierbadbehavior")
public class SupplierBadBehaviorController {

    private static final Logger log = LoggerFactory.getLogger(SupplierBadBehaviorController.class);
    //TODO  不知是否需要设置权限
    public static final String MENU_CODE_ROOT = "supplierbadbehavior";
    public static final String MENU_CODE_VIEW = MENU_CODE_ROOT + ":view";
    public static final String MENU_CODE_SAVE = MENU_CODE_ROOT + ":save";
    public static final String MENU_CODE_DELETE = MENU_CODE_ROOT + ":delete";


    @SofaReference(interfaceType = CgSupplierBadBehaviorFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgSupplierBadBehaviorFacade cgSupplierBadBehaviorFacade;
    @Resource
    private CgSupplierBadBehaviorMapper cgSupplierBadBehaviorMapper;
    @SofaReference(interfaceType = CgSupplierFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgSupplierFacade cgSupplierFacade;
    @Resource
    private CgSupplierMapper cgSupplierMapper;


    @GetMapping("querysupplierrefer")
    @Operation(summary = "查询供应商参照")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<CgSupplierVo>> querySupplierRefer(CgSupplierQueryCriteriaRequest queryCriteria,
                                                                 Pageable pageable) {
        Page<CgSupplierSmallDto> result = cgSupplierFacade.queryRefer(queryCriteria, pageable);
        return ResponseEntity.ok(result.map(cgSupplierMapper::toVo));
    }


    @GetMapping("list")
    @Operation(summary = "列表")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.sup.SupplierBadBehaviorController).MENU_CODE_VIEW)")
    public ResponseEntity<Page<CgSupplierBadBehaviorVo>> list(Pageable pageable, @ParameterObject CgSupplierBadBehaviorQueryCriteria queryCriteria) {
        Page<CgSupplierBadBehaviorDto> list = cgSupplierBadBehaviorFacade.list(pageable, queryCriteria);
        Page<CgSupplierBadBehaviorVo> vo = list.map(cgSupplierBadBehaviorMapper::toVo);
        return ResponseEntity.ok(vo);
    }

    @PostMapping("save")
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.sup.SupplierBadBehaviorController).MENU_CODE_SAVE)")
    public ResponseEntity<Integer> save(@RequestBody CgSupplierBadBehaviorSaveRequest saveRequest) {
        CgSupplierBadBehaviorSaveDto saveDto = cgSupplierBadBehaviorMapper.toSaveDto(saveRequest);
        Integer id = cgSupplierBadBehaviorFacade.save(saveDto);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("delete")
    @Operation(summary = "删除")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.sup.SupplierBadBehaviorController).MENU_CODE_DELETE)")
    public ResponseEntity<?> delete(@RequestParam Set<Integer> ids) {
        cgSupplierBadBehaviorFacade.delete(ids);
        return ResponseEntity.ok().build();
    }


}
