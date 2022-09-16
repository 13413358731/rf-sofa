package com.realfinance.sofa.cg.sup.controller;

import cn.hutool.core.io.FileUtil;
import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.cg.sup.facade.CgSupplierAccountFacade;
import com.realfinance.sofa.cg.sup.facade.CgSupplierExaminationFacade;
import com.realfinance.sofa.cg.sup.facade.CgSupplierFacade;
import com.realfinance.sofa.cg.sup.model.*;
import com.realfinance.sofa.cg.sup.service.mapstruct.SupplierEditMapper;
import com.realfinance.sofa.cg.sup.util.ChineseNumberUtils;
import com.realfinance.sofa.cg.sup.util.LinkUtils;
import com.realfinance.sofa.common.filestore.FileStore;
import com.realfinance.sofa.common.filetoken.FileToken;
import com.realfinance.sofa.common.filetoken.FileTokens;
import com.realfinance.sofa.common.ocr.OcrService;
import com.realfinance.sofa.common.ocr.model.BusinessLicense;
import com.realfinance.sofa.common.ocr.model.IdCard;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@Tag(name = "供应商门户-供应商信息修改")
@RequestMapping("/cg/sup/portal/edit")
public class SupplierEditController {

    private static final Logger log = LoggerFactory.getLogger(SupplierEditController.class);

