package com.realfinance.sofa.cg.controller.cg.core;

import cn.hutool.core.io.FileUtil;
import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.cg.controller.flow.FlowApi;
import com.realfinance.sofa.cg.core.facade.CgBiddingDocumentFacade;
import com.realfinance.sofa.cg.core.facade.CgProjectExecutionFacade;
import com.realfinance.sofa.cg.core.model.*;
import com.realfinance.sofa.cg.model.cg.CgAttVo;
import com.realfinance.sofa.cg.model.cg.CgBiddingDocumentVo;
import com.realfinance.sofa.cg.model.cg.CgBusinessReplyVo;
import com.realfinance.sofa.cg.model.cg.CgProjectExecutionSupVo;
import com.realfinance.sofa.cg.model.cg.CgSupplierVo;
import com.realfinance.sofa.cg.service.mapstruct.CgBiddingDocumentMapper;
import com.realfinance.sofa.cg.sup.facade.CgBusinessReplyFacade;
import com.realfinance.sofa.cg.sup.model.CgBusinessReplyDetailsDto;
import com.realfinance.sofa.cg.sup.model.CgBusinessReplyQueryCriteria;
import com.realfinance.sofa.cg.util.DataRuleHelper;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jeecgframework.poi.word.WordExportUtil;
import org.jeecgframework.poi.word.entity.MyXWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@Tag(name = "采购方案执行-编辑招标文件和发标")
@RequestMapping("/cg/core/projexec/biddingdoc")
public class BiddingDocumentController implements FlowApi {

    private static final Logger log = LoggerFactory.getLogger(BiddingDocumentController.class);

    public static final String MENU_CODE_ROOT = "biddoc";
    public static final String MENU_CODE_VIEW = MENU_CODE_ROOT + ":view";
    public static final String MENU_CODE_SAVE = MENU_CODE_ROOT + ":save";

