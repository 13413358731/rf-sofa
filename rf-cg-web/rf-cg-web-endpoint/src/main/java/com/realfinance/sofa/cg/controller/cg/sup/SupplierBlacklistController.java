package com.realfinance.sofa.cg.controller.cg.sup;

import cn.hutool.core.io.FileUtil;
import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.cg.controller.flow.FlowApi;
import com.realfinance.sofa.cg.model.cg.*;
import com.realfinance.sofa.cg.service.mapstruct.CgSupplierBlacklistMapper;
import com.realfinance.sofa.cg.service.mapstruct.CgSupplierMapper;
import com.realfinance.sofa.cg.sup.facade.CgSupplierBlacklistFacade;
import com.realfinance.sofa.cg.sup.facade.CgSupplierFacade;
import com.realfinance.sofa.cg.sup.model.CgSupplierBlacklistDetailsDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierBlacklistDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierBlacklistSaveDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierSmallDto;
import com.realfinance.sofa.cg.util.DataRuleHelper;
import com.realfinance.sofa.cg.util.LinkUtils;
import com.realfinance.sofa.common.datascope.DataScopeUtils;
import com.realfinance.sofa.common.filestore.FileStore;
import com.realfinance.sofa.common.filetoken.FileToken;
import com.realfinance.sofa.common.filetoken.FileTokens;
import com.realfinance.sofa.flow.facade.FlowFacade;
import com.realfinance.sofa.flow.model.FlowCallbackRequest;
import com.realfinance.sofa.flow.model.FlowCallbackResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

@RestController
@Tag(name = "供应商黑名单管理")
@RequestMapping("/cg/sup/supplierblacklist")
public class SupplierBlacklistController implements FlowApi {

    private static final Logger log = LoggerFactory.getLogger(SupplierBlacklistController.class);

    public static final String MENU_CODE_ROOT = "supplierblacklist";
    public static final String MENU_CODE_VIEW = MENU_CODE_ROOT + ":view";
    public static final String MENU_CODE_SAVE = MENU_CODE_ROOT + ":save";
    public static final String MENU_CODE_DELETE = MENU_CODE_ROOT + ":delete";
    public static final String MENU_CODE_APPROVE = MENU_CODE_ROOT + ":approve";

    @SofaReference(interfaceType = CgSupplierBlacklistFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgSupplierBlacklistFacade cgSupplierBlacklistFacade;
    @SofaReference(interfaceType = CgSupplierFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgSupplierFacade cgSupplierFacade;
    @SofaReference(interfaceType = FlowFacade.class, uniqueId = "${service.rf-flow.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private FlowFacade flowFacade;

    @Resource
    private DataRuleHelper dataRuleHelper;

    @Resource
    private FileStore fileStore;

    @Resource
    private CgSupplierMapper cgSupplierMapper;
    @Resource
    private CgSupplierBlacklistMapper cgSupplierBlacklistMapper;

    @GetMapping("list")
    @Operation(summary = "查询黑名单审核列表")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.sup.SupplierBlacklistController).MENU_CODE_VIEW)")
    public ResponseEntity<Page<CgSupplierBlacklistVo>> list(CgSupplierBlacklistQueryCriteriaRequest queryCriteria,
                                                            Pageable pageable) {
        dataRuleHelper.installDataRule(MENU_CODE_VIEW);
        Page<CgSupplierBlacklistDto> result = cgSupplierBlacklistFacade.list(queryCriteria, PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(), Sort.by("modifiedTime").descending()));
        return ResponseEntity.ok(result.map(cgSupplierBlacklistMapper::toVo));
    }

