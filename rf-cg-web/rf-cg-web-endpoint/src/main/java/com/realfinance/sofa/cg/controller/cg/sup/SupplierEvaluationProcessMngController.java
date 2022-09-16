package com.realfinance.sofa.cg.controller.cg.sup;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.cg.core.facade.CgProjectFacade;
import com.realfinance.sofa.cg.core.model.CgProjectSmallDto;
import com.realfinance.sofa.cg.model.cg.*;
import com.realfinance.sofa.cg.model.system.DepartmentTreeVo;
import com.realfinance.sofa.cg.model.system.UserQueryCriteriaRequest;
import com.realfinance.sofa.cg.model.system.UserVo;
import com.realfinance.sofa.cg.service.mapstruct.*;
import com.realfinance.sofa.cg.sup.facade.CgSupEvaluationProcessMngFacade;
import com.realfinance.sofa.cg.sup.facade.CgSupplierEvaluationFacade;
import com.realfinance.sofa.cg.sup.facade.CgSupplierEvaluationSheetFacade;
import com.realfinance.sofa.cg.sup.facade.CgSupplierFacade;
import com.realfinance.sofa.cg.sup.model.*;
import com.realfinance.sofa.system.facade.SystemQueryFacade;
import com.realfinance.sofa.system.model.DepartmentSmallTreeDto;
import com.realfinance.sofa.system.model.UserDto;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@Tag(name = "供应商评估流程管理")
@RequestMapping("/cg/sup/supevaluaprocessmng")
public class SupplierEvaluationProcessMngController {
    private static final Logger log = LoggerFactory.getLogger(SupplierEvaluationProcessMngController.class);

    @SofaReference(interfaceType = CgSupEvaluationProcessMngFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgSupEvaluationProcessMngFacade cgSupEvaluationProcessMngFacade;
    @SofaReference(interfaceType = SystemQueryFacade.class, uniqueId = "${service.rf-system.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private SystemQueryFacade systemQueryFacade;
    @SofaReference(interfaceType = CgProjectFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgProjectFacade cgProjectFacade;
    @SofaReference(interfaceType = CgSupplierEvaluationSheetFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgSupplierEvaluationSheetFacade cgSupplierEvaluationSheetFacade;
    @SofaReference(interfaceType = CgSupplierFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgSupplierFacade cgSupplierFacade;

    @Resource
    private CgSupEvaluationProcessMngMapper cgSupEvaluationProcessMngMapper;
    @Resource
    private DepartmentMapper departmentMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private CgProjectMapper cgProjectMapper;
    @Resource
    private CgSupplierEvaluationSheetMapper cgSupplierEvaluationSheetMapper;
    @Resource
    private CgSupplierMapper cgSupplierMapper;



    @GetMapping("list")
    @Operation(summary = "查询供应商评估流程")
    public ResponseEntity<Page<CgSupEvaluationProcessMngVo>> list(CgSupEvaluationProcessMngQueryCriteriaRequest queryCriteria,
                                                                  Pageable pageable) {
        Page<CgSupEvaluationProcessMngDto> result = cgSupEvaluationProcessMngFacade.list(queryCriteria, pageable);
        return ResponseEntity.ok(result.map(cgSupEvaluationProcessMngMapper::toVo));
    }

    @PostMapping("save")
    @Operation(summary = "保存供应商评估流程管理")
    public ResponseEntity<Integer> save(@Validated(CgSupEvaluationProcessMngVo.Save.class)
                                           @RequestBody CgSupEvaluationProcessMngVo vo) {
        CgSupEvaluationProcessMngDetailsSaveDto saveDto = cgSupEvaluationProcessMngMapper.toSaveDto(vo);
        Integer id = cgSupEvaluationProcessMngFacade.save(saveDto);
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
        Integer id1 = cgSupEvaluationProcessMngFacade.startevaluate(supplierEvaluationSubDtos,id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("getdetailsbyid")
    @Operation(summary = "查询供应商评估详情")
    public ResponseEntity<CgSupEvaluationProcessMngVo> getDetailsById(@Parameter(description = "供应商评估流程管理id") @RequestParam Integer id) {
        CgSupEvaluationProcessMngDetailsDto result = cgSupEvaluationProcessMngFacade.getDetailsById(id);
        CgSupEvaluationProcessMngVo resultVo = cgSupEvaluationProcessMngMapper.toVo(result);
        return ResponseEntity.ok(resultVo);
    }

    @GetMapping("querydepartmentrefer")
    @Operation(summary = "查询评估部门参照",description = "返回部门树，非系统租户只能返回当前登录的法人下的部门树")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<DepartmentTreeVo>> queryDepartmentRefer() {
        List<DepartmentSmallTreeDto> result = systemQueryFacade.queryDepartmentRefer();
        return ResponseEntity.ok(result.stream().map(departmentMapper::departmentSmallTreeDto2DepartmentTreeVo).collect(Collectors.toList()));
    }

    @GetMapping("queryuserrefer")
    @Operation(summary = "查询用户参照", description = "非系统租户只返回当前登录的法人下的用户")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<UserVo>> queryUserRefer(UserQueryCriteriaRequest queryCriteria,
                                                       Pageable pageable) {
        Page<UserDto> result = systemQueryFacade.queryUserRefer(queryCriteria,pageable);
        return ResponseEntity.ok(result.map(userMapper::userDto2UserVo));
    }

    @GetMapping("queryprojectrefer")
    @Operation(summary = "查询采购方案参照")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<CgProjectVo>> queryProjectRefer(CgProjectQueryCriteriaRequest queryCriteria,
                                                               Pageable pageable) {
        Page<CgProjectSmallDto> result = cgProjectFacade.queryRefer(queryCriteria,pageable);
        return ResponseEntity.ok(result.map(cgProjectMapper::toVo));
    }

    @GetMapping("queryevaluationsheetrefer")
    @Operation(summary = "查询评估表参照")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<CgSupplierEvaluationSheetMainVo>> querySupplierEvaluationSheetMainRefer(CgSupplierEvaluationSheetMainQueryCriteriaRequest queryCriteria,
                                                               Pageable pageable) {
        Page<CgSupplierEvaluationSheetMainDto> result = cgSupplierEvaluationSheetFacade.queryRefer(queryCriteria,pageable);
        return ResponseEntity.ok(result.map(cgSupplierEvaluationSheetMapper::toVo));
    }

    @GetMapping("queryesupplierrefer")
    @Operation(summary = "查询供应商参照")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<CgSupplierVo>> querySupplierRefer(CgSupplierQueryCriteriaRequest queryCriteria,
                                                                                                       Pageable pageable) {
        Page<CgSupplierSmallDto> result = cgSupplierFacade.queryRefer(queryCriteria,pageable);
        return ResponseEntity.ok(result.map(cgSupplierMapper::toVo));
    }

    @DeleteMapping("delete")
    @Operation(summary = "删除供应商评估")
    public ResponseEntity<?> delete(@Parameter(description = "专家ID") @RequestParam Set<Integer> id) {
        cgSupEvaluationProcessMngFacade.delete(id);
        return ResponseEntity.ok().build();
    }
}
