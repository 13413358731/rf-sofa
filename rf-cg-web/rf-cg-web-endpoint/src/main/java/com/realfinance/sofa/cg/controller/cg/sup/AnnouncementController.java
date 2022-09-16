package com.realfinance.sofa.cg.controller.cg.sup;


import cn.hutool.core.io.FileUtil;
import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.jcraft.jsch.UserInfo;
import com.realfinance.sofa.cg.controller.flow.FlowApi;
import com.realfinance.sofa.cg.model.cg.CgAttVo;
import com.realfinance.sofa.cg.model.system.AnnouncementDetailsVo;
import com.realfinance.sofa.cg.model.system.AnnouncementQueryCriteriaRequest;
import com.realfinance.sofa.cg.model.system.AnnouncementSaveRequest;
import com.realfinance.sofa.cg.model.system.AnnouncementVo;
import com.realfinance.sofa.cg.security.AuthInfo;
import com.realfinance.sofa.cg.service.mapstruct.AnnouncementMapper;
import com.realfinance.sofa.cg.sup.facade.CgSupplierAnnouncementFacade;
import com.realfinance.sofa.cg.sup.model.SupplierAnnouncementDetailsDto;
import com.realfinance.sofa.cg.sup.model.SupplierAnnouncementDto;
import com.realfinance.sofa.cg.sup.model.SupplierAnnouncementSaveDto;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Map;
import java.time.LocalDateTime;
import java.util.Set;

@RestController
@RequestMapping("/cg/sup/announcement")
@Tag(name = "公告")
public class AnnouncementController implements FlowApi {

    private static final Logger log = LoggerFactory.getLogger(AnnouncementController.class);
    // TODO: 2021/1/15
    public static final String MENU_CODE_ROOT = "announcement";
    public static final String MENU_CODE_VIEW = MENU_CODE_ROOT + ":view";
    public static final String MENU_CODE_SAVE = MENU_CODE_ROOT + ":save";
    public static final String MENU_CODE_DELETE = MENU_CODE_ROOT + ":delete";
    public static final String MENU_CODE_APPROVE = MENU_CODE_ROOT + ":approve";
    public static final String MENU_CODE_RELEASE = MENU_CODE_ROOT + ":release";
    public static final String MENU_CODE_STOP = MENU_CODE_ROOT + ":stop";
    public static final String MENU_CODE_RECOVERY = MENU_CODE_ROOT + ":recovery";


    @SofaReference(interfaceType = CgSupplierAnnouncementFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgSupplierAnnouncementFacade announcementFacade;

    @SofaReference(interfaceType = FlowFacade.class, uniqueId = "${service.rf-flow.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private FlowFacade flowFacade;

    @Resource
    private DataRuleHelper dataRuleHelper;

    @Resource
    private AnnouncementMapper announcementMapper;

    @Resource
    private FileStore fileStore;

    @GetMapping("/list")
    @Operation(summary = "查询公告列表")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.sup.AnnouncementController).MENU_CODE_VIEW)")
    public ResponseEntity<Page<AnnouncementVo>> list(Pageable pageable, AnnouncementQueryCriteriaRequest request) {
        //dataRuleHelper.installDataRule(MENU_CODE_VIEW);
        Page<SupplierAnnouncementDto> list = announcementFacade.list(pageable, request);
        Page<AnnouncementVo> map = list.map(announcementMapper::announcementDto2AnnouncementVo);
        return ResponseEntity.ok(map);

    }