    @GetMapping("getdetailsbyid")
    @Operation(summary = "查询供应商黑名单详情")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.sup.SupplierBlacklistController).MENU_CODE_VIEW)")
    public ResponseEntity<CgSupplierBlacklistVo> getDetailsById(@Parameter(description = "供应商审核ID") @RequestParam Integer id,
                                                                @AuthenticationPrincipal Authentication authentication) {
        dataRuleHelper.installDataRule(MENU_CODE_VIEW);
        CgSupplierBlacklistDetailsDto result = cgSupplierBlacklistFacade.getDetailsById(id);
        CgSupplierBlacklistVo vo = cgSupplierBlacklistMapper.toDetailsVo(result);
        if (vo.getAttachments() != null) {
            for (CgSupplierBlacklistAttachmentVo Att : vo.getAttachments()) {
                FileToken fileToken = FileTokens.create(Att.getPath(), Att.getName(), authentication.getName());
                Att.setLink(LinkUtils.createFileDownloadLink(fileToken));
            }
        }
        vo.setFlowInfo(getFlowInfo(id.toString()));
        return ResponseEntity.ok(vo);
    }


    @GetMapping("querysupplierrefer")
    @Operation(summary = "查询供应商参照")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<CgSupplierVo>> querySupplierRefer(CgSupplierQueryCriteriaRequest queryCriteria,
                                                                 Pageable pageable) {
        Page<CgSupplierSmallDto> result = cgSupplierFacade.queryRefer(queryCriteria,pageable);
        return ResponseEntity.ok(result.map(cgSupplierMapper::toVo));
    }

    @PostMapping("save")
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.sup.SupplierBlacklistController).MENU_CODE_SAVE)")
    public ResponseEntity<Integer> save(@Validated(CgSupplierBlacklistVo.Save.class) @RequestBody CgSupplierBlacklistVo vo) {
        if (vo.getId() != null) {
            checkBusinessDataIsEditable(getFlowInfo(vo.getId().toString()));
        }
        CgSupplierBlacklistSaveDto saveDto = cgSupplierBlacklistMapper.toSaveDto(vo);
        Integer id = cgSupplierBlacklistFacade.save(saveDto);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("delete")
    @Operation(summary = "删除")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.sup.SupplierBlacklistController).MENU_CODE_DELETE)")
    public ResponseEntity<?> delete(@Parameter(description = "黑名单ID") @RequestParam Set<Integer> id) {
        cgSupplierBlacklistFacade.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("invalid")
    @Operation(summary = "失效", description = "使黑名单失效")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.sup.SupplierBlacklistController).MENU_CODE_APPROVE)")
    public ResponseEntity<?> invalid(@Parameter(description = "黑名单ID") @RequestParam Integer id) {
        cgSupplierBlacklistFacade.invalid(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("fileupload")
    @Operation(summary = "文件上传")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.sup.SupplierBlacklistController).MENU_CODE_SAVE)")
    public ResponseEntity<CgSupplierBlacklistAttachmentVo> fileupload(@RequestParam("file")MultipartFile file,
                                                                      @AuthenticationPrincipal Authentication authentication){
        String id = fileStore.upload("/supblack", file.getOriginalFilename(), file.getResource());
        CgSupplierBlacklistAttachmentVo attachmentVo=new CgSupplierBlacklistAttachmentVo();
        attachmentVo.setExt(FileUtil.extName(file.getOriginalFilename()));
        attachmentVo.setName(file.getOriginalFilename());
        attachmentVo.setPath(id);
        attachmentVo.setSize(file.getSize());
        attachmentVo.setUploadTime(LocalDateTime.now());
        attachmentVo.setNote("SUPPLIERBLACK");
        FileToken fileToken = FileTokens.create(id, file.getOriginalFilename(), authentication.getName());
        attachmentVo.setLink(LinkUtils.createFileDownloadLink(fileToken));
        return ResponseEntity.ok(attachmentVo);
    }
    protected String getString(Collection list) {
        if (list == null || list.size() == 0) {
            return null;
        }
        String str = StringUtils.substringBeforeLast(
                StringUtils.substringAfterLast(list.toString(), "["), "]");
        return str;
    }

    @Override
    public ResponseEntity<String> flowStartProcess(Integer id, Map<String, String> formData) {
        String processInstanceId = getFlowFacade().startProcess(getBusinessCode(), id.toString(), formData,null);
        return ResponseEntity.ok(processInstanceId);
    }

    @Override
    public ResponseEntity<FlowCallbackResponse> flowCallback(@RequestBody FlowCallbackRequest request) {
        DataScopeUtils.installTenantId(request.getTenantId());
        if (request.typeIs(FlowCallbackRequest.TYPE_START)) {
            cgSupplierBlacklistFacade.updateStatus(Integer.parseInt(request.getBusinessKey()),"SUBMITTED");
        } else if (request.typeIs(FlowCallbackRequest.TYPE_DELETE)) {
            cgSupplierBlacklistFacade.updateStatus(Integer.parseInt(request.getBusinessKey()),"EDIT");
        } else if (request.typeIs(FlowCallbackRequest.TYPE_END)) {
            cgSupplierBlacklistFacade.updateStatus(Integer.parseInt(request.getBusinessKey()),"PASS");
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
