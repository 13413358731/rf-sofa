package com.realfinance.sofa.cg.controller.cg.sup;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.cg.model.cg.*;
import com.realfinance.sofa.cg.service.mapstruct.*;
import com.realfinance.sofa.cg.sup.facade.CgSupplierAssessmentFacade;
import com.realfinance.sofa.cg.sup.model.CgSupplierAssessmentDetailsDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierAssessmentDetailsSaveDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierAssessmentDto;
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
@Tag(name = "供应商考核")
@RequestMapping("/cg/sup/supplierassessment")
public class SupplierAssessmentController {
    private static final Logger log = LoggerFactory.getLogger(SupplierAssessmentController.class);

    @SofaReference(interfaceType = CgSupplierAssessmentFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgSupplierAssessmentFacade cgSupplierAssessmentFacade;

    @Resource
    private CgSupplierAssessmentMapper cgSupplierAssessmentMapper;

    @GetMapping("list")
    @Operation(summary = "查询供应商考核指标主表")
    public ResponseEntity<Page<CgSupplierAssessmentVo>> list(CgSupplierAssessmentQueryCriteriaRequest queryCriteria,
                                                        Pageable pageable) {
        Page<CgSupplierAssessmentDto> result = cgSupplierAssessmentFacade.list(queryCriteria, pageable);
        return ResponseEntity.ok(result.map(cgSupplierAssessmentMapper::toVo));
    }

    @PostMapping("save")
    @Operation(summary = "保存供应商考核主表")
    public ResponseEntity<Integer> save(@Validated(CgSupplierAssessmentVo.Save.class)
                                           @RequestBody CgSupplierAssessmentVo vo) {
        CgSupplierAssessmentDetailsSaveDto saveDto = cgSupplierAssessmentMapper.toSaveDto(vo);
        Integer id = cgSupplierAssessmentFacade.save(saveDto);
        return ResponseEntity.ok(id);
    }

    @GetMapping("getdetailsbyid")
    @Operation(summary = "查询供应商考核详情")
    public ResponseEntity<CgSupplierAssessmentVo> getDetailsById(@Parameter(description = "专家抽取主表id") @RequestParam Integer id) {
        CgSupplierAssessmentDetailsDto result = cgSupplierAssessmentFacade.getDetailsById(id);
        return ResponseEntity.ok(cgSupplierAssessmentMapper.toVo(result));
    }

    @DeleteMapping("delete")
    @Operation(summary = "删除供应商考核主表")
    public ResponseEntity<?> delete(@Parameter(description = "专家ID") @RequestParam Set<Integer> id) {
        cgSupplierAssessmentFacade.delete(id);
        return ResponseEntity.ok().build();
    }
}