    @SofaReference(interfaceType = CgSupplierExaminationFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt", timeout = 300000))
    private CgSupplierExaminationFacade cgSupplierExaminationFacade;

    @SofaReference(interfaceType = CgSupplierFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgSupplierFacade cgSupplierFacade;

    @SofaReference(interfaceType = CgSupplierAccountFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgSupplierAccountFacade cgSupplierAccountFacade;

    @javax.annotation.Resource
    private OcrService ocrService;
    @javax.annotation.Resource
    private FileStore fileStore;

    @GetMapping("examinations")
    @Operation(summary = "查询审核记录，只能查询到门户修改的记录")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<CgSupplierExaminationDto>> examinations(Authentication authentication, Pageable pageable) {
        String username = authentication.getName();
        CgSupplierExaminationQueryCriteria queryCriteria = new CgSupplierExaminationQueryCriteria();
        queryCriteria.setUsername(username);
        queryCriteria.setCategoryIn(Stream.of("MODIFY_FROM_PORTAL","INITIAL").collect(Collectors.toSet()));
        Page<CgSupplierExaminationDto> result = cgSupplierExaminationFacade.list(queryCriteria, pageable);
        return ResponseEntity.ok(result);
    }

    @PostMapping("ocrbusinesslicense")
    @Operation(summary = "OCR-营业执照")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Map<String,Object>> ocrBusinessLicense(@RequestParam("file") MultipartFile file) {
        BusinessLicense businessLicense = ocrService.ocrBusinessLicense(file.getResource());
        HashMap<String, Object> orcResult = new HashMap<>();
        orcResult.put("name",businessLicense.getBizLicenseCompanyName());
        orcResult.put("unifiedSocialCreditCode",businessLicense.getBizLicenseCreditCode());
        orcResult.put("companyType",businessLicense.getBizLicenseCompanyType());
        // TODO: 2021/2/13 注册资本转换
//        orcResult.put("registeredCapital",getRegisteredCapital(businessLicense.getBizLicenseRegCapital()));
        orcResult.put("registeredCapitalUppercase",businessLicense.getBizLicenseRegCapital());
        orcResult.put("registeredCapitalCurrency","CNY");
        String bizLicenseStartTime = businessLicense.getBizLicenseStartTime();
        orcResult.put("setupDate",ChineseNumberUtils.parseLocalDate(bizLicenseStartTime));
        String bizLicenseOperatingPeriod = businessLicense.getBizLicenseOperatingPeriod();
        if (bizLicenseOperatingPeriod != null) {
            if (bizLicenseOperatingPeriod.contains("至")) {
                String[] split = bizLicenseOperatingPeriod.split("至");
                if (split.length == 2) {
                    orcResult.put("businessTermStart", ChineseNumberUtils.parseLocalDate(split[0]));
                    if (split[1].contains("长期")) {
                        orcResult.put("businessTermEnd",LocalDate.of(9999,12,31));
                    } else {
                        orcResult.put("businessTermEnd",ChineseNumberUtils.parseLocalDate(split[1]));
                    }
                }
            } else {
                orcResult.put("businessTermStart", LocalDate.of(0, 1, 1));
                if (bizLicenseOperatingPeriod.contains("长期")) {
                    orcResult.put("businessTermEnd",LocalDate.of(9999,12,31));
                } else {
                    orcResult.put("businessTermEnd",ChineseNumberUtils.parseLocalDate(bizLicenseOperatingPeriod));
                }
            }
        }
        orcResult.put("statutoryRepresentative",businessLicense.getBizLicenseOwnerName());
        orcResult.put("businessScope",businessLicense.getBizLicenseScope());
        orcResult.put("companyDomicile",businessLicense.getBizLicenseAddress());

        AttachmentVo attachment = upload("BUSINESS_LICENSE",file);
        Map<String,Object> result = new HashMap<>();
        result.put("attachments", Collections.singletonList(attachment));
        result.put("orcResult", orcResult);
        return ResponseEntity.ok(result);

    }

    @PostMapping("ocridcard")
    @Operation(summary = "OCR-身份证")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Map<String,Object>> ocrIdCard(@RequestParam("front") MultipartFile front,
                                                        @RequestParam("back") MultipartFile back) {
        IdCard idCard = ocrService.ocrIdCard(front.getResource());
        AttachmentVo idCardFront = upload("ID_CARD_FRONT",front);
        AttachmentVo idCardBack = upload("ID_CARD_BACK",back);
        HashMap<Object, Object> orcResult = new HashMap<>();
        orcResult.put("idCardName", idCard.getName());
        orcResult.put("idCardNumber", idCard.getIdNumber());
        orcResult.put("idCardAddress", idCard.getAddress());
        Map<String,Object> result = new HashMap<>();
        result.put("orcResult", orcResult);
        result.put("attachments", Stream.of(idCardFront,idCardBack).collect(Collectors.toList()));
        return ResponseEntity.ok(result);
    }

    @PostMapping("fileupload")
    @Operation(summary = "附件上传")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Map<String,Object>> fileUpload(@RequestParam("file") MultipartFile file,
                                                         @RequestParam("category") String category,
                                                         @RequestParam(value = "key", required = false) String key) {
        AttachmentVo attachment = upload(category,file);
        Map<String,Object> result = new HashMap<>();
        result.put("attachment",attachment);
        result.put("key",key);
        return ResponseEntity.ok(result);
    }

    @GetMapping("supplierdetails")
    @Operation(summary = "供应商信息详情")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CgSupplierEditVo> supplierDetails(Authentication authentication) {
        CgSupplierExaminationQueryCriteria queryCriteria = new CgSupplierExaminationQueryCriteria();
        queryCriteria.setUsername(authentication.getName());
        queryCriteria.setCategoryIn(Stream.of("MODIFY_FROM_PORTAL","INITIAL").collect(Collectors.toSet()));
        Page<CgSupplierExaminationDto> list = cgSupplierExaminationFacade.list(queryCriteria, PageRequest.of(0, 1, Sort.by("createdTime").descending()));
        if (list.isEmpty()) {
            return ResponseEntity.ok().build();
        } else {
            CgSupplierExaminationDto cgSupplierExaminationDto = list.getContent().get(0);
            String status = cgSupplierExaminationDto.getStatus();
            CgSupplierEditVo data;
            if ("PASS".equals(status)) {
                data =  SupplierEditMapper.INSTANCE.toVo(cgSupplierFacade.getDetailsByUsername(authentication.getName()));
            } else {
                data = SupplierEditMapper.INSTANCE.toVo(cgSupplierExaminationFacade.getDetailsById(cgSupplierExaminationDto.getId()));
            }
            List<AttachmentVo> attachments = data.getAttachments();
            if (attachments != null && !attachments.isEmpty()) {
                for (AttachmentVo attachment : attachments) {
                    FileToken fileToken = FileTokens.create(attachment.getPath(), attachment.getName(), authentication.getName());
                    attachment.setLink(LinkUtils.createFileDownloadLink(fileToken));
                    attachment.setId(null);
                }
            }
            return ResponseEntity.ok(data);
        }
    }

    // TODO: 2020/12/14 日志
    // TODO: 2021/1/19 可以尝试通过限流策略防止重复提交
    @PostMapping("submit")
    @Operation(summary = "提交修改")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> submitExamination(@RequestBody @Validated CgSupplierExaminationDetailsSaveRequest saveRequest,
                                               Authentication authentication) {
        if (saveRequest.getAttachments() != null) {
            for (AttachmentVo attachment : saveRequest.getAttachments()) {
                if (Stream.of("FINANCIAL_REPORT", "BUSINESS_LICENSE", "QUALIFICATION_CERTIFICATE", "LEASE_CONTRACT",
                        "MAIN_BUSINESS", "ID_CARD_FRONT", "ID_CARD_BACK", "OTHER","SUP_IN_PROMISE","AUTHID_CARD_FRONT","AUTHID_CARD_BACK","PUR_AUTH_ATTORNEY","QUALITY_AUTH").noneMatch(e -> e.equals(attachment.getCategory()))) {
                    throw new IllegalArgumentException("附件类型不正确");
                }
            }
        }

        CgSupplierExaminationDetailsSaveDto saveDto = SupplierEditMapper.INSTANCE.toSaveDto(saveRequest);
        CgSupplierAccountDto account = cgSupplierAccountFacade.getByUsername(authentication.getName());
        CgSupplierExaminationQueryCriteria queryCriteria = new CgSupplierExaminationQueryCriteria();
        queryCriteria.setAccountId(account.getId());
        queryCriteria.setCategoryIn(Stream.of("MODIFY_FROM_PORTAL","INITIAL").collect(Collectors.toSet()));
        PageRequest pageRequest = PageRequest.of(0, 1, Sort.by("createdTime").descending());
        Page<CgSupplierExaminationDto> list = cgSupplierExaminationFacade.list(queryCriteria, pageRequest);
        if (list.isEmpty()) {
            saveDto.setId(null);
        } else {
            CgSupplierExaminationDto cgSupplierExaminationDto = list.getContent().get(0);
            String status = cgSupplierExaminationDto.getStatus();
            if ("SUBMITTED".equals(status)) {
                throw new RuntimeException("供应商正在修改审批中");
            } else if ("EDIT".equals(status)) {
                saveDto.setId(cgSupplierExaminationDto.getId());
            } else {
                saveDto.setId(null);
            }
        }
        saveDto.setCategory("MODIFY_FROM_PORTAL");
        saveDto.setAccount(account.getId());
        cgSupplierExaminationFacade.save(saveDto);
        return ResponseEntity.ok().build();
    }

    private AttachmentVo upload(String category, MultipartFile file) {
        String authenticationName = SecurityContextHolder.getContext().getAuthentication().getName();
        String id = fileStore.upload("/sup/"+authenticationName, file.getOriginalFilename(), file.getResource());
        AttachmentVo attachment = new AttachmentVo();
        attachment.setCategory(category);
        attachment.setExt(FileUtil.extName(file.getOriginalFilename()));
        attachment.setName(file.getOriginalFilename());
        attachment.setPath(id);
        attachment.setSize(file.getSize());
        attachment.setUploadTime(LocalDateTime.now());
        FileToken fileToken = FileTokens.create(id,file.getOriginalFilename(),authenticationName);
        attachment.setLink(LinkUtils.createFileDownloadLink(fileToken));
        return attachment;
    }
}
