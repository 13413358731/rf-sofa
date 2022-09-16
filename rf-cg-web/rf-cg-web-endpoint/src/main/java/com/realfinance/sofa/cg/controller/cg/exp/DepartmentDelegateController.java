package com.realfinance.sofa.cg.controller.cg.exp;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.cg.core.facade.CgDrawExpertFacade;
import com.realfinance.sofa.cg.core.facade.CgExpertLabelFacade;
import com.realfinance.sofa.cg.core.facade.CgExpertLabelTypeFacade;
import com.realfinance.sofa.cg.core.model.CgDrawExpertDto;
import com.realfinance.sofa.cg.core.model.CgDrawExpertSaveDto;
import com.realfinance.sofa.cg.model.cg.CgDrawExpertQueryCriteriaRequest;
import com.realfinance.sofa.cg.model.cg.CgDrawExpertVo;
import com.realfinance.sofa.cg.service.mapstruct.CgDrawExpertMapper;
import com.realfinance.sofa.cg.service.mapstruct.CgExpertLabelMapper;
import com.realfinance.sofa.cg.service.mapstruct.DepartmentMapper;
import com.realfinance.sofa.cg.service.mapstruct.UserMapper;
import com.realfinance.sofa.cg.util.DataRuleHelper;
import com.realfinance.sofa.flow.facade.FlowFacade;
import com.realfinance.sofa.system.facade.SystemQueryFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Set;

@RestController
@Tag(name = "部门委派")
@RequestMapping("/cg/core/departmentdelegate")
public class DepartmentDelegateController {
    private static final Logger log = LoggerFactory.getLogger(DepartmentDelegateController.class);

    public static final String MENU_CODE_ROOT = "departmentdelegate";
    public static final String MENU_CODE_VIEW = MENU_CODE_ROOT + ":view";

    @SofaReference(interfaceType = CgDrawExpertFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgDrawExpertFacade cgDrawExpertFacade;
    @SofaReference(interfaceType = CgExpertLabelFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgExpertLabelFacade cgExpertLabelFacade;
    @SofaReference(interfaceType = CgExpertLabelTypeFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgExpertLabelTypeFacade cgExpertLabelTypeFacade;
    @SofaReference(interfaceType = FlowFacade.class, uniqueId = "${service.rf-flow.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private FlowFacade flowFacade;
    @SofaReference(interfaceType = SystemQueryFacade.class, uniqueId = "${service.rf-system.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private SystemQueryFacade systemQueryFacade;

    @Resource
    private DataRuleHelper dataRuleHelper;

    @Resource
    private CgDrawExpertMapper cgDrawExpertMapper;

    @Resource
    private CgExpertLabelMapper cgExpertLabelMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private DepartmentMapper departmentMapper;

    @GetMapping("list")
    @Operation(summary = "查询专家抽取主表")
    public ResponseEntity<Page<CgDrawExpertVo>> list(CgDrawExpertQueryCriteriaRequest queryCriteria,
                                                     Pageable pageable) {
        Page<CgDrawExpertDto> result = cgDrawExpertFacade.list(queryCriteria, pageable);
        return ResponseEntity.ok(result.map(cgDrawExpertMapper::toVo));
    }

    @PostMapping("save")
    @Operation(summary = "保存专家抽取主表")
    public ResponseEntity<Integer> save(@Validated(CgDrawExpertVo.Save.class)
                                        @RequestBody CgDrawExpertVo vo) {
        CgDrawExpertSaveDto saveDto = cgDrawExpertMapper.toSaveDto(vo);
        Integer id = cgDrawExpertFacade.save(saveDto);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("delete")
    @Operation(summary = "删除专家抽取主表")
    public ResponseEntity<?> delete(@Parameter(description = "专家ID") @RequestParam Set<Integer> id) {
        cgDrawExpertFacade.delete(id);
        return ResponseEntity.ok().build();
    }
    
    



}
