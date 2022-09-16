package com.realfinance.sofa.cg.controller.cg.sup;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.cg.controller.flow.FlowApi;
import com.realfinance.sofa.cg.model.cg.*;
import com.realfinance.sofa.cg.service.mapstruct.CgSupplierAssessmentIndicatorMapper;
import com.realfinance.sofa.cg.service.mapstruct.CgSupplierAssessmentMapper;
import com.realfinance.sofa.cg.service.mapstruct.CgSupplierEvaluationSheetMapper;
import com.realfinance.sofa.cg.sup.facade.CgSupplierAssessmentFacade;
import com.realfinance.sofa.cg.sup.facade.CgSupplierEvaluationSheetFacade;
import com.realfinance.sofa.cg.sup.model.*;
import com.realfinance.sofa.common.datascope.DataScopeUtils;
import com.realfinance.sofa.flow.facade.FlowFacade;
import com.realfinance.sofa.flow.model.FlowCallbackRequest;
import com.realfinance.sofa.flow.model.FlowCallbackResponse;
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
@Tag(name = "供应商评估样表")
@RequestMapping("/cg/sup/supplierEvaluationSheet")
public class SupplierEvaluationSheetController implements FlowApi {
    private static final Logger log = LoggerFactory.getLogger(SupplierEvaluationSheetController.class);

    public static final String MENU_CODE_ROOT = "supevaluation";

    @SofaReference(interfaceType = CgSupplierEvaluationSheetFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgSupplierEvaluationSheetFacade cgSupplierEvaluationSheetFacade;
    @SofaReference(interfaceType = CgSupplierAssessmentFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgSupplierAssessmentFacade cgSupplierAssessmentFacade;
    @SofaReference(interfaceType = FlowFacade.class, uniqueId = "${service.rf-flow.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private FlowFacade flowFacade;

    @Resource
    private CgSupplierEvaluationSheetMapper cgSupplierEvaluationSheetMapper;
    @Resource
    private CgSupplierAssessmentMapper cgSupplierAssessmentMapper;
    @Resource
    private CgSupplierAssessmentIndicatorMapper cgSupplierAssessmentIndicatorMapper;

    @GetMapping("list")
    @Operation(summary = "查询供应商评估样表")
    public ResponseEntity<Page<CgSupplierEvaluationSheetMainVo>> list(CgSupplierEvaluationSheetMainQueryCriteriaRequest queryCriteria,
                                                        Pageable pageable) {
        Page<CgSupplierEvaluationSheetMainDto> result = cgSupplierEvaluationSheetFacade.list(queryCriteria, pageable);
        return ResponseEntity.ok(result.map(cgSupplierEvaluationSheetMapper::toVo));
    }

    @GetMapping("queryAssessmentrefer")
    @Operation(summary = "查询考核项参照")
    public ResponseEntity<Page<CgSupplierAssessmentVo>> queryAssessmentRefer(CgSupplierAssessmentQueryCriteriaRequest queryCriteria,
                                                                                      Pageable pageable) {
        Page<CgSupplierAssessmentDto> result = cgSupplierAssessmentFacade.queryRefer(queryCriteria,pageable);
        for (CgSupplierAssessmentDto dto : result) {
            dto.setId(null);
        }
//        for (CgSupplierAssessmentDto cgSupplierAssessmentDto : result) {
//            if(cgSupplierAssessmentDto.getId()!=null){
//                cgSupplierAssessmentDto.setId(null);
//            }
//        }
        return ResponseEntity.ok(result.map(cgSupplierAssessmentMapper::toVo));
    }

    @GetMapping("queryAssessmentIndicatorrefer")
    @Operation(summary = "查询考核指标参照")
    public ResponseEntity<Page<CgSupplierAssessmentIndicatorVo>> queryAssessmentRefer(CgSupplierAssessmentIndicatorQueryCriteriaRequest queryCriteria,
                                                                             Pageable pageable) {
        Page<CgSupplierAssessmentIndicatorDto> result = cgSupplierAssessmentFacade.queryIndicatorRefer(queryCriteria,pageable);
        return ResponseEntity.ok(result.map(cgSupplierAssessmentIndicatorMapper::toVo));
    }

    @PostMapping("save")
    @Operation(summary = "保存供应商评估样表")
    public ResponseEntity<Integer> save(@Validated(CgSupplierEvaluationSheetMainVo.Save.class)
                                           @RequestBody CgSupplierEvaluationSheetMainVo vo) {
        CgSupplierEvaluationSheetDetailsSaveDto saveDto = cgSupplierEvaluationSheetMapper.toSaveDto(vo);
        Integer id = cgSupplierEvaluationSheetFacade.save(saveDto);
        return ResponseEntity.ok(id);
    }

    @GetMapping("getdetailsbyid")
    @Operation(summary = "查询供应商评估样表详情")
    public ResponseEntity<CgSupplierEvaluationSheetMainVo> getDetailsById(@Parameter(description = "专家抽取主表id") @RequestParam Integer id) {
        CgSupplierEvaluationSheetDetailsDto result = cgSupplierEvaluationSheetFacade.getDetailsById(id);
        CgSupplierEvaluationSheetMainVo vo = cgSupplierEvaluationSheetMapper.toVo(result);
        vo.setFlowInfo(getFlowInfo(id.toString()));
        return ResponseEntity.ok(vo);
    }

    @DeleteMapping("delete")
    @Operation(summary = "删除供应商评估样表")
    public ResponseEntity<?> delete(@Parameter(description = "专家ID") @RequestParam Set<Integer> id) {
        cgSupplierEvaluationSheetFacade.delete(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<FlowCallbackResponse> flowCallback(@RequestBody FlowCallbackRequest request) {
        DataScopeUtils.installTenantId(request.getTenantId());
        if (request.typeIs(FlowCallbackRequest.TYPE_START)) {
            cgSupplierEvaluationSheetFacade.updateStatus(Integer.parseInt(request.getBusinessKey()),"SUBMITTED");
        } else if (request.typeIs(FlowCallbackRequest.TYPE_DELETE)) {
            cgSupplierEvaluationSheetFacade.updateStatus(Integer.parseInt(request.getBusinessKey()),"EDIT");
        } else if (request.typeIs(FlowCallbackRequest.TYPE_END)) {
            cgSupplierEvaluationSheetFacade.updateStatus(Integer.parseInt(request.getBusinessKey()),"PASS");
        }
        return ResponseEntity.ok(FlowCallbackResponse.ok());
    }

    @Override
    public String getBusinessCode() {
        return MENU_CODE_ROOT;
    }

    @Override
    public FlowFacade getFlowFacade() {
        return flowFacade;
    }
}
