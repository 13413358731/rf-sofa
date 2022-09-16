package com.realfinance.sofa.cg.controller.cg.core;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.realfinance.sofa.cg.controller.flow.FlowApi;
import com.realfinance.sofa.cg.core.facade.CgProjectExecutionFacade;
import com.realfinance.sofa.cg.core.facade.CgProjectFacade;
import com.realfinance.sofa.cg.core.facade.CgRequirementFacade;
import com.realfinance.sofa.cg.core.model.*;
import com.realfinance.sofa.cg.model.cg.*;
import com.realfinance.sofa.cg.model.flow.FlowTaskVo;
import com.realfinance.sofa.cg.security.AuthInfo;
import com.realfinance.sofa.cg.service.mapstruct.*;
import com.realfinance.sofa.cg.sup.facade.CgBusinessProjectFacade;
import com.realfinance.sofa.cg.sup.facade.CgBusinessReplyFacade;
import com.realfinance.sofa.cg.sup.facade.CgSupplierFacade;
import com.realfinance.sofa.cg.sup.model.*;
import com.realfinance.sofa.cg.util.DataRuleHelper;
import com.realfinance.sofa.cg.util.LinkUtils;
import com.realfinance.sofa.cg.util.POIUtils;
import com.realfinance.sofa.common.filetoken.FileToken;
import com.realfinance.sofa.common.filetoken.FileTokens;
import com.realfinance.sofa.flow.facade.FlowFacade;
import com.realfinance.sofa.flow.model.FlowCallbackRequest;
import com.realfinance.sofa.flow.model.FlowCallbackResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@Tag(name = "采购方案执行")
@RequestMapping("/cg/core/projexec")
public class ProjectExecutionController implements FlowApi {

    private static final Logger log = LoggerFactory.getLogger(ProjectExecutionController.class);

    public static final String MENU_CODE_ROOT = "purprojexec";
    public static final String MENU_CODE_VIEW = MENU_CODE_ROOT + ":view";
    public static final String MENU_CODE_SAVE = MENU_CODE_ROOT + ":save";
    public static final String MENU_CODE_RETURN = MENU_CODE_ROOT + ":return";  //退回

