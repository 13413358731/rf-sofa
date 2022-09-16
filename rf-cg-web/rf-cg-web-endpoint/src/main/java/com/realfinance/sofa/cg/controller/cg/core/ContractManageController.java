package com.realfinance.sofa.cg.controller.cg.core;

import cn.hutool.core.io.FileUtil;
import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.cg.controller.flow.FlowApi;
import com.realfinance.sofa.cg.core.facade.CgContractManageFacade;
import com.realfinance.sofa.cg.core.facade.CgParameterFacade;
import com.realfinance.sofa.cg.core.facade.CgPurchaseResultNoticeFacade;
import com.realfinance.sofa.cg.core.model.CgContractManageDetailsDto;
import com.realfinance.sofa.cg.core.model.CgContractManageDto;
import com.realfinance.sofa.cg.core.model.CgContractManageQueryCriteria;
import com.realfinance.sofa.cg.core.model.CgContractManageSaveDto;
import com.realfinance.sofa.cg.model.cg.CgContractAttachmentVo;
import com.realfinance.sofa.cg.model.cg.CgContractManageVo;
import com.realfinance.sofa.cg.service.mapstruct.CgContractManageMapper;
import com.realfinance.sofa.cg.util.LinkUtils;
import com.realfinance.sofa.common.filestore.FileStore;
import com.realfinance.sofa.common.filetoken.FileToken;
import com.realfinance.sofa.common.filetoken.FileTokens;
import com.realfinance.sofa.flow.facade.FlowFacade;
import com.realfinance.sofa.flow.model.FlowCallbackRequest;
import com.realfinance.sofa.flow.model.FlowCallbackResponse;
import com.realfinance.sofa.system.facade.SystemQueryFacade;
import com.realfinance.sofa.system.facade.UserMngFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@Tag(name = "合同管理")
@RequestMapping("/cg/core/contractManage")
public class ContractManageController implements FlowApi {
    private static final Logger log = LoggerFactory.getLogger(ContractManageController.class);

    public static final String MENU_CODE_ROOT = "contract";

