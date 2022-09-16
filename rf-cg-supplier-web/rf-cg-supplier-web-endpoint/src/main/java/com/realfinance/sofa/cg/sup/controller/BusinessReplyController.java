package com.realfinance.sofa.cg.sup.controller;

import cn.hutool.core.io.FileUtil;
import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.cg.sup.facade.CgBusinessReplyFacade;
import com.realfinance.sofa.cg.sup.model.AttachmentVo;
import com.realfinance.sofa.cg.sup.model.CgBusinessReplyDetailsDto;
import com.realfinance.sofa.cg.sup.model.CgBusinessReplyDto;
import com.realfinance.sofa.cg.sup.model.CgBusinessReplyQueryCriteria;
import com.realfinance.sofa.cg.sup.model.CgBusinessReplyVo;
import com.realfinance.sofa.cg.sup.security.SupplierUser;
import com.realfinance.sofa.cg.sup.service.mapstruct.BusinessReplyMapper;
import com.realfinance.sofa.cg.sup.util.LinkUtils;
import com.realfinance.sofa.common.filestore.FileCipherUtils;
import com.realfinance.sofa.common.filestore.FileStore;
import com.realfinance.sofa.common.filetoken.FileToken;
import com.realfinance.sofa.common.filetoken.FileTokens;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@Tag(name = "商务应答")
@RequestMapping("/cg/sup/bizreply")
public class BusinessReplyController {

    private static final Logger log = LoggerFactory.getLogger(BusinessReplyController.class);

