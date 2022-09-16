package com.realfinance.sofa.cg.controller.cg.sup;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.cg.controller.flow.FlowApi;
import com.realfinance.sofa.cg.model.cg.*;
import com.realfinance.sofa.cg.service.mapstruct.CgSupplierExaminationMapper;
import com.realfinance.sofa.cg.service.mapstruct.CgSupplierMapper;
import com.realfinance.sofa.cg.sup.facade.CgSupplierExaminationFacade;
import com.realfinance.sofa.cg.sup.facade.CgSupplierFacade;
import com.realfinance.sofa.cg.sup.model.*;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

@RestController
@Tag(name = "供应商信息修改")
@RequestMapping("/cg/sup/supplieredit")
public class SupplierEditController implements FlowApi {

    private static final Logger log = LoggerFactory.getLogger(SupplierEditController.class);

    public static final String MENU_CODE_ROOT = "supplieredit";
    public static final String MENU_CODE_VIEW = MENU_CODE_ROOT + ":view";
    public static final String MENU_CODE_SAVE = MENU_CODE_ROOT + ":save";
    public static final String MENU_CODE_DELETE = MENU_CODE_ROOT + ":delete";
    public static final String MENU_CODE_APPROVE = MENU_CODE_ROOT + ":approve";

    @SofaReference(interfaceType = CgSupplierExaminationFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgSupplierExaminationFacade cgSupplierExaminationFacade;

    @SofaReference(interfaceType = CgSupplierFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgSupplierFacade cgSupplierFacade;

    @SofaReference(interfaceType = FlowFacade.class, uniqueId = "${service.rf-flow.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private FlowFacade flowFacade;

    @Resource
    private DataRuleHelper dataRuleHelper;

    @Resource
    private CgSupplierExaminationMapper cgSupplierExaminationMapper;
    @Resource
    private CgSupplierMapper cgSupplierMapper;

    @GetMapping("list")
    @Operation(summary = "查询供应商信息修改列表")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.sup.SupplierEditController).MENU_CODE_VIEW)")
    public ResponseEntity<Page<CgSupplierExaminationVo>> list(CgSupplierExaminationQueryCriteriaRequest queryCriteria,
                                                              Pageable pageable) {
        queryCriteria.setCategoryIn(Collections.singleton("MODIFY_FROM_INTERNAL"));
        dataRuleHelper.installDataRule(MENU_CODE_VIEW);
        Page<CgSupplierExaminationDto> result = cgSupplierExaminationFacade.list(queryCriteria, pageable);
        return ResponseEntity.ok(result.map(cgSupplierExaminationMapper::toVo));
    }

    @GetMapping("querysupplierrefer")
    @Operation(summary = "查询供应商参照")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<CgSupplierVo>> querySupplierRefer(CgSupplierQueryCriteriaRequest queryCriteria,
                                                                 Pageable pageable) {
        Page<CgSupplierSmallDto> result = cgSupplierFacade.queryRefer(queryCriteria,pageable);
        return ResponseEntity.ok(result.map(cgSupplierMapper::toVo));
    }

    @GetMapping("getdetailsbyid")
    @Operation(summary = "查询供应商信息修改详情")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.sup.SupplierEditController).MENU_CODE_VIEW)")
    public ResponseEntity<CgSupplierExaminationVo> getDetailsById(@Parameter(description = "供应商信息修改ID") @RequestParam Integer id,
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

    @PostMapping("create")
    @Operation(summary = "根据供应商ID新增")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.sup.SupplierEditController).MENU_CODE_SAVE)")
    public ResponseEntity<Integer> create(@Parameter(description = "供应商ID") @RequestParam Integer supplierId) {
        // TODO: 2020/12/14 这里得数据规则看看是否需要修改
        //dataRuleHelper.installDataRule(MENU_CODE_VIEW);
        Integer id = cgSupplierExaminationFacade.createFromInternal(supplierId);
        return ResponseEntity.ok(id);
    }

    @PostMapping("save")
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.sup.SupplierEditController).MENU_CODE_SAVE)")
    public ResponseEntity<Integer> save(@Validated(CgSupplierExaminationVo.SupplierEdit.class) @RequestBody CgSupplierExaminationVo vo) {
        if (vo.getId() != null) {
            checkBusinessDataIsEditable(getFlowInfo(vo.getId().toString()));
        }
        CgSupplierExaminationDetailsSaveDto saveDto = cgSupplierExaminationMapper.toSaveDto(vo);
        Integer id = cgSupplierExaminationFacade.save(saveDto);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("delete")
    @Operation(summary = "删除")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.sup.SupplierEditController).MENU_CODE_DELETE)")
    public ResponseEntity<?> delete(@Parameter(description = "供应商修改ID") @RequestParam Set<Integer> id) {
        cgSupplierExaminationFacade.delete(id);
        return ResponseEntity.ok().build();
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
    public String getBusinessCode() {
        return MENU_CODE_ROOT;
    }

    @Override
    public FlowFacade getFlowFacade() {
        return flowFacade;
    }
}
