package com.realfinance.sofa.cg.controller.cg.core;

import cn.hutool.core.io.FileUtil;
import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.cg.controller.flow.FlowApi;
import com.realfinance.sofa.cg.core.facade.CgMultipleReleaseFacade;
import com.realfinance.sofa.cg.core.facade.CgProjectExecutionFacade;
import com.realfinance.sofa.cg.core.model.*;
import com.realfinance.sofa.cg.model.cg.*;
import com.realfinance.sofa.cg.service.mapstruct.CgMultipleReleaseMapper;
import com.realfinance.sofa.cg.sup.facade.CgBusinessReplyFacade;
import com.realfinance.sofa.cg.sup.model.CgAttachmentDto;
import com.realfinance.sofa.cg.sup.model.CgBusinessReplyDetailsDto;
import com.realfinance.sofa.cg.sup.model.CgBusinessReplyDto;
import com.realfinance.sofa.cg.sup.model.CgBusinessReplyQueryCriteria;
import com.realfinance.sofa.cg.util.DataRuleHelper;
import com.realfinance.sofa.cg.util.LinkUtils;
import com.realfinance.sofa.common.datascope.DataScopeUtils;
import com.realfinance.sofa.common.filestore.FileCipherUtils;
import com.realfinance.sofa.common.filestore.FileStore;
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
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Tag(name = "采购方案执行-多轮发布")
@RequestMapping("/cg/core/projexec/multiplerelease")
public class MultipleReleaseController implements FlowApi {

    private static final Logger log = LoggerFactory.getLogger(MultipleReleaseController.class);

    public static final String MENU_CODE_ROOT = "multirelease";
    public static final String MENU_CODE_VIEW = MENU_CODE_ROOT + ":view";
    public static final String MENU_CODE_SAVE = MENU_CODE_ROOT + ":save";

    @SofaReference(interfaceType = CgProjectExecutionFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgProjectExecutionFacade cgProjectExecutionFacade;
    @SofaReference(interfaceType = CgMultipleReleaseFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgMultipleReleaseFacade cgMultipleReleaseFacade;
    @SofaReference(interfaceType = CgBusinessReplyFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgBusinessReplyFacade cgBusinessReplyFacade;
    @SofaReference(interfaceType = FlowFacade.class, uniqueId = "${service.rf-flow.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private FlowFacade flowFacade;

    @Resource
    private DataRuleHelper dataRuleHelper;
    @Resource
    private FileStore fileStore;
    @Resource
    private CgMultipleReleaseMapper cgMultipleReleaseMapper;

    @GetMapping("list")
    @Operation(summary = "查询采购方案执行-发布列表")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.MultipleReleaseController).MENU_CODE_VIEW)")
    public ResponseEntity<Page<CgMultipleReleaseVo>> list(@ParameterObject CgMultipleReleaseQueryCriteria queryCriteria,
                                                          Pageable pageable) {
        dataRuleHelper.installDataRule(MENU_CODE_VIEW);
        Page<CgMultipleReleaseDto> result = cgMultipleReleaseFacade.list(queryCriteria, pageable);
        return ResponseEntity.ok(result.map(cgMultipleReleaseMapper::toVo));
    }

    @GetMapping("getdetailsbyid")
    @Operation(summary = "查询采购方案执行-发布详情")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.MultipleReleaseController).MENU_CODE_VIEW)")
    public ResponseEntity<CgMultipleReleaseVo> getDetailsById(@Parameter(description = "ID") @RequestParam Integer id) {
        dataRuleHelper.installDataRule(MENU_CODE_VIEW);
        CgMultipleReleaseDetailsDto result = cgMultipleReleaseFacade.getDetailsById(id);
        CgMultipleReleaseVo vo = cgMultipleReleaseMapper.toVo(result);
        if (vo.getMultipleReleaseAtts() != null) {
            for (CgAttVo attD : vo.getMultipleReleaseAtts()) {
                FileToken fileToken = FileTokens.create(attD.getPath(), attD.getName());
                attD.setLink(LinkUtils.createFileDownloadLink(fileToken));
            }
        }
        List<CgBusinessReplyVo> replies = vo.getReplies();
        vo.setFlowInfo(getFlowInfo(id.toString()));
        List<CgProjectExecutionSupDto> cgProjectExecutionSupDto = cgProjectExecutionFacade.getSuppliersById(result.getProjectExecution().getId());
        List<CgProjectExecutionSupVo> cgProjectExecutionSupVo = cgProjectExecutionSupDto.stream().map(cgMultipleReleaseMapper::toVo).collect(Collectors.toList());

        vo.setSuppliers(cgProjectExecutionSupVo);
        // 查询供应商报价列表
        CgBusinessReplyQueryCriteria queryCriteria = new CgBusinessReplyQueryCriteria();
        queryCriteria.setReplyType("发布中");
        queryCriteria.setReleaseId(id.toString());
        queryCriteria.setBusinessProjectId(vo.getProjectExecution().getProject().getId().toString());
        List<CgBusinessReplyDetailsDto> listDetails = cgBusinessReplyFacade.listDetails(queryCriteria);
        decryptAtts(listDetails);
        List<CgBusinessReplyVo> replyVoList = listDetails.stream().map(cgMultipleReleaseMapper::toVo).collect(Collectors.toList());
        for (CgBusinessReplyVo reply : replyVoList) {
            List<CgSupplierAttachmentVo> contractAttachments = reply.getAttDs();
            if (contractAttachments != null && !contractAttachments.isEmpty()) {
                for (CgSupplierAttachmentVo attachment : contractAttachments) {
                    FileToken fileToken = FileTokens.create(attachment.getPath(), attachment.getName());
                    attachment.setLink(LinkUtils.createFileDownloadLink(fileToken));
                }
            }
        }
        for (CgBusinessReplyVo reply : replyVoList) {
            List<CgSupplierAttachmentVo> contractAttachments = reply.getAttUs();
            if (contractAttachments != null && !contractAttachments.isEmpty()) {
                for (CgSupplierAttachmentVo attachment : contractAttachments) {
                    FileToken fileToken = FileTokens.create(attachment.getPath(), attachment.getName());
                    attachment.setLink(LinkUtils.createFileDownloadLink(fileToken));
                }
            }
        }
        vo.setReplies(replyVoList);
        return ResponseEntity.ok(vo);
    }