    @SofaReference(interfaceType = CgBusinessReplyFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgBusinessReplyFacade cgBusinessReplyFacade;

    @Resource
    private FileStore fileStore;


    @GetMapping("list")
    @Operation(summary = "查询")
    @PreAuthorize("hasAuthority('SUPPLIER')")
    public ResponseEntity<Page<CgBusinessReplyVo>> list(@ParameterObject CgBusinessReplyQueryCriteria queryCriteria,
                                                        Pageable pageable,
                                                        @AuthenticationPrincipal SupplierUser supplierUser) {
        queryCriteria.setSupplierId(supplierUser.getSupplierId());
        Page<CgBusinessReplyDto> result = cgBusinessReplyFacade.list(queryCriteria, pageable);
        return ResponseEntity.ok(result.map(BusinessReplyMapper.INSTANCE::toVo));
    }

    @GetMapping("getdetailsbyid")
    @Operation(summary = "查询详情")
    @PreAuthorize("hasAuthority('SUPPLIER')")
    public ResponseEntity<CgBusinessReplyVo> getDetailsById(@Parameter(description = "ID") @RequestParam Integer id,
                                                            @AuthenticationPrincipal SupplierUser supplierUser) {
        CgBusinessReplyQueryCriteria queryCriteria = new CgBusinessReplyQueryCriteria();
        queryCriteria.setSupplierId(supplierUser.getSupplierId());
        queryCriteria.setId(id);
        Page<CgBusinessReplyDto> check = cgBusinessReplyFacade.list(queryCriteria, PageRequest.of(0,1));
        if (check.isEmpty()) {
            throw new RuntimeException("无法获取信息");
        }
        //CgBusinessReplyDetailsDto result = cgBusinessReplyFacade.getDetailsById(id);
        CgBusinessReplyDetailsDto result=cgBusinessReplyFacade.getDetailsByAction(queryCriteria);
        CgBusinessReplyVo vo = BusinessReplyMapper.INSTANCE.toVo(result);
        if (vo.getAttDs() != null) {
            for (AttachmentVo attD : vo.getAttDs()) {
                FileToken fileToken = FileTokens.create(attD.getPath(), attD.getName(), supplierUser.getUsername());
                attD.setLink(LinkUtils.createFileDownloadLink(fileToken));
            }
        }
        List<CgBusinessReplyDto> dtos = cgBusinessReplyFacade.getCgBusinessReply(result.getReleaseId(), "招标中");
        vo.setList(dtos);
        return ResponseEntity.ok(vo);
    }


    @GetMapping("getfabudetailsbyid")
    @Operation(summary = "查询发布列表详情")
    @PreAuthorize("hasAuthority('SUPPLIER')")
    public ResponseEntity<CgBusinessReplyVo> getFaBuDetailsById(@Parameter(description = "ID")@RequestParam Integer id,
                                                                @AuthenticationPrincipal SupplierUser supplierUser){
        CgBusinessReplyQueryCriteria queryCriteria=new CgBusinessReplyQueryCriteria();
        queryCriteria.setBusinessReplyId(id);
        queryCriteria.setSupplierId(supplierUser.getSupplierId());
        CgBusinessReplyDetailsDto rebuild = cgBusinessReplyFacade.getFaBuDetailsByAction(queryCriteria);
        CgBusinessReplyVo replyVo = BusinessReplyMapper.INSTANCE.toVo(rebuild);
        if (replyVo.getAttDs() != null) {
            for (AttachmentVo attD : replyVo.getAttDs()) {
                FileToken fileToken = FileTokens.create(attD.getPath(), attD.getName(), supplierUser.getUsername());
                attD.setLink(LinkUtils.createFileDownloadLink(fileToken));
            }
        }
        return ResponseEntity.ok(replyVo);
    }

    @GetMapping("updatefiletime")
    @Operation(summary = "修改下载时间")
    public ResponseEntity<Void> updateFileTimeById(@Parameter(description = "ID") @RequestParam Integer id,
                                                   @AuthenticationPrincipal SupplierUser supplierUser){
        CgBusinessReplyQueryCriteria queryCriteria = new CgBusinessReplyQueryCriteria();
        queryCriteria.setSupplierId(supplierUser.getSupplierId());
        queryCriteria.setId(id);
        cgBusinessReplyFacade.updateBusinessReplyById(queryCriteria);
        return ResponseEntity.ok().build();
    }

    @GetMapping("getdetailsbyidandtype")
    @Operation(summary = "查询发布列表")
    @PreAuthorize("hasAuthority('SUPPLIER')")
    public ResponseEntity<List<CgBusinessReplyVo>> getDetailsByIdAndType(@Parameter(description = "ID") @RequestParam Integer id,
                                                                   Pageable pageable,
                                                                   @AuthenticationPrincipal SupplierUser supplierUser){
        CgBusinessReplyQueryCriteria queryCriteria=new CgBusinessReplyQueryCriteria();
        queryCriteria.setSupplierId(supplierUser.getSupplierId());
        queryCriteria.setId(id);
        queryCriteria.setReplyType("发布中");
        Page<CgBusinessReplyDto> check = cgBusinessReplyFacade.list(queryCriteria, pageable);
        if(check.isEmpty()){
            throw new RuntimeException("无法获取信息");
        }
        List<CgBusinessReplyDto> list = cgBusinessReplyFacade.list(queryCriteria);
        List<CgBusinessReplyVo> replyVos = list.stream().map(BusinessReplyMapper.INSTANCE::toVo).collect(Collectors.toList());
        return ResponseEntity.ok(replyVos);
    }

    @PostMapping("recordfiledownload")
    @Operation(summary = "记录应答文件下载")
    @PreAuthorize("hasAuthority('SUPPLIER')")
    public ResponseEntity<Void> recordFileDownload(@Parameter(description = "ID") @RequestParam Integer id) {
        cgBusinessReplyFacade.recordFileDownloadTime(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("recordsign")
    @Operation(summary = "记录应答签名")
    @PreAuthorize("hasAuthority('SUPPLIER')")
    public ResponseEntity<Void> recordSign(@Parameter(description = "ID") @RequestParam Integer id) {
        cgBusinessReplyFacade.recordSignTime(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("savereplydesc")
    @Operation(summary = "保存应答说明")
    @PreAuthorize("hasAuthority('SUPPLIER')")
    public ResponseEntity<Integer> saveReplyDescription(@RequestBody CgBusinessReplyVo vo) {
        if (LocalDateTime.now().isAfter(vo.getDeadline())){
            throw new RuntimeException("当前时间大于投标截止时间，不能进行提交操作");
        }
        Integer id = cgBusinessReplyFacade.updateReplyDescription(vo.getId(), vo.getReplyDescription());
        return ResponseEntity.ok(id);
    }

    @PostMapping("updatesupreply")
    @Operation(summary = "更新应答信息")
    public ResponseEntity<Integer> updateSupReply(@RequestBody CgBusinessReplyVo vo) {
        Integer id = cgBusinessReplyFacade.updateSupReply(vo.getId(),vo.getNormal(),vo.getNote());
        return ResponseEntity.ok(id);
    }

    @PostMapping("reply")
    @Operation(summary = "应答")
    @PreAuthorize("hasAuthority('SUPPLIER')")
    public ResponseEntity<Integer> reply(@RequestBody CgBusinessReplyVo vo) {
        if (LocalDateTime.now().isAfter(vo.getDeadline())){
            throw new RuntimeException("当前时间大于投标截止时间，不能进行提交操作");
        }
        if (vo.getAttUs() != null || vo.getAttUs().size()!=0) {
            for (AttachmentVo attUs : vo.getAttUs()) {
                if (Stream.of("QUOTE","TECH_BIZ","TECHNOLOGY").noneMatch(e -> e.equals(attUs.getCategory()))) {
                    throw new IllegalArgumentException("附件类型不正确");
                }
            }
        }
        CgBusinessReplyDetailsDto details = cgBusinessReplyFacade.getDetailsById(vo.getId());
        if (details.getSignTime() == null || details.getReplyDescription() == null || details.getReplyDescription().isBlank()) {
            throw new RuntimeException("请确认标书已收到并进行提问");
        }
        Integer id = cgBusinessReplyFacade.updateReply(vo.getId(),BusinessReplyMapper.INSTANCE.toDto(vo.getAttUs()),
                vo.getPaymentDescription(), vo.getSupplyDescription(),vo.getTaxRateDescription(),vo.getOtherDescription(),vo.getPrices());
        return ResponseEntity.ok(id);
    }

    @PostMapping("fileupload")
    @Operation(summary = "附件上传")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AttachmentVo> fileUpload(@RequestParam("file") MultipartFile file,
                                                   @RequestParam("category") String category) {
        String authenticationName = SecurityContextHolder.getContext().getAuthentication().getName();
        InputStream in = null;
        try {
            in = new ByteArrayInputStream(FileCipherUtils.encrypt(file.getInputStream().readAllBytes()));
        } catch (IOException e) {
            throw new RuntimeException("获取文件流失败！");
        }
        String id = fileStore.upload("/sup/"+authenticationName, file.getOriginalFilename(), in);
        AttachmentVo attachment = new AttachmentVo();
        attachment.setCategory(category);
        attachment.setExt(FileUtil.extName(file.getOriginalFilename()));
        attachment.setName(file.getOriginalFilename());
        attachment.setPath(id);
        attachment.setSize(file.getSize());
        attachment.setUploadTime(LocalDateTime.now());
        attachment.setSource("供应商应答上传");
        FileToken fileToken = FileTokens.create(id,file.getOriginalFilename(),authenticationName);
        attachment.setLink(LinkUtils.createFileDownloadLink(fileToken));
        return ResponseEntity.ok(attachment);
    }
}