    @SofaReference(interfaceType = CgContractManageFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgContractManageFacade cgContractManageFacade;

    @SofaReference(interfaceType = CgPurchaseResultNoticeFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgPurchaseResultNoticeFacade cgPurchaseResultNoticeFacade;

    @SofaReference(interfaceType = SystemQueryFacade.class, uniqueId = "${service.rf-system.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private SystemQueryFacade systemQueryFacade;

    @SofaReference(interfaceType = FlowFacade.class, uniqueId = "${service.rf-flow.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private FlowFacade flowFacade;

    @SofaReference(interfaceType = CgParameterFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgParameterFacade cgParameterFacade;

    @SofaReference(interfaceType = UserMngFacade.class, uniqueId = "${service.rf-system.id}", binding = @SofaReferenceBinding(bindingType = "bolt", timeout = 300000))
    private UserMngFacade userMngFacade;

    @Resource
    private CgContractManageMapper cgContractManageMapper;

    @javax.annotation.Resource
    private FileStore fileStore;

    @GetMapping("list")
    @Operation(summary = "查询合同管理列表")
    @Parameters({
            @Parameter(name = "startDateBefore", schema = @Schema(type = "string", format = "date-time"), description = "合同生效日期之前", in = ParameterIn.QUERY),
            @Parameter(name = "startDateAfter", schema = @Schema(type = "string", format = "date-time"), description = "合同生效日期之后", in = ParameterIn.QUERY),
            @Parameter(name = "expireDateBefore", schema = @Schema(type = "string", format = "date-time"), description = "合同到期之前", in = ParameterIn.QUERY),
            @Parameter(name = "expireDateAfter", schema = @Schema(type = "string", format = "date-time"), description = "合同到期之后", in = ParameterIn.QUERY),
    })
    public ResponseEntity<Page<CgContractManageVo>> list(CgContractManageQueryCriteria queryCriteria,
                                                         Pageable pageable) {
        Page<CgContractManageDto> result = cgContractManageFacade.list(queryCriteria, pageable);
        return ResponseEntity.ok(result.map(cgContractManageMapper::toVo));
    }

    /*@GetMapping("list")
    @Operation(summary = "查询合同管理列表")
    public ResponseEntity<Page<CgContractManageVo>> list(@ParameterObject CgContractManageQueryCriteria queryCriteria,
                                                         Pageable pageable) {
        Page<CgContractManageDto> result = cgContractManageFacade.list(queryCriteria, pageable);
        return ResponseEntity.ok(result.map(cgContractManageMapper::toVo));
    }*/


    @GetMapping("getdetailsbyid1")
    @Operation(summary = "查询合同管理详情")
    public ResponseEntity<CgContractManageVo> getDetailsById1(@Parameter(description = "专家抽取主表id") @RequestParam Integer id) {
        CgContractManageDetailsDto result = cgContractManageFacade.getDetailsById(id);
        return ResponseEntity.ok(cgContractManageMapper.toVo(result));
    }

    @GetMapping("getdetailsbyid")
    @Operation(summary = "查询合同管理详情")
    public ResponseEntity<CgContractManageVo> getDetailsById(@Parameter(description = "专家抽取主表id") @RequestParam Integer id) {
        CgContractManageVo result = cgContractManageMapper.toVo(cgContractManageFacade.getDetailsById(id));
        List<CgContractAttachmentVo> contractAttachments = result.getContractAttachments();
        if (contractAttachments != null && !contractAttachments.isEmpty()) {
            for (CgContractAttachmentVo attachment : contractAttachments) {
                FileToken fileToken = FileTokens.create(attachment.getPath(), attachment.getName());
                attachment.setLink(LinkUtils.createFileDownloadLink(fileToken));
                if (attachment.getUploader() != null) {
                    attachment.setUploaderUser(systemQueryFacade.queryUserSmallDto(attachment.getUploader()).getRealname());

                }
            }
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("save")
    @Operation(summary = "保存")
    public ResponseEntity<Integer> save(@Validated @RequestBody CgContractManageVo vo) {
        CgContractManageSaveDto saveDto = cgContractManageMapper.toSaveDto(vo);
        Integer id = cgContractManageFacade.save(saveDto);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("delete")
    @Operation(summary = "删除")
    public ResponseEntity<?> delete(@Parameter(description = "合同管理ID") @RequestParam Set<Integer> id) {
        cgContractManageFacade.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("fileupload")
    @Operation(summary = "附件上传")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Map<String, Object>> fileUpload(Authentication authentication, @RequestParam("file") MultipartFile file) {
        CgContractAttachmentVo attachment = upload(file);
        attachment.setUploader(Integer.parseInt(authentication.getName()));
        attachment.setSource("CONTRACTMANAGE");
        Map<String, Object> result = new HashMap<>();
        result.put("attachment", attachment);
        return ResponseEntity.ok(result);
    }

    @PostMapping("interfile")
    @Operation(summary = "合同归档")
    public ResponseEntity<?> interfile(@RequestParam Integer id) {
        cgContractManageFacade.updateFileStatus(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("text")
    public void text( ){
        /*SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd");
        String time = s.format(new Date());
        //获取当前0点的整点时间戳
        Long nowTime=null;
        try {
            Date d=s.parse(time);
            nowTime=d.getTime();
            System.out.println("d:"+d);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
    }


    private CgContractAttachmentVo upload(MultipartFile file) {
        String authenticationName = SecurityContextHolder.getContext().getAuthentication().getName();
        String id = fileStore.upload("/sup/" + authenticationName, file.getOriginalFilename(), file.getResource());
        CgContractAttachmentVo attachment = new CgContractAttachmentVo();
        attachment.setExt(FileUtil.extName(file.getOriginalFilename()));
        attachment.setName(file.getOriginalFilename());
        attachment.setPath(id);
        attachment.setSize(file.getSize());
        attachment.setUploadTime(LocalDateTime.now());
        FileToken fileToken = FileTokens.create(id, file.getOriginalFilename(), authenticationName);
        attachment.setLink(LinkUtils.createFileDownloadLink(fileToken));
        return attachment;
    }
    private CgContractAttachmentVo upload2(MultipartFile file) {
        String authenticationName = SecurityContextHolder.getContext().getAuthentication().getName();
        String id = fileStore.upload(file.getOriginalFilename(), file.getResource());
        CgContractAttachmentVo attachment = new CgContractAttachmentVo();
        attachment.setExt(FileUtil.extName(file.getOriginalFilename()));
        attachment.setName(file.getOriginalFilename());
        attachment.setPath(id);
        attachment.setSize(file.getSize());
        attachment.setUploadTime(LocalDateTime.now());
        FileToken fileToken = FileTokens.create(id, file.getOriginalFilename(), authenticationName);
        attachment.setLink(LinkUtils.createFileDownloadLink(fileToken));
        return attachment;
    }

    @Override
    public String getBusinessCode() {
        return MENU_CODE_ROOT;
    }

    @Override
    public FlowFacade getFlowFacade() {
        return flowFacade;
    }

    @Override
    public ResponseEntity<FlowCallbackResponse> flowCallback(@RequestBody FlowCallbackRequest request) {
        return ResponseEntity.ok(FlowCallbackResponse.ok());
    }

}