    @SofaReference(interfaceType = CgProjectExecutionFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgProjectExecutionFacade cgProjectExecutionFacade;
    @SofaReference(interfaceType = CgBiddingDocumentFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgBiddingDocumentFacade cgBiddingDocumentFacade;
    @SofaReference(interfaceType = CgBusinessReplyFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgBusinessReplyFacade cgBusinessReplyFacade;
    @SofaReference(interfaceType = FlowFacade.class, uniqueId = "${service.rf-flow.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private FlowFacade flowFacade;

    @Resource
    private DataRuleHelper dataRuleHelper;
    @Resource
    private FileStore fileStore;
    @Resource
    private CgBiddingDocumentMapper cgBiddingDocumentMapper;

    @GetMapping("list")
    @Operation(summary = "查询采购方案执行-发标列表")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.BiddingDocumentController).MENU_CODE_VIEW)")
    public ResponseEntity<Page<CgBiddingDocumentVo>> list(@ParameterObject CgBiddingDocumentQueryCriteria queryCriteria,
                                                          Pageable pageable) {
        dataRuleHelper.installDataRule(MENU_CODE_VIEW);
        Page<CgBiddingDocumentDto> result = cgBiddingDocumentFacade.list(queryCriteria, PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(), Sort.by("modifiedTime").descending()));
        return ResponseEntity.ok(result.map(cgBiddingDocumentMapper::toVo));
    }

    @GetMapping("getdetailsbyid")
    @Operation(summary = "查询采购方案执行-发标详情")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.BiddingDocumentController).MENU_CODE_VIEW)")
    public ResponseEntity<CgBiddingDocumentVo> getDetailsById(@Parameter(description = "ID") @RequestParam Integer id) {
        dataRuleHelper.installDataRule(MENU_CODE_VIEW);
        CgBiddingDocumentDetailsDto result = cgBiddingDocumentFacade.getDetailsById(id);
        CgBiddingDocumentVo vo = cgBiddingDocumentMapper.toVo(result);
        if(vo.getBiddingDocumentAtts()!=null){
            for(CgAttVo attVo:vo.getBiddingDocumentAtts()){
                FileToken fileToken = FileTokens.create(attVo.getPath(), attVo.getName());
                attVo.setLink(LinkUtils.createFileDownloadLink(fileToken));
            }
        }
        vo.setFlowInfo(getFlowInfo(id.toString()));
        // 查询供应商
        List<CgProjectExecutionSupVo> suppliers = cgProjectExecutionFacade.getSuppliersById(result.getProjectExecution().getId())
                .stream().map(cgBiddingDocumentMapper::toVo)
                .filter(Objects::nonNull).collect(Collectors.toList());
        //过滤已作废的供应商
        List<CgProjectExecutionSupVo> newSuppliers = suppliers.stream().filter(CgProjectExecutionSupVo -> !CgProjectExecutionSupVo.getModifyMode().equals("TT")).collect(Collectors.toList());
        vo.setSuppliers(newSuppliers);
        CgBusinessReplyQueryCriteria queryCriteria = new CgBusinessReplyQueryCriteria();
        queryCriteria.setReplyType("招标中");
        queryCriteria.setBusinessProjectId(vo.getProjectExecution().getProject().getId().toString());
        List<CgBusinessReplyDetailsDto> listDetails = cgBusinessReplyFacade.listDetails(queryCriteria);
        List<CgBusinessReplyVo> replyVoList = listDetails.stream().map(cgBiddingDocumentMapper::toVo).collect(Collectors.toList());
        /*for(CgBusinessReplyVo replyVo:replyVoList){
            if(vo.getNeedQuote()==true&&replyVo.getNeedQuote()==vo.getNeedQuote()) {
                replyVo
            }
        }*/
        vo.setReplies(replyVoList);
        return ResponseEntity.ok(vo);
    }

    @GetMapping("getidbyprojectexecutionid")
    @Operation(summary = "根据执行ID查询采购方案执行-发标的ID")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.BiddingDocumentController).MENU_CODE_VIEW)")
    public ResponseEntity<Integer> getDetailsByProjectExecutionId(@Parameter(description = "采购方案执行ID") @RequestParam Integer projectExecutionId) {
        Integer id = cgBiddingDocumentFacade.getIdByProjectExecutionId(projectExecutionId);
        return ResponseEntity.ok(id);
    }

    @PostMapping("save")
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.BiddingDocumentController).MENU_CODE_SAVE)")
    public ResponseEntity<Integer> save(@Validated @RequestBody CgBiddingDocumentVo vo) {
        if (vo.getId() != null) {
            checkBusinessDataIsEditable(getFlowInfo(vo.getId().toString()));
        }
        CgBiddingDocumentDetailsSaveDto saveDto = cgBiddingDocumentMapper.toSaveDto(vo);
        Integer id = cgBiddingDocumentFacade.save(saveDto);
        return ResponseEntity.ok(id);
    }

    @PostMapping("updatetime")
    @Operation(summary = "修改时间")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.BiddingDocumentController).MENU_CODE_SAVE)")
    public ResponseEntity<Void> updateTime(@Validated @RequestBody CgBiddingDocumentVo vo) {
        cgBusinessReplyFacade.updateTime(vo.getId().toString(),"招标中",vo.getBidEndTime(),vo.getOpenBidStartTime());
        cgBiddingDocumentFacade.updateTime(vo.getId(), vo.getBidEndTime(), vo.getOpenBidStartTime(), vo.getOpenBidEndTime());
        return ResponseEntity.ok().build();
    }

    @PostMapping("generatedoc")
    @Operation(summary = "生成文档")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.BiddingDocumentController).MENU_CODE_SAVE)")
    public ResponseEntity<Void> generateDoc(@Parameter(description = "ID") @RequestParam Integer id,
                                            @AuthenticationPrincipal Authentication authentication) {
        CgBiddingDocumentDetailsDto details = cgBiddingDocumentFacade.getDetailsById(id);
        byte[] biddingDocumentTemplate = cgBiddingDocumentFacade.getBiddingDocumentTemplate(id);
        List<CgBiddingDocumentSectionDto> sections = details.getBiddingDocumentSections();
        HashMap<String, Object> params = new HashMap<>();
        if (sections != null) {
            for (CgBiddingDocumentSectionDto section : sections) {
                params.put(section.getSectionName(),section.getText() == null ? "" : section.getText());
            }
        }
        try {
            MyXWPFDocument myXWPFDocument = new MyXWPFDocument(new ByteArrayInputStream(biddingDocumentTemplate));
            WordExportUtil.exportWord07(myXWPFDocument,params);
            FastByteArrayOutputStream out = new FastByteArrayOutputStream();
            myXWPFDocument.write(out);
            String fileId = fileStore.upload("/biddoc", details.getDocName(), out.getInputStream());
            CgProjectExecutionAttDto att = new CgProjectExecutionAttDto();
            att.setPath(fileId);
            att.setExt("docx");
            att.setName(details.getDocName());
            att.setSize((long) out.size());
            att.setSource("BIDDING_DOCUMENT");
            cgBiddingDocumentFacade.saveBiddingDocumentAttachment(id, att);
        } catch (Exception e) {
            log.error("生成招标文件失败");
            throw new RuntimeException("生成招标文件失败");
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("fileupload")
    @Operation(summary = "文件上传")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.MultipleReleaseController).MENU_CODE_SAVE)")
    public ResponseEntity<CgAttVo> fileUpload(@RequestParam("file") MultipartFile file,
                                              @AuthenticationPrincipal Authentication authentication) {
        String id = fileStore.upload("/biddoc", file.getOriginalFilename(), file.getResource());
        CgAttVo cgAttVo = new CgAttVo();
        cgAttVo.setExt(FileUtil.extName(file.getOriginalFilename()));
        cgAttVo.setName(file.getOriginalFilename());
        cgAttVo.setPath(id);
        cgAttVo.setSize(file.getSize());
        cgAttVo.setUploadTime(LocalDateTime.now());
        cgAttVo.setSource("BIDDING_DOCUMENT");
        FileToken fileToken = FileTokens.create(id,file.getOriginalFilename(),authentication.getName());
        cgAttVo.setLink(LinkUtils.createFileDownloadLink(fileToken));
        return ResponseEntity.ok(cgAttVo);
    }


    @Override
    public ResponseEntity<String> flowStartProcess(@Parameter(description = "ID") @RequestParam Integer id,
                                                   @RequestBody(required = false) Map<String, String> formData) {
        CgBiddingDocumentDetailsDto dto = cgBiddingDocumentFacade.getDetailsById(id);
        if (dto.getBiddingDocumentAtts().size() == 0) {
            throw new RuntimeException("请先上传需要盖章的pdf文件!");
        }
        List<CgProjectExecutionAttDto> list = (dto.getBiddingDocumentAtts()).stream().filter(e -> e.getExt().equals("pdf")).collect(Collectors.toList());

        if (list.size() == 0) {
            throw new RuntimeException("请先上传需要盖章的pdf文件!");
        }
        String processInstanceId = getFlowFacade().startProcess(getBusinessCode(), id.toString(), formData,dto.getProjectExecution().getProject().getName());
        return ResponseEntity.ok(processInstanceId);
    }

    @Override
    public ResponseEntity<FlowCallbackResponse> flowCallback(FlowCallbackRequest request) {
        DataScopeUtils.installTenantId(request.getTenantId());
        if (request.typeIs(FlowCallbackRequest.TYPE_START)) {
            cgBiddingDocumentFacade.updateStatus(Integer.parseInt(request.getBusinessKey()),"SUBMITTED");
        } else if (request.typeIs(FlowCallbackRequest.TYPE_DELETE)) {
            cgBiddingDocumentFacade.updateStatus(Integer.parseInt(request.getBusinessKey()),"EDIT");
        } else if (request.typeIs(FlowCallbackRequest.TYPE_END)) {
            String businessKey = request.getBusinessKey();
            CgBiddingDocumentDetailsDto cgBiddingDocumentDetailsDto = cgBiddingDocumentFacade.getDetailsById(Integer.parseInt(businessKey));
            List<CgProjectExecutionAttDto> cgProjectExecutionAttDtos = (cgBiddingDocumentDetailsDto.getBiddingDocumentAtts()).stream().filter(e -> e.getExt().equals("pdf")).collect(Collectors.toList());
            SdebankSDNSPaperless sd = new SdebankSDNSPaperless();
            //附件下载,以及附件盖章,重新上传
            for (CgProjectExecutionAttDto cgProjectExecutionAttDto : cgProjectExecutionAttDtos) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                fileStore.download(cgProjectExecutionAttDto.getPath(), out);
                try (ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray())) {
                    byte[] bytes = sd.sealAutoCrossPdf(in);
                    ByteArrayInputStream input = new ByteArrayInputStream(bytes);
                    String ID = fileStore.upload("/biddoc", cgProjectExecutionAttDto.getName(), input);
                    cgProjectExecutionAttDto.setPath(ID);
                    cgProjectExecutionAttDto.setUploadTime(LocalDateTime.now());
                } catch (Exception e) {
                    log.error("骑缝章生成失败!", e);
                    throw new FileStoreException(e);
                }
            }
            CgBiddingDocumentDetailsDto dto = cgBiddingDocumentFacade.updateAttachment(Integer.parseInt(request.getBusinessKey()), cgProjectExecutionAttDtos);
            List<String> prices = Collections.emptyList();
            if (dto.getNeedQuote()) {
                prices = cgProjectExecutionFacade.listPriceEvalRuleProductName(dto.getProjectExecution().getId());
            }
            cgBusinessReplyFacade.release(dto.getProjectExecution().getProject().getId().toString(),request.getBusinessKey(),
                    "招标中",dto.getName(), dto.getContent(),dto.getBidEndTime(),dto.getNeedQuote(),dto.getOpenBidStartTime(),
                    cgBiddingDocumentMapper.tranAtt(dto.getBiddingDocumentAtts()),prices,dto.getSupplierIds());
            cgBiddingDocumentFacade.updateStatus(Integer.parseInt(request.getBusinessKey()),"PASS");
        } else if (request.typeIs(FlowCallbackRequest.TYPE_LOAD_DATA)) {
            return ResponseEntity.ok(FlowCallbackResponse.ok(cgBiddingDocumentFacade.getById(Integer.parseInt(request.getBusinessKey()))));
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
