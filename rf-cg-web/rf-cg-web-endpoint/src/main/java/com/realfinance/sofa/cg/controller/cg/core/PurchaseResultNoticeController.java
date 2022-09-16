package com.realfinance.sofa.cg.controller.cg.core;


import cn.hutool.core.io.FileUtil;
import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.cg.controller.flow.FlowApi;
import com.realfinance.sofa.cg.core.facade.CgProjectExecutionFacade;
import com.realfinance.sofa.cg.core.facade.CgPurchaseResultNoticeFacade;
import com.realfinance.sofa.cg.core.model.*;
import com.realfinance.sofa.cg.model.cg.CgAttVo;
import com.realfinance.sofa.cg.model.cg.CgPurchaseResultNoticeSaveRequset;
import com.realfinance.sofa.cg.model.cg.CgPurchaseResultNoticeVo;
import com.realfinance.sofa.cg.model.system.DepartmentTreeVo;
import com.realfinance.sofa.cg.model.system.UserQueryCriteriaRequest;
import com.realfinance.sofa.cg.model.system.UserVo;
import com.realfinance.sofa.cg.service.mapstruct.CgProjectExecutionMapper;
import com.realfinance.sofa.cg.service.mapstruct.CgSupplierMapper;
import com.realfinance.sofa.cg.service.mapstruct.DepartmentMapper;
import com.realfinance.sofa.cg.service.mapstruct.PurchaseResultNoticeMapper;
import com.realfinance.sofa.cg.service.mapstruct.UserMapper;
import com.realfinance.sofa.cg.sup.facade.CgBusinessProjectFacade;
import com.realfinance.sofa.cg.sup.facade.CgMassMessagingFacade;
import com.realfinance.sofa.cg.sup.facade.CgSupplierFacade;
import com.realfinance.sofa.cg.sup.model.CgAttachmentDto;
import com.realfinance.sofa.cg.sup.model.CgBusinessProjectDetailsDto;
import com.realfinance.sofa.cg.sup.model.CgBusinessProjectDto;
import com.realfinance.sofa.cg.sup.model.CgBusinessProjectQueryCriteria;
import com.realfinance.sofa.cg.sup.model.CgBusinessReplyDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierContactsDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierDetailsDto;
import com.realfinance.sofa.cg.util.LinkUtils;
import com.realfinance.sofa.common.datascope.DataScopeUtils;
import com.realfinance.sofa.common.filestore.FileStore;
import com.realfinance.sofa.common.filestore.FileStoreException;
import com.realfinance.sofa.common.filetoken.FileToken;
import com.realfinance.sofa.common.filetoken.FileTokens;
import com.realfinance.sofa.flow.facade.FlowFacade;
import com.realfinance.sofa.flow.model.FlowCallbackRequest;
import com.realfinance.sofa.flow.model.FlowCallbackResponse;
import com.realfinance.sofa.sdebank.SdebankSDNSPaperless;
import com.realfinance.sofa.sdebank.model.NoteDto;
import com.realfinance.sofa.system.facade.SystemQueryFacade;
import com.realfinance.sofa.system.model.DepartmentSmallTreeDto;
import com.realfinance.sofa.system.model.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Tag(name = "采购结果通知")
@RequestMapping("/cg/core/purchaseresultnotice")
public class PurchaseResultNoticeController implements FlowApi {

    private static final Logger log = LoggerFactory.getLogger(PurchaseResultNoticeController.class);
    //todo  不知是否需要设置权限
    public static final String MENU_CODE_ROOT = "purchaseresultnotice";
    public static final String MENU_CODE_VIEW = MENU_CODE_ROOT + ":view";
    public static final String MENU_CODE_SAVE = MENU_CODE_ROOT + ":save";
    public static final String MENU_CODE_DELETE = MENU_CODE_ROOT + ":delete";
    public static final String MENU_CODE_APPROVE = MENU_CODE_ROOT + ":approve";

