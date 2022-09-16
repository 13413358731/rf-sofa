package com.realfinance.sofa.cg.controller.cg.sup;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.cg.controller.flow.FlowApi;
import com.realfinance.sofa.cg.model.cg.CgSupplierExaminationAttachmentVo;
import com.realfinance.sofa.cg.model.cg.CgSupplierExaminationQueryCriteriaRequest;
import com.realfinance.sofa.cg.model.cg.CgSupplierExaminationVo;
import com.realfinance.sofa.cg.service.mapstruct.CgSupplierExaminationMapper;
import com.realfinance.sofa.cg.sup.facade.CgSupplierExaminationFacade;
import com.realfinance.sofa.cg.sup.model.CgSupplierExaminationDetailsDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierExaminationDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierExaminationQueryCriteria;
import com.realfinance.sofa.cg.util.DataRuleHelper;
import com.realfinance.sofa.cg.util.LinkUtils;
import com.realfinance.sofa.common.datascope.DataScopeUtils;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@Tag(name = "供应商审核")
@RequestMapping("/cg/sup/supplieraudit")
public class SupplierAuditController implements FlowApi {
    private static final Logger log = LoggerFactory.getLogger(SupplierAuditController.class);

    public static final String MENU_CODE_ROOT = "supplieraudit";
    public static final String MENU_CODE_VIEW = MENU_CODE_ROOT + ":view";
    public static final String MENU_CODE_DELETE = MENU_CODE_ROOT + ":delete";
    public static final String MENU_CODE_APPROVE = MENU_CODE_ROOT + ":approve";

    @SofaReference(interfaceType = CgSupplierExaminationFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgSupplierExaminationFacade cgSupplierExaminationFacade;
    @SofaReference(interfaceType = FlowFacade.class, uniqueId = "${service.rf-flow.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private FlowFacade flowFacade;

    @Resource
    private DataRuleHelper dataRuleHelper;

    @Resource
    private CgSupplierExaminationMapper cgSupplierExaminationMapper;

    @GetMapping("list")
    @Operation(summary = "查询供应商审核列表")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.sup.SupplierAuditController).MENU_CODE_VIEW)")
    public ResponseEntity<Page<CgSupplierExaminationVo>> list(CgSupplierExaminationQueryCriteriaRequest queryCriteria,
                                                              Pageable pageable) {
        queryCriteria.setCategoryIn(Stream.of("INITIAL","MODIFY_FROM_PORTAL").collect(Collectors.toSet()));
        dataRuleHelper.installDataRule(MENU_CODE_VIEW);
        Page<CgSupplierExaminationDto> result = cgSupplierExaminationFacade.list(queryCriteria, pageable);
        return ResponseEntity.ok(result.map(cgSupplierExaminationMapper::toVo));
    }

    @GetMapping("getdetailsbyid")
    @Operation(summary = "查询供应商审核详情")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.sup.SupplierAuditController).MENU_CODE_VIEW)")
    public ResponseEntity<CgSupplierExaminationVo> getDetailsById(@Parameter(description = "供应商审核ID") @RequestParam Integer id,
                                                                  @AuthenticationPrincipal Authentication authentication) {
        dataRuleHelper.installDataRule(MENU_CODE_VIEW);
        CgSupplierExaminationDetailsDto result = cgSupplierExaminationFacade.getDetailsById(id);
        CgSupplierExaminationVo vo = cgSupplierExaminationMapper.toVo(result);
        if (vo.getAttachments() != null) {
            for (CgSupplierExaminationAttachmentVo Att : vo.getAttachments()) {
                FileToken fileToken = FileTokens.create(Att.getPath(), Att.getName(), authentication.getName());
                Att.setLink(LinkUtils.createFileDownloadLink(fileToken));
            }
        }
        vo.setFlowInfo(getFlowInfo(id.toString()));
        return ResponseEntity.ok(vo);
    }

    @GetMapping("supplierCreditStatus")
    @Operation(summary = "查询供应商信用信息")
    public ResponseEntity<CgSupplierExaminationVo> supplierCreditStatus(@Parameter(description = "供应商审核ID") @RequestParam Integer id) {
        return ResponseEntity.ok(cgSupplierExaminationMapper.toVo(cgSupplierExaminationFacade.updateSupplierExaminationCredit(id)));
    }

    @Override
    public ResponseEntity<String> flowStartProcess(@Parameter(description = "ID") @RequestParam Integer id,
                                                   @RequestBody(required = false) Map<String, String> formData) {
        CgSupplierExaminationDto byId = cgSupplierExaminationFacade.getById(id);
        String username = byId.getAccount().getUsername();
        CgSupplierExaminationQueryCriteria queryCriteria = new CgSupplierExaminationQueryCriteria();
        queryCriteria.setUsername(username);
        queryCriteria.setStatus("SUBMITTED");
        Page<CgSupplierExaminationDto> list = cgSupplierExaminationFacade.list(queryCriteria, PageRequest.of(0, 1));
        if (!list.isEmpty()) {
            throw new RuntimeException("该供应商已在处在审批流程中");
        }
        return FlowApi.super.flowStartProcess(id,formData);
    }

    @Override
    public ResponseEntity<FlowCallbackResponse> flowCallback(@RequestBody FlowCallbackRequest request) {
        DataScopeUtils.installTenantId(request.getTenantId());
        if (request.typeIs(FlowCallbackRequest.TYPE_START)) {
            cgSupplierExaminationFacade.updateStatus(Integer.parseInt(request.getBusinessKey()),"SUBMITTED",null);
        } else if (request.typeIs(FlowCallbackRequest.TYPE_DELETE)) {
            cgSupplierExaminationFacade.updateStatus(Integer.parseInt(request.getBusinessKey()),"EDIT","");
        } else if (request.typeIs(FlowCallbackRequest.TYPE_END)) {
            cgSupplierExaminationFacade.updateStatus(Integer.parseInt(request.getBusinessKey()),"PASS","通过");
        }
        return ResponseEntity.ok(FlowCallbackResponse.ok());
    }

    @Override
    public FlowFacade getFlowFacade() {
        return flowFacade;
    }

    @Override
    public String getBusinessCode() {
        return MENU_CODE_ROOT;
    }
}
