package com.realfinance.sofa.cg.controller.cg.core;


import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.cg.core.facade.CgParameterFacade;
import com.realfinance.sofa.cg.core.model.CgParameterQueryCriteria;
import com.realfinance.sofa.cg.core.model.ParameterDto;
import com.realfinance.sofa.cg.core.model.ParameterSaveDto;
import com.realfinance.sofa.cg.model.cg.ParameterVo;
import com.realfinance.sofa.cg.service.mapstruct.ParameterMapper;
import io.swagger.v3.oas.annotations.Operation;
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

@RestController
@Tag(name = "参数设置")
@RequestMapping("/cg/core/parameter")
public class ParameterController {

    private static final Logger log = LoggerFactory.getLogger(ParameterController.class);


    //TODO  不知是否需要设置权限
    public static final String MENU_CODE_ROOT = "parameter";
    public static final String MENU_CODE_VIEW = MENU_CODE_ROOT + ":view";
    public static final String MENU_CODE_SAVE = MENU_CODE_ROOT + ":save";

    @SofaReference(interfaceType = CgParameterFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgParameterFacade cgParameterFacade;
    @Resource
    private ParameterMapper parameterMapper;

    @GetMapping("list")
    @Operation(summary = "列表")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.ParameterController).MENU_CODE_VIEW)")
    public ResponseEntity<Page<ParameterVo>> list(Pageable pageable, @ParameterObject CgParameterQueryCriteria queryCriteria) {
        Page<ParameterDto> page = cgParameterFacade.list(pageable, queryCriteria);
        return ResponseEntity.ok(page.map(parameterMapper::toVo));
    }


    @PostMapping("save")
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.ParameterController).MENU_CODE_SAVE)")
    public ResponseEntity<Integer> save(@RequestBody ParameterSaveDto saveDto) {
        Integer id = cgParameterFacade.save(saveDto);
        return ResponseEntity.ok(id);
    }
}