    @SofaReference(interfaceType = CgPurchaseResultNoticeFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgPurchaseResultNoticeFacade cgPurchaseResultNoticeFacade;
    @SofaReference(interfaceType = FlowFacade.class, uniqueId = "${service.rf-flow.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private FlowFacade flowFacade;
    @SofaReference(interfaceType = CgBusinessProjectFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgBusinessProjectFacade cgBusinessProjectFacade;
    @Resource
    private FileStore fileStore;
    @Resource
    private PurchaseResultNoticeMapper purchaseResultNoticeMapper;
    @SofaReference(interfaceType = CgProjectExecutionFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgProjectExecutionFacade cgProjectExecutionFacade;
    @Resource
    private CgProjectExecutionMapper cgProjectExecutionMapper;
    @SofaReference(interfaceType = SystemQueryFacade.class, uniqueId = "${service.rf-system.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private SystemQueryFacade systemQueryFacade;
    @SofaReference(interfaceType = CgMassMessagingFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgMassMessagingFacade cgMassMessagingFacade;
    @Resource
    private DepartmentMapper departmentMapper;
    @Resource
    private UserMapper userMapper;

    @SofaReference(interfaceType = CgSupplierFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgSupplierFacade cgSupplierFacade;

    @Resource
    private CgSupplierMapper cgSupplierMapper;

    @GetMapping("queryuserrefer")
    @Operation(summary = "查询用户参照", description = "非系统租户只返回当前登录的法人下的用户")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<UserVo>> queryUserRefer(UserQueryCriteriaRequest queryCriteria,
                                                       Pageable pageable) {
        Page<UserDto> result = systemQueryFacade.queryUserRefer(queryCriteria, pageable);
        return ResponseEntity.ok(result.map(userMapper::userDto2UserVo));
    }

    @GetMapping("querydepartmentrefer")
    @Operation(summary = "查询部门参照", description = "返回部门树，非系统租户只能返回当前登录的法人下的部门树")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<DepartmentTreeVo>> queryDepartmentRefer() {
        List<DepartmentSmallTreeDto> result = systemQueryFacade.queryDepartmentRefer();
        return ResponseEntity.ok(result.stream().map(departmentMapper::departmentSmallTreeDto2DepartmentTreeVo).collect(Collectors.toList()));
    }

    /*@GetMapping("querysuppliercontactsrefer")
    @Operation(summary = "查询供应商联系人参照")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<SupplierContactsDto>> querySupplierRefer(Pageable pageable){
        Page<SupplierContactsDto> page = cgMassMessagingFacade.list(pageable);
        return ResponseEntity.ok(page);
    }*/

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
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.PurchaseResultNoticeController).MENU_CODE_SAVE)")
    public ResponseEntity<Integer> save(@RequestBody CgPurchaseResultNoticeSaveRequset request) {
        if (request.getId() != null) {
            checkBusinessDataIsEditable(getFlowInfo(request.getId().toString()));
        }
        CgPurchaseResultNoticeSaveDto saveDto = purchaseResultNoticeMapper.toSaveDto(request);
        Integer id = cgPurchaseResultNoticeFacade.save(saveDto);
        return ResponseEntity.ok(id);
    }


    @GetMapping("list")
    @Operation(summary = "列表")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.PurchaseResultNoticeController).MENU_CODE_VIEW)")
    public ResponseEntity<Page<CgPurchaseResultNoticeVo>> list(Pageable pageable, @ParameterObject CgPurchaseResultNoticeQueryCriteria queryCriteria) {
        Page<CgPurchaseResultNoticeDto> list = cgPurchaseResultNoticeFacade.list(pageable, queryCriteria);
        Page<CgPurchaseResultNoticeVo> vo = list.map(purchaseResultNoticeMapper::toVo);
        return ResponseEntity.ok(vo);
    }

    @GetMapping("getidbyprojectid")
    @Operation(summary = "根据执行ID查询结果通知的ID")
    public ResponseEntity<Integer> getDetailsByProjectExecutionId(@Parameter(description = "采购方案ID") @RequestParam Integer projectId) {
        Integer id = cgPurchaseResultNoticeFacade.getIdByProjectId(projectId);
        return ResponseEntity.ok(id);
    }

    @GetMapping("getdetailsbyid")
    @Operation(summary = "详情")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.PurchaseResultNoticeController).MENU_CODE_VIEW)")
    public ResponseEntity<CgPurchaseResultNoticeVo> getDetailsById(@Parameter(description = "采购结果通知ID") @RequestParam Integer id) {
        CgPurchaseResultNoticeDto dto = cgPurchaseResultNoticeFacade.getDetailsById(id);
        CgPurchaseResultNoticeVo vo = purchaseResultNoticeMapper.toVo(dto);

        /*List<CgPurResultNoticeSupVo> resultNoticeSups = vo.getResultNoticeSups();
        List<CgPurResultNoticeSupVo> newResultNoticeSups=new ArrayList<>();
        for(CgPurResultNoticeSupVo supVo:resultNoticeSups){
            CgSupplierDetailsDto supplierDetails = cgSupplierFacade.getDetailsById(supVo.getSupplierId().getId());
            CgSupplierVo cgSupplierVo = cgSupplierMapper.toVo(supplierDetails);
            List<CgSupplierContactsVo> contacts = cgSupplierVo.getContacts();
            supVo.setContactsVos(contacts);
            newResultNoticeSups.add(supVo);
        }
        vo.setResultNoticeSups(newResultNoticeSups);*/

        vo.setFlowInfo(getFlowInfo(id.toString()));
        if (vo.getAttachments() != null) {
            for (CgAttVo Att : vo.getAttachments()) {
                FileToken fileToken = FileTokens.create(Att.getPath(), Att.getName(), null);
                Att.setLink(LinkUtils.createFileDownloadLink(fileToken));
            }
        }
        return ResponseEntity.ok(vo);
    }

    @PostMapping("fileupload")
    @Operation(summary = "文件上传")
    public ResponseEntity<CgAttVo> fileUpload(@RequestParam("file") MultipartFile file,
                                              @AuthenticationPrincipal Authentication authentication) {
        // TODO: 2021/2/23 上传路径
        String id = fileStore.upload("/notice", file.getOriginalFilename(), file.getResource());
        CgAttVo cgAttVo = new CgAttVo();
        cgAttVo.setExt(FileUtil.extName(file.getOriginalFilename()));
        cgAttVo.setName(file.getOriginalFilename());
        cgAttVo.setPath(id);
        cgAttVo.setSize(file.getSize());
        cgAttVo.setSource("PURCHASERESULTNOTICE");
        cgAttVo.setUploadTime(LocalDateTime.now());
        FileToken fileToken = FileTokens.create(id, file.getOriginalFilename(), authentication.getName());
        cgAttVo.setLink(LinkUtils.createFileDownloadLink(fileToken));
        return ResponseEntity.ok(cgAttVo);
    }

    @GetMapping("text")
    public void text(){
        SdebankSDNSPaperless sd = new SdebankSDNSPaperless();
        NoteDto noteDto = new NoteDto();
        List<String> list=new ArrayList<>();
        list.add("腾讯");
        noteDto.setSupplierNames(list);
        noteDto.setProjectNo("SDG052021131");
        noteDto.setProjectName("测试全09-24-01");
        noteDto.setRealName("廖淑婷");
        noteDto.setMobile("123456789");
        noteDto.setTime("2021年09月25日");
        String note = sd.note(noteDto);
        cgPurchaseResultNoticeFacade.text(note);
    }

    @Override
    public ResponseEntity<String> flowStartProcess(@Parameter(description = "ID") @RequestParam Integer id,
                                                   @RequestBody(required = false) Map<String, String> formData) {
        CgPurchaseResultNoticeDto dto = cgPurchaseResultNoticeFacade.getDetailsById(id);
        if (dto.getAttachments().size() == 0) {
            throw new RuntimeException("请先上传需要盖章的pdf文件!");
        }
        List<CgAttSaveDto> list = (dto.getAttachments()).stream().filter(e -> e.getExt().equals("pdf")).collect(Collectors.toList());

        if (list.size() == 0) {
            throw new RuntimeException("请先上传需要盖章的pdf文件!");
        }
        String processInstanceId = getFlowFacade().startProcess(getBusinessCode(), id.toString(), formData,dto.getName());
        return ResponseEntity.ok(processInstanceId);
    }

    @Override
    public ResponseEntity<FlowCallbackResponse> flowCallback(@RequestBody FlowCallbackRequest request) {
        DataScopeUtils.installTenantId(request.getTenantId());
        if (request.typeIs(FlowCallbackRequest.TYPE_START)) {
            cgPurchaseResultNoticeFacade.updateStatus(Integer.parseInt(request.getBusinessKey()), "SUBMITTED");
        } else if (request.typeIs(FlowCallbackRequest.TYPE_DELETE)) {
            cgPurchaseResultNoticeFacade.updateStatus(Integer.parseInt(request.getBusinessKey()), "EDIT");
        } else if (request.typeIs(FlowCallbackRequest.TYPE_END)) {
            cgPurchaseResultNoticeFacade.updateStatus(Integer.parseInt(request.getBusinessKey()), "PASS");
            CgPurchaseResultNoticeDto p = cgPurchaseResultNoticeFacade.getDetailsById(Integer.parseInt(request.getBusinessKey()));
            //获取需要盖章的附件id(用于下载)
            List<CgAttSaveDto> cgAttSaveDtos = (p.getAttachments()).stream().filter(e -> e.getExt().equals("pdf")).collect(Collectors.toList());
            SdebankSDNSPaperless sd = new SdebankSDNSPaperless();
            //附件下载,以及附件盖章,重新上传
            for (CgAttSaveDto dto : cgAttSaveDtos) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                fileStore.download(dto.getPath(), out);
                try (ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray())) {
                    byte[] bytes = sd.sealAutoPdf(in);
                    ByteArrayInputStream input = new ByteArrayInputStream(bytes);
                    String ID = fileStore.upload("/notice", dto.getName(), input);
                    dto.setPath(ID);
                    dto.setUploadTime(LocalDateTime.now());
                } catch (Exception e) {
                    log.error("电子盖章失败!", e);
                    throw new FileStoreException(e);
                }
            }
            CgPurchaseResultNoticeDto purchaseResultNotice = cgPurchaseResultNoticeFacade.updateAttachment(Integer.parseInt(request.getBusinessKey()), cgAttSaveDtos);
            List<CgAttachmentDto> dtoList = new ArrayList<>();
            for (CgAttSaveDto dtos : purchaseResultNotice.getAttachments()) {
                CgAttachmentDto attachmentDto = new CgAttachmentDto();
                attachmentDto.setId(dtos.getId());
                attachmentDto.setCategory(null);
                attachmentDto.setName(dtos.getName());
                attachmentDto.setUploadTime(dtos.getUploadTime());
                attachmentDto.setNote(dtos.getSource());
                attachmentDto.setSize(dtos.getSize());
                attachmentDto.setExt(dtos.getExt());
                attachmentDto.setPath(dtos.getPath());
                dtoList.add(attachmentDto);
            }
            CgBusinessProjectQueryCriteria queryCriteria = new CgBusinessProjectQueryCriteria();
            queryCriteria.setProjectId(purchaseResultNotice.getProjectId().toString());
            List<CgBusinessProjectDto> list = cgBusinessProjectFacade.list(queryCriteria);
            if (list.size() > 0) {
                CgBusinessProjectDto businessProjectDto = list.get(0);
                CgBusinessProjectDetailsDto businessProject = cgBusinessProjectFacade.getDetailsById(businessProjectDto.getId());
                businessProject.setProjectStatus("已完成");
                //List<CgBusinessReplyDetailsDto> replies = businessProject.getReplies();
                List<CgBusinessReplyDto> replies = businessProject.getReplies();
                for (CgBusinessReplyDto reply : replies) {
                    reply.setReplyType("已完成");
                    reply.setContent(purchaseResultNotice.getOutsideContent());
                    reply.setName(purchaseResultNotice.getName());
                }
                List<CgPurchaseResultNoticeSupDto> resultNoticeSups = purchaseResultNotice.getResultNoticeSups();
                for (CgPurchaseResultNoticeSupDto resultNoticeSup : resultNoticeSups) {
                    for (CgBusinessReplyDto reply : replies) {
                        if (resultNoticeSup.getSupplierId().equals(reply.getSupplierId())) {
                            reply.setNeedQuote(resultNoticeSup.getBid());
                        }
                    }
                }
                Integer save = cgBusinessProjectFacade.save(businessProject, dtoList);
            }
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
