package com.realfinance.sofa.cg.controller.cg.sup;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.cg.model.cg.*;
import com.realfinance.sofa.cg.service.mapstruct.CgSupEvaluationProcessMngMapper;
import com.realfinance.sofa.cg.service.mapstruct.CgSupplierEvaluationMainMapper;
import com.realfinance.sofa.cg.sup.facade.CgSupEvaluationProcessMngFacade;
import com.realfinance.sofa.cg.sup.facade.CgSupplierEvaluationFacade;
import com.realfinance.sofa.cg.sup.model.*;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@Tag(name = "供应商评估")
@RequestMapping("/cg/sup/supplierevaluation")
public class SupplierEvaluationController {
    private static final Logger log = LoggerFactory.getLogger(SupplierEvaluationController.class);

    @SofaReference(interfaceType = CgSupplierEvaluationFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgSupplierEvaluationFacade cgSupplierEvaluationFacade;
    @SofaReference(interfaceType = CgSupEvaluationProcessMngFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgSupEvaluationProcessMngFacade cgSupEvaluationProcessMngFacade;

    @Resource
    private CgSupplierEvaluationMainMapper cgSupplierEvaluationMainMapper;
    @Resource
    private CgSupEvaluationProcessMngMapper cgSupEvaluationProcessMngMapper;

    @GetMapping("list")
    @Operation(summary = "查询供应商评估主表")
    public ResponseEntity<Page<CgSupplierEvaluationMainVo>> list(CgSupplierEvaluationMainQueryCriteriaRequest queryCriteria,
                                                                 Pageable pageable) {
        Page<CgSupplierEvaluationMainDto> result = cgSupplierEvaluationFacade.list(queryCriteria, pageable);
        return ResponseEntity.ok(result.map(cgSupplierEvaluationMainMapper::toVo));
    }

    @PostMapping("edit")
    @Operation(summary = "保存供应商考核主表")
    public ResponseEntity<Integer> edit(@Validated(CgSupplierEvaluationMainVo.Save.class)
                                           @RequestBody CgSupplierEvaluationMainVo vo) {
        CgSupplierEvaluationMainDetailsSaveDto saveDto = cgSupplierEvaluationMainMapper.toSaveDto(vo);
        Integer id = cgSupplierEvaluationFacade.save(saveDto);
        return ResponseEntity.ok(id);
    }

    @PostMapping("startevaluate")
    @Operation(summary = "发起评估")
    public ResponseEntity<Integer> startevaluate(@RequestParam Integer id) {
        CgSupEvaluationProcessMngDetailsDto result = cgSupEvaluationProcessMngFacade.getDetailsById(id);
        CgSupEvaluationProcessMngVo cgSupEvaluationProcessMngVo = cgSupEvaluationProcessMngMapper.toVo(result);
        List<CgSupplierEvaluationSheetSubDto> supplierEvaluationSubDtos = new ArrayList<>();
        List<CgSupplierEvaluationSheetSubVo> supplierEvaluationSheetSubs = cgSupEvaluationProcessMngVo.getEvaluationSheetNo().getSupplierEvaluationSheetSubs();
        for (CgSupplierEvaluationSheetSubVo supplierEvaluationSheetSub : supplierEvaluationSheetSubs) {
            CgSupplierEvaluationSheetSubDto supplierEvaluationSubDto = new CgSupplierEvaluationSheetSubDto();
            supplierEvaluationSubDto.setAssessmentNo(supplierEvaluationSheetSub.getAssessmentNo());
            supplierEvaluationSubDto.setAssessmentName(supplierEvaluationSheetSub.getAssessmentName());
            supplierEvaluationSubDto.setAssessmentWeight(supplierEvaluationSheetSub.getAssessmentWeight());
            List<CgSupplierEvaluationSheetGrandsonDto> supplierEvaluationSheetGrandsonDtos = new ArrayList<>();
            for (CgSupplierEvaluationSheetGrandsonVo supplierEvaluationSheetGrandson : supplierEvaluationSheetSub.getSupplierEvaluationSheetGrandsons()) {
                CgSupplierEvaluationSheetGrandsonDto supplierEvaluationSheetGrandsonDto = new CgSupplierEvaluationSheetGrandsonDto();
                supplierEvaluationSheetGrandsonDto.setAssessmentIndicatorNo(supplierEvaluationSheetGrandson.getAssessmentIndicatorNo());
                supplierEvaluationSheetGrandsonDto.setAssessmentIndicatorName(supplierEvaluationSheetGrandson.getAssessmentIndicatorName());
                supplierEvaluationSheetGrandsonDto.setCalculation(supplierEvaluationSheetGrandson.getCalculation());
                supplierEvaluationSheetGrandsonDto.setIndicatorWeight(supplierEvaluationSheetGrandson.getIndicatorWeight());
                supplierEvaluationSheetGrandsonDtos.add(supplierEvaluationSheetGrandsonDto);
//            supplierEvaluationSubDto.setAssessmentIndicatorNo(supplierEvaluationSheetSub.getAssessmentIndicatorNo());
//            supplierEvaluationSubDto.setAssessmentIndicatorName(supplierEvaluationSheetSub.getAssessmentIndicatorName());
//            supplierEvaluationSubDto.setCalculation(supplierEvaluationSheetSub.getCalculation());
//            supplierEvaluationSubDto.setIndicatorWeight(supplierEvaluationSheetSub.getIndicatorWeight());
            }
            supplierEvaluationSubDto.setSupplierEvaluationSheetGrandsons(supplierEvaluationSheetGrandsonDtos);
            supplierEvaluationSubDtos.add(supplierEvaluationSubDto);
        }
        Integer id1 = cgSupplierEvaluationFacade.startevaluate(supplierEvaluationSubDtos,id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("getdetailsbyid")
    @Operation(summary = "查询供应商评估详情")
    public ResponseEntity<CgSupplierEvaluationMainVo> getDetailsById(@Parameter(description = "专家抽取主表id") @RequestParam Integer id) {
        CgSupplierEvaluationMainDetailsDto result = cgSupplierEvaluationFacade.getDetailsById(id);
        return ResponseEntity.ok(cgSupplierEvaluationMainMapper.toVo(result));
    }

    @DeleteMapping("delete")
    @Operation(summary = "删除供应商评估")
    public ResponseEntity<?> delete(@Parameter(description = "专家ID") @RequestParam Set<Integer> id) {
        cgSupplierEvaluationFacade.delete(id);
        return ResponseEntity.ok().build();
    }
}