    @SofaReference(interfaceType = CgProjectExecutionFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgProjectExecutionFacade cgProjectExecutionFacade;
    @SofaReference(interfaceType = CgBusinessReplyFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgBusinessReplyFacade cgBusinessReplyFacade;
    @SofaReference(interfaceType = CgBusinessProjectFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgBusinessProjectFacade cgBusinessProjectFacade;
    @SofaReference(interfaceType = CgSupplierFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgSupplierFacade cgSupplierFacade;

    @SofaReference(interfaceType = CgProjectFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt", timeout = 300000))
    private CgProjectFacade cgProjectFacade;

    @SofaReference(interfaceType = CgRequirementFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgRequirementFacade cgRequirementFacade;

    @SofaReference(interfaceType = FlowFacade.class, uniqueId = "${service.rf-flow.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private FlowFacade flowFacade;

    @Resource
    private CgProjectMapper cgProjectMapper;

    @Resource
    private CgProjectExecutionMapper cgProjectExecutionMapper;
    @Resource
    private CgSupplierMapper cgSupplierMapper;

    @Resource
    private DataRuleHelper dataRuleHelper;

    @Resource
    private ObjectMapper objectMapper;

    @Resource
    private CgProjectEvalMapper cgProjectEvalMapper;

    @Resource
    private CgRequirementMapper cgRequirementMapper;

    @GetMapping("list")
    @Operation(summary = "查询采购方案执行列表")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.ProjectExecutionController).MENU_CODE_VIEW)")
    public ResponseEntity<Page<CgProjectExecutionVo>> list(@ParameterObject CgProjectExecutionQueryCriteria queryCriteria,
                                                           Pageable pageable) {
        dataRuleHelper.installDataRule(MENU_CODE_VIEW);
        Page<CgProjectExecutionDto> result = cgProjectExecutionFacade.list(queryCriteria, PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(), Sort.by("modifiedTime").descending()));
        return ResponseEntity.ok(result.map(cgProjectExecutionMapper::toVo));
    }

    @GetMapping("getdetailsbyid")
    @Operation(summary = "查询采购方案执行详情")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.ProjectExecutionController).MENU_CODE_VIEW)")
    public ResponseEntity<CgProjectExecutionVo> getDetailsById(@Parameter(description = "ID") @RequestParam Integer id) {
        dataRuleHelper.installDataRule(MENU_CODE_VIEW);
        CgProjectExecutionDetailsDto result = cgProjectExecutionFacade.getDetailsById(id);
        CgProjectExecutionVo vo = cgProjectExecutionMapper.toVo(result);
        List<CgAttVo> projectExecutionAtts = vo.getProjectExecutionAtts();
        if(projectExecutionAtts!=null && !projectExecutionAtts.isEmpty()){
            for (CgAttVo projectExecutionAtt : projectExecutionAtts) {
                FileToken fileToken = FileTokens.create(projectExecutionAtt.getPath(),projectExecutionAtt.getName());
                projectExecutionAtt.setLink(LinkUtils.createFileDownloadLink(fileToken));
            }
        }
//        if(vo.getProjectExecutionAtts()!=null){
//            for(CgAttVo cgAttVo:vo.getProjectExecutionAtts()){
//                FileToken fileToken = FileTokens.create(cgAttVo.getPath(), cgAttVo.getName());
//                cgAttVo.setLink(LinkUtils.createFileDownloadLink(fileToken));
//            }
//        }
        setReplyInfo(vo);
        vo.setFlowInfo(getFlowInfo(id.toString()));
        return ResponseEntity.ok(vo);
    }

    @GetMapping("getreqdetailsbyid")
    @Operation(summary = "查询采购需求详情")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.RequirementController).MENU_CODE_VIEW)")
    public ResponseEntity<CgRequirementVo> getRequirementDetailsById(@Parameter(description = "采购方案ID") @RequestParam Integer id) {
        dataRuleHelper.installDataRule(MENU_CODE_VIEW);
        CgRequirementDetailsDto result = cgProjectFacade.getRequirementDetailsById(id);
        CgRequirementVo vo = cgRequirementMapper.toVo(result);
        //vo.setFlowInfo(getFlowInfo(id.toString()));
        return ResponseEntity.ok(vo);
    }