    @GetMapping("/getdetails")
    @Operation(summary = "查询公告详情")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.sup.AnnouncementController).MENU_CODE_VIEW)")
    public ResponseEntity<AnnouncementDetailsVo> getdetails(@Parameter(description = "公告ID") @RequestParam Integer id,
                                                            @AuthenticationPrincipal Authentication authentication) {
        SupplierAnnouncementDetailsDto detailsDto = announcementFacade.getdetails(id);
        AnnouncementDetailsVo Vo = announcementMapper.toVo(detailsDto);
        Vo.setFlowInfo(getFlowInfo(id.toString()));
        if (Vo.getAttachments() != null) {
            for (CgAttVo Att : Vo.getAttachments()) {
                FileToken fileToken = FileTokens.create(Att.getPath(), Att.getName(), authentication.getName());
                Att.setLink(LinkUtils.createFileDownloadLink(fileToken));
            }
        }
        return ResponseEntity.ok(Vo);
    }


    @PostMapping("/save")
    @Operation(summary = "保存更新公告")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.sup.AnnouncementController).MENU_CODE_SAVE)")
    public ResponseEntity<Integer> save(@RequestBody AnnouncementSaveRequest saveRequest) {
        if (saveRequest.getId() != null) {
            checkBusinessDataIsEditable(getFlowInfo(saveRequest.getId().toString()));
        }
        SupplierAnnouncementSaveDto SaveDto = announcementMapper.announcementSaveRequest2SupplierAnnouncementSaveDto(saveRequest);
        Integer saveid = announcementFacade.save(SaveDto);
        return ResponseEntity.ok(saveid);
    }


    @DeleteMapping("/deleate")
    @Operation(summary = "删除公告")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.sup.AnnouncementController).MENU_CODE_DELETE)")
    public ResponseEntity<?> deleate(@Parameter(description = "公告ID") @RequestParam Set<Integer> ids) {
        announcementFacade.deleate(ids);
        return ResponseEntity.ok().build();
    }


    @GetMapping("release")
    @Operation(summary = "发布公告")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.sup.AnnouncementController).MENU_CODE_RELEASE)")
    public ResponseEntity<?> release(@Parameter(description = "公告ID") Integer id) {
        announcementFacade.release(id);
        return ResponseEntity.ok().build();

    }


    @GetMapping("stop")
    @Operation(summary = "停用公告")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.sup.AnnouncementController).MENU_CODE_STOP)")
    public ResponseEntity<?> stop(@Parameter(description = "公告ID") Integer id,
                                  @AuthenticationPrincipal AuthInfo authInfo) {
        String username = authInfo.getUser().getUsername();
        announcementFacade.stop(id, username);
        return ResponseEntity.ok().build();
    }

    @GetMapping("recovery")
    @Operation(summary = "恢复公告")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.sup.AnnouncementController).MENU_CODE_RECOVERY)")
    public ResponseEntity<?> recovery(@Parameter(description = "公告ID") Integer id) {
        announcementFacade.recovery(id);
        return ResponseEntity.ok().build();
    }


    @PostMapping("fileupload")
    @Operation(summary = "文件上传")
    public ResponseEntity<CgAttVo> fileUpload(@RequestParam("file") MultipartFile file,
                                              @AuthenticationPrincipal Authentication authentication) {
        // TODO: 2021/2/23 上传路径
        String id = fileStore.upload("/annou", file.getOriginalFilename(), file.getResource());
        CgAttVo cgAttVo = new CgAttVo();
        cgAttVo.setExt(FileUtil.extName(file.getOriginalFilename()));
        cgAttVo.setName(file.getOriginalFilename());
        cgAttVo.setPath(id);
        cgAttVo.setSize(file.getSize());
        cgAttVo.setSource("ANNOUNCEMENT");
        cgAttVo.setUploadTime(LocalDateTime.now());
        FileToken fileToken = FileTokens.create(id, file.getOriginalFilename(), null);
        cgAttVo.setLink(LinkUtils.createFileDownloadLink(fileToken));
        return ResponseEntity.ok(cgAttVo);
    }


    @Override
    public ResponseEntity<String> flowStartProcess(Integer id, Map<String, String> formData) {
        SupplierAnnouncementDto dto = announcementFacade.getdetail(id);
        String processInstanceId = getFlowFacade().startProcess(getBusinessCode(), id.toString(), formData,dto.getTitle());
        return ResponseEntity.ok(processInstanceId);
    }

    @Override
    public ResponseEntity<FlowCallbackResponse> flowCallback(@RequestBody FlowCallbackRequest request) {
        DataScopeUtils.installTenantId(request.getTenantId());

        if (request.typeIs(FlowCallbackRequest.TYPE_START)) {
            announcementFacade.updateStatus(Integer.parseInt(request.getBusinessKey()), "SUBMITTED");
        } else if (request.typeIs(FlowCallbackRequest.TYPE_DELETE)) {
            announcementFacade.updateStatus(Integer.parseInt(request.getBusinessKey()), "EDIT");
        } else if (request.typeIs(FlowCallbackRequest.TYPE_END)) {
            announcementFacade.updateStatus(Integer.parseInt(request.getBusinessKey()), "PASS");
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