    @PostMapping("save")
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.MultipleReleaseController).MENU_CODE_SAVE)")
    public ResponseEntity<Integer> save(@Validated @RequestBody CgMultipleReleaseVo vo) {
        if (vo.getId() != null) {
            checkBusinessDataIsEditable(getFlowInfo(vo.getId().toString()));
        }
        CgMultipleReleaseDetailsSaveDto saveDto = cgMultipleReleaseMapper.toSaveDto(vo);
        Integer id = cgMultipleReleaseFacade.save(saveDto);
        return ResponseEntity.ok(id);
    }

    @PostMapping("fileupload")
    @Operation(summary = "文件上传")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.MultipleReleaseController).MENU_CODE_SAVE)")
    public ResponseEntity<CgAttVo> fileUpload(@RequestParam("file") MultipartFile file,
                                              @AuthenticationPrincipal Authentication authentication) {
        String id = fileStore.upload("/multirelease", file.getOriginalFilename(), file.getResource());
        CgAttVo cgAttVo = new CgAttVo();
        cgAttVo.setExt(FileUtil.extName(file.getOriginalFilename()));
        cgAttVo.setName(file.getOriginalFilename());
        cgAttVo.setPath(id);
        cgAttVo.setSize(file.getSize());
        cgAttVo.setUploadTime(LocalDateTime.now());
        cgAttVo.setSource("MULTIPLE_RELEASE");
        FileToken fileToken = FileTokens.create(id,file.getOriginalFilename(),authentication.getName());
        cgAttVo.setLink(LinkUtils.createFileDownloadLink(fileToken));
        return ResponseEntity.ok(cgAttVo);
    }

    @Override
    public ResponseEntity<FlowCallbackResponse> flowCallback(FlowCallbackRequest request) {
        DataScopeUtils.installTenantId(request.getTenantId());
        if (request.typeIs(FlowCallbackRequest.TYPE_START)) {
            cgMultipleReleaseFacade.updateStatus(Integer.parseInt(request.getBusinessKey()),"SUBMITTED");
        } else if (request.typeIs(FlowCallbackRequest.TYPE_DELETE)) {
            cgMultipleReleaseFacade.updateStatus(Integer.parseInt(request.getBusinessKey()),"EDIT");
        } else if (request.typeIs(FlowCallbackRequest.TYPE_END)) {
            CgMultipleReleaseDetailsDto dto = cgMultipleReleaseFacade.getDetailsById(Integer.parseInt(request.getBusinessKey()));
            List<String> prices = Collections.emptyList();
            if (dto.getNeedQuote()) {
                prices = cgProjectExecutionFacade.listPriceEvalRuleProductName(dto.getProjectExecution().getId());
            }
            cgBusinessReplyFacade.release(dto.getProjectExecution().getProject().getId().toString(),request.getBusinessKey(),
                    "发布中",dto.getName(),dto.getContent(),dto.getReplyEndTime(),dto.getNeedQuote(),dto.getOpenStartTime(),
                    cgMultipleReleaseMapper.tranAtt(dto.getMultipleReleaseAtts()),prices,dto.getSupplierIds());
            cgMultipleReleaseFacade.updateStatus(Integer.parseInt(request.getBusinessKey()),"PASS");
        } else if (request.typeIs(FlowCallbackRequest.TYPE_LOAD_DATA)) {
            return ResponseEntity.ok(FlowCallbackResponse.ok(cgMultipleReleaseFacade.getById(Integer.parseInt(request.getBusinessKey()))));
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

    /**
     * 解密附件
     * @return
     */
    private List<CgBusinessReplyDetailsDto> decryptAtts(List<CgBusinessReplyDetailsDto> list) {
        for (CgBusinessReplyDetailsDto cgBusinessReplyDto : list) {
            List<CgAttachmentDto> attUs = cgBusinessReplyDto.getAttUs();
            if (attUs != null) {
                for (CgAttachmentDto us : attUs) {
                    FastByteArrayOutputStream out = new FastByteArrayOutputStream();
                    fileStore.download(us.getPath(),out);
                    ByteArrayResource resource = new ByteArrayResource(FileCipherUtils.decrypt(out.toByteArray()));
                    String path = fileStore.upload("/biddoc", us.getName(), resource);
                    us.setPath(path);
                }
            }
        }
        return list;
    }
}