    @GetMapping("getcaigoudetailsbyid")
    @Operation(summary = "查询采购方案详情")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.ProjectExecutionController).MENU_CODE_VIEW)")
    public ResponseEntity<CgProjectVo> getCaiGouDetailsById(@Parameter(description = "采购方案ID") @RequestParam Integer id) {
        dataRuleHelper.installDataRule(MENU_CODE_VIEW);
        CgProjectDetailsDto result = cgProjectFacade.getDetailsById(id);
        CgProjectVo vo = cgProjectMapper.toVo(result);
        //vo.setFlowInfo(getFlowInfo(id.toString()));
        return ResponseEntity.ok(vo);
    }

    @GetMapping("querysupplierrefer")
    @Operation(summary = "查询供应商参照")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.ProjectController).MENU_CODE_SAVE)")
    public ResponseEntity<Page<CgSupplierVo>> querySupplierRefer(CgSupplierQueryCriteriaRequest queryCriteria,
                                                                 Pageable pageable) {
        Page<CgSupplierDto> result = cgSupplierFacade.list(queryCriteria, pageable);
        return ResponseEntity.ok(result.map(cgSupplierMapper::toVo));
    }

    @GetMapping("querysuppliercontacts")
    @Operation(summary = "查询供应商联系人列表")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.ProjectController).MENU_CODE_SAVE)")
    public ResponseEntity<List<CgSupplierContactsDto>> querySupplierContacts(Integer supplierId) {
        CgSupplierDetailsDto cgSupplierDetailsDto = cgSupplierFacade.getDetailsById(supplierId);
        List<CgSupplierContactsDto> contacts = cgSupplierDetailsDto.getContacts();
        return ResponseEntity.ok(contacts);
    }


    @PostMapping("save")
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.ProjectExecutionController).MENU_CODE_SAVE)")
    public ResponseEntity<Integer> save(@Validated @RequestBody CgProjectExecutionVo vo) {
        CgProjectExecutionDetailsSaveDto saveDto = cgProjectExecutionMapper.toSaveDto(vo);
        Integer id = cgProjectExecutionFacade.save(saveDto);
        return ResponseEntity.ok(id);
    }

    @GetMapping("getreplydetailsbyreplyid")
    @Operation(summary = "查询报价应答详情")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.ProjectExecutionController).MENU_CODE_VIEW)")
    public ResponseEntity<CgBusinessReplyVo> getReplyDetailsByReplyId(@Parameter(description = "应答ID") @RequestParam Integer replyId) {
        CgBusinessReplyDetailsDto result = cgBusinessReplyFacade.getDetailsById(replyId);
        return ResponseEntity.ok(cgProjectExecutionMapper.toVo(result));
    }

    @PostMapping("startstep")
    @Operation(summary = "开始方案执行环节")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.ProjectExecutionController).MENU_CODE_SAVE)")
    public ResponseEntity<Void> startStep(@Parameter(description = "环节ID") @RequestParam Integer stepId) {
        cgProjectExecutionFacade.startStep(stepId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("endstep")
    @Operation(summary = "结束方案执行环节")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.ProjectExecutionController).MENU_CODE_SAVE)")
    public ResponseEntity<Void> endStep(@Parameter(description = "环节ID") @RequestParam Integer stepId,
                                        @RequestBody(required = false) @Validated(FlowTaskVo.Complete.class) FlowTaskVo flowTaskVo) {
        cgProjectExecutionFacade.endStep(stepId);
        if (flowTaskVo!=null){
            FlowApi.super.flowCompleteTask(flowTaskVo);
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("invalidbid")
    @Operation(summary = "废标")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.ProjectExecutionController).MENU_CODE_SAVE)")
    public ResponseEntity<Void> invalidBid(@RequestBody Map<String, String> body,
                                           @Parameter(description = "ID") @RequestParam String processInstanceId) {
        Integer id = Integer.parseInt(body.get("id"));
        String invalidBidReason = body.get("invalidReason");
        CgProjectExecutionDto dto = cgProjectExecutionFacade.getById(id);
        String projectId = dto.getProject().getId().toString();
        cgBusinessProjectFacade.updateProjectStatus(projectId,"作废");
        cgProjectExecutionFacade.invalidBid(id, invalidBidReason);
        //结束通知任务审批流
        FlowApi.super.flowDeleteProcess(processInstanceId,body);
        return ResponseEntity.ok().build();
    }

    @PostMapping("savesupplier")
    @Operation(summary = "保存供应商看板")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.ProjectExecutionController).MENU_CODE_SAVE)")
    public ResponseEntity<Integer> saveSupplier(@RequestParam Integer id, @RequestBody CgProjectExecutionSupVo projectExecutionSupVo) {
        CgProjectExecutionSupDto dto = cgProjectExecutionMapper.cgProjectExecutionSupVoToCgProjectExecutionSupDto(projectExecutionSupVo);
        Integer cgProjectExecutionSupId = cgProjectExecutionFacade.saveSupplier(id,dto);
        return ResponseEntity.ok(cgProjectExecutionSupId);
    }

    @PostMapping("invalidsupplier")
    @Operation(summary = "作废供应商")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.ProjectExecutionController).MENU_CODE_SAVE)")
    public ResponseEntity<Integer> invalidSupplier(@RequestParam Integer id, @RequestParam Integer projectExecutionSupId) {
        cgProjectExecutionFacade.invalidSupplier(id,projectExecutionSupId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("saveeval")
    @Operation(summary = "保存评分大项")
    public ResponseEntity<Integer> save(@RequestBody ArrayList<CgProjectEvalVo> vo) {
        for (CgProjectEvalVo cgProjectEvalVo : vo) {
            CgProjectEvalDto saveDto = cgProjectEvalMapper.toSaveDto(cgProjectEvalVo);
            Integer id = cgProjectExecutionFacade.saveEval(saveDto);
        }
        return ResponseEntity.ok(200);
    }

    @PostMapping("return")
    @Operation(summary = "退回需求")
    //@PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.ProjectExecutionController).MENU_CODE_RETURN)")
    public ResponseEntity<Void> returnReq(@RequestBody CgProjectExecutionVo vo,
                                          @AuthenticationPrincipal AuthInfo authInfo){
        String reason = String.format("%s(%s) 于 %s 退回，原因：%s",
                authInfo.getUser().getRealname(),authInfo.getUser().getUsername(),
                LocalDateTime.now().toString(),vo.getReason());
        //dataRuleHelper.installDataRule(MENU_CODE_RETURN);
        //方案执行退回 FAZXTH
        cgRequirementFacade.updateAcceptStatus(vo.getProject().getRequirement().getId(),"FAZXTH",reason);
        vo.setReturnReq(true);
        CgProjectExecutionDetailsSaveDto saveDto = cgProjectExecutionMapper.toSaveDto(vo);
        Integer id = cgProjectExecutionFacade.save(saveDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("export")
    @Operation(summary = "导出采购方案")
    public ResponseEntity<byte[]> exportData(@Parameter(description = "采购方案ID") @RequestParam Integer projectId){
        CgProjectDetailsDto dto = cgProjectFacade.getDetailsById(projectId);
        CgRequirementVo cgRequirementVo = cgRequirementMapper.toVo(dto.getRequirement());
        CgProjectVo vo = cgProjectMapper.toVo(dto);
        vo.setRequirement(cgRequirementVo);
        return POIUtils.project2Excel(vo);
    }

    @Override
    public ResponseEntity<String> flowStartProcess(@Parameter(description = "采购方案id") @RequestParam Integer id,
                                                   @RequestBody(required = false) Map<String, String> formData) {
        //根据采购方案id查询出采购执行id和采购方案创建人id
        Map<String, String> map = cgProjectExecutionFacade.listProjectExecutionId(id);
        String processInstanceId = getFlowFacade().startProcessToUserId(getBusinessCode(), map.get("projectExecutionId"), formData,Integer.parseInt(map.get("createdUserId")),map.get("name"));
        return ResponseEntity.ok(processInstanceId);
    }

    /**
     * 设置应答报价信息
     * @param vo
     */
    private void setReplyInfo(CgProjectExecutionVo vo) {
        try {
            CgBusinessReplyQueryCriteria queryCriteria = new CgBusinessReplyQueryCriteria();
            queryCriteria.setBusinessProjectId(vo.getProject().getId().toString());
            queryCriteria.setNeedQuote(true);
            List<CgBusinessReplyDto> replies = cgBusinessReplyFacade
                    .list(queryCriteria);
            if (replies != null && !replies.isEmpty()) {
                int round = replies.stream().mapToInt(CgBusinessReplyDto::getQuoteRound).max().orElse(0);
                if (round > 0) {
                    ObjectNode replyInfo = objectMapper.createObjectNode();
                    replyInfo.put("round",round);
                    ArrayNode replyInfoData = replyInfo.putArray("data");
                    Map<Integer, List<CgBusinessReplyDto>> collect =
                            replies.stream().collect(Collectors.groupingBy(CgBusinessReplyDto::getSupplierId));
                    collect.forEach((k, v) -> {
                        ObjectNode datum = replyInfoData.addObject();
                        datum.put("supplierId",k);
                        vo.getProjectExecutionSups().stream()
                                .filter(e -> Objects.nonNull(e.getSupplier()))
                                .filter(e -> Objects.equals(k,e.getSupplier().getId())).findFirst()
                                .ifPresent(e -> datum.put("supplierName",e.getSupplier().getName()));
                        for (CgBusinessReplyDto cgBusinessReplyDto : v) {
                            datum.put("reply_" + cgBusinessReplyDto.getQuoteRound(),cgBusinessReplyDto.getId());
                            datum.put("reply_" + cgBusinessReplyDto.getQuoteRound() + "_totalPrice",cgBusinessReplyDto.getTotalPrice());
                        }
                    });
                    vo.setReplyInfo(replyInfo);
                }
            }
        } catch (Exception e) {
            log.error("查询供应商报价信息出错",e);
        }
    }

    @Override
    public ResponseEntity<FlowCallbackResponse> flowCallback(FlowCallbackRequest request) {
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
