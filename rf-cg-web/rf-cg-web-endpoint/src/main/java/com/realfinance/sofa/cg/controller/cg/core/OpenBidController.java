package com.realfinance.sofa.cg.controller.cg.core;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.cg.controller.flow.FlowApi;
import com.realfinance.sofa.cg.core.facade.CgBiddingDocumentFacade;
import com.realfinance.sofa.cg.core.facade.CgProjectExecutionFacade;
import com.realfinance.sofa.cg.core.model.CgBiddingDocumentDetailsDto;
import com.realfinance.sofa.cg.core.model.CgBiddingDocumentDto;
import com.realfinance.sofa.cg.core.model.CgBiddingDocumentQueryCriteria;
import com.realfinance.sofa.cg.core.model.CgProjectExecutionAttDto;
import com.realfinance.sofa.cg.model.cg.*;
import com.realfinance.sofa.cg.model.flow.FlowTaskVo;
import com.realfinance.sofa.cg.service.mapstruct.CgBiddingDocumentMapper;
import com.realfinance.sofa.cg.service.mapstruct.CgProjectExecutionMapper;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@Tag(name = "采购方案执行-唱标")
@RequestMapping("/cg/core/projexec/openbid")
public class OpenBidController implements FlowApi {
    private static final Logger log = LoggerFactory.getLogger(OpenBidController.class);

    public static final String MENU_CODE_ROOT = "openbid";
    public static final String MENU_CODE_VIEW = MENU_CODE_ROOT + ":view";
    public static final String MENU_CODE_OPEN_QUOTE1 = MENU_CODE_ROOT + ":opquote1"; // 同意开启价格
    public static final String MENU_CODE_OPEN_QUOTE2 = MENU_CODE_ROOT + ":opquote2"; // 开启价格
    public static final String MENU_CODE_OPEN_BIZ1 = MENU_CODE_ROOT + ":opbiz1"; // 同意开启商务
    public static final String MENU_CODE_OPEN_BIZ2 = MENU_CODE_ROOT + ":opbiz2"; // 开启商务

    @SofaReference(interfaceType = CgProjectExecutionFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgProjectExecutionFacade cgProjectExecutionFacade;
    @SofaReference(interfaceType = CgBiddingDocumentFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgBiddingDocumentFacade cgBiddingDocumentFacade;
    @SofaReference(interfaceType = CgBusinessReplyFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgBusinessReplyFacade cgBusinessReplyFacade;
    @SofaReference(interfaceType = FlowFacade.class, uniqueId = "${service.rf-flow.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private FlowFacade flowFacade;

    @Resource
    private FileStore fileStore;

    @Resource
    private DataRuleHelper dataRuleHelper;

    @Resource
    private CgBiddingDocumentMapper cgBiddingDocumentMapper;

    @Resource
    private CgProjectExecutionMapper cgProjectExecutionMapper;

    @GetMapping("list")
    @Operation(summary = "查询采购方案列表")
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
        // 查询供应商
        Integer projExecId = result.getProjectExecution().getId();
        List<CgProjectExecutionSupVo> suppliers = cgProjectExecutionFacade.getSuppliersById(projExecId)
                .stream().map(cgBiddingDocumentMapper::toVo)
//                .map(CgProjectExecutionSupVo::getSupplier)
                .filter(Objects::nonNull).collect(Collectors.toList());
        vo.setSuppliers(suppliers);

        List<CgProjectExecutionStepVo> steps = cgProjectExecutionFacade.getStepsById(projExecId)
                .stream().map(cgBiddingDocumentMapper::toVo)
                .filter(Objects::nonNull).collect(Collectors.toList());
        vo.setProjectExecutionSteps(steps);

        CgBusinessReplyQueryCriteria queryCriteria = new CgBusinessReplyQueryCriteria();
        queryCriteria.setReplyType("招标中");
        queryCriteria.setBusinessProjectId(vo.getProjectExecution().getProject().getId().toString());
        List<CgBusinessReplyDetailsDto> listDetails = cgBusinessReplyFacade.listDetails(queryCriteria);
        List<CgBusinessReplyVo> replyVoList = listDetails.stream().map(cgBiddingDocumentMapper::toVo).collect(Collectors.toList());
        vo.setReplies(replyVoList);
        vo.setFlowInfo(getFlowInfo(id.toString()));
        return ResponseEntity.ok(vo);
    }

    @PostMapping("updatesupreply")
    @Operation(summary = "更新应答的信息")
    public ResponseEntity<Integer> updateSupReply(@RequestBody CgBusinessReplyVo vo) {
        Integer id = cgBusinessReplyFacade.updateSupReply(vo.getId(),vo.getNormal(),vo.getNote());
        return ResponseEntity.ok(id);
    }

//    @PostMapping("savesupplier")
//    @Operation(summary = "唱标小保存更改供应商看板")
//    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.ProjectExecutionController).MENU_CODE_SAVE)")
//    public ResponseEntity<Integer> saveSupplier(@RequestParam Integer id, @RequestBody CgProjectExecutionSupVo projectExecutionSupVo) {
//        CgProjectExecutionSupDto dto = cgProjectExecutionMapper.cgProjectExecutionSupVoToCgProjectExecutionSupDto(projectExecutionSupVo);
//        Integer cgProjectExecutionSupId = cgProjectExecutionFacade.saveSupplier(id,dto);
//        return ResponseEntity.ok(cgProjectExecutionSupId);
//    }

    @GetMapping("getreplydetailsbyreplyid")
    @Operation(summary = "查询报价应答详情")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.BiddingDocumentController).MENU_CODE_VIEW)")
    public ResponseEntity<CgBusinessReplyVo> getReplyDetailsByReplyId(@Parameter(description = "应答ID") @RequestParam Integer replyId) {
        CgBusinessReplyDetailsDto result = cgBusinessReplyFacade.getDetailsById(replyId);
        decryptAtts3(result);
        CgBusinessReplyVo vo = cgProjectExecutionMapper.toVo(result);
        if(vo.getAttDs()!=null){
            for(CgSupplierAttachmentVo attachmentVo:vo.getAttDs()){
                FileToken fileToken = FileTokens.create(attachmentVo.getPath(), attachmentVo.getName());
                attachmentVo.setLink(LinkUtils.createFileDownloadLink(fileToken));
            }
        }
        if(vo.getAttUs()!=null){
            for(CgSupplierAttachmentVo attachmentVo:vo.getAttUs()){
                FileToken fileToken = FileTokens.create(attachmentVo.getPath(), attachmentVo.getName());
                attachmentVo.setLink(LinkUtils.createFileDownloadLink(fileToken));
            }
        }
        return ResponseEntity.ok(vo);
    }

    /*@PostMapping("openquote1")
    @Operation(summary = "监督人员同意开启价格标")
    //@PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.OpenBidController).MENU_CODE_OPEN_QUOTE1)")
    public ResponseEntity<Void> openQuote1(@RequestParam Integer id) {
        cgBiddingDocumentFacade.preQuote(id);
        return ResponseEntity.ok().build();

    }*/

    @PostMapping("openquote2")
    @Operation(summary = "经办人开标")
    //@PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.OpenBidController).MENU_CODE_OPEN_QUOTE2)")
    public ResponseEntity<Void> openQuote2(@RequestParam Integer id) {
        CgBiddingDocumentDetailsDto result = cgBiddingDocumentFacade.getDetailsById(id);
        Integer projExecId = result.getProjectExecution().getId();
        List<CgProjectExecutionSupVo> suppliers = cgProjectExecutionFacade.getSuppliersById(projExecId)
                .stream().map(cgBiddingDocumentMapper::toVo)
//                .map(CgProjectExecutionSupVo::getSupplier)
                .filter(Objects::nonNull).collect(Collectors.toList());
        if (suppliers==null || suppliers.size()<3){
            throw new RuntimeException("供应商不足3家！");
        }
        if (result.getPreOpenQuoteUserId()==null) {
            throw new RuntimeException("监督人员未签到!");
        }
        List<CgProjectExecutionAttDto> atts = decryptAtts(id);
        cgBiddingDocumentFacade.openQuote(id,atts);
        cgBusinessReplyFacade.updateOtherDescription(id.toString(),"招标中","开标开始");
        return ResponseEntity.ok().build();
    }


    @PostMapping("closequote2")
    @Operation(summary = "经办人结束开标")
    public ResponseEntity<Void> closequote2(@RequestParam Integer id) {
        cgBiddingDocumentFacade.closequote(id);
        //修改门户 其他说明字段
        cgBusinessReplyFacade.updateOtherDescription(id.toString(),"招标中","开标结束");
        return ResponseEntity.ok().build();
    }


    /*@PostMapping("openbiz2")
    @Operation(summary = "经办人开标")
    //@PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.OpenBidController).MENU_CODE_OPEN_BIZ2)")
    public ResponseEntity<Void> openBiz2(@RequestParam Integer id) {
        if (!cgBiddingDocumentFacade.checkOpenBiz(id)) {
            throw new RuntimeException("监督人员未签到!");
        }
        List<CgProjectExecutionAttDto> atts = decryptAtts(id);
        //生成开启价格表时间
        cgBiddingDocumentFacade.openBiz(id,atts);
        return null;
    }*/


    @PostMapping("startprocess")
    @Operation(summary = "发起监督通知")
    public ResponseEntity<String> flowStartProcess(@Parameter(description = "ID") @RequestParam Integer id,
                                                   @Parameter(description = "userId")@RequestParam Integer userId,
                                                   @RequestBody(required = false) Map<String, String> formData) {
        CgBiddingDocumentDetailsDto result = cgBiddingDocumentFacade.getDetailsById(id);
        String processInstanceId = getFlowFacade().startProcessToUserId(getBusinessCode(), id.toString(), formData,userId,result.getProjectExecution().getProject().getName());
        return ResponseEntity.ok(processInstanceId);
    }

    @Override
    @Operation(summary = "签到")
    public ResponseEntity<?> flowCompleteTask(FlowTaskVo flowTaskVo) {
        cgBiddingDocumentFacade.preBiz(Integer.parseInt(flowTaskVo.getProcessInstance().getBusinessKey()));
        getFlowFacade().completeTask(getBusinessCode(), flowTaskVo.getId(),
                flowTaskVo.getComment(), flowTaskVo.getNextUserTaskInfo(), flowTaskVo.getFormData());
        return ResponseEntity.ok().build();
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
        DataScopeUtils.installTenantId(request.getTenantId());
        if (request.typeIs(FlowCallbackRequest.TYPE_START)) {
            cgBiddingDocumentFacade.updatePreOpenBizTime(Integer.parseInt(request.getBusinessKey()),FlowCallbackRequest.TYPE_START);
        }
        return ResponseEntity.ok(FlowCallbackResponse.ok());
    }


    /**
     * 解密附件
     * @return
     */
    private List<CgProjectExecutionAttDto> decryptAtts(Integer id) {
        CgBiddingDocumentDto biddingDocumentDto = cgBiddingDocumentFacade.getById(id);
        CgBusinessReplyQueryCriteria queryCriteria = new CgBusinessReplyQueryCriteria();
        queryCriteria.setReplyType("发标");
        queryCriteria.setBusinessProjectId(biddingDocumentDto.getProjectExecution().getProject().getId().toString());
        List<CgBusinessReplyDto> list = cgBusinessReplyFacade
                .list(queryCriteria);
        List<CgProjectExecutionAttDto> result = new ArrayList<>();
        for (CgBusinessReplyDto cgBusinessReplyDto : list) {
            CgBusinessReplyDetailsDto details = cgBusinessReplyFacade.getDetailsById(cgBusinessReplyDto.getId());
            List<CgAttachmentDto> attUs = details.getAttUs();
            if (attUs != null) {
                for (CgAttachmentDto us : attUs) {
                    FastByteArrayOutputStream out = new FastByteArrayOutputStream();
                    fileStore.download(us.getPath(),out);
                    ByteArrayResource resource = new ByteArrayResource(FileCipherUtils.decrypt(out.toByteArray()));
                    String path = fileStore.upload("/biddoc", us.getName(), resource);
                    CgProjectExecutionAttDto projectExecutionAttDto = new CgProjectExecutionAttDto();
                    projectExecutionAttDto.setName(us.getName());
                    projectExecutionAttDto.setSource("PORTAL");
                    projectExecutionAttDto.setSize(us.getSize());
                    projectExecutionAttDto.setExt(us.getExt());
                    projectExecutionAttDto.setPath(path);
                    projectExecutionAttDto.setStepType("CB");
                    projectExecutionAttDto.setStepDataId(id);
                    projectExecutionAttDto.setEncrypted(false);
                    projectExecutionAttDto.setAttSign(us.getCategory());
                    projectExecutionAttDto.setSupplierId(cgBusinessReplyDto.getSupplierId());
                    projectExecutionAttDto.setUploadTime(us.getUploadTime());
                }
            }
     }
        return result;
    }

    private CgBusinessReplyDetailsDto decryptAtts3(CgBusinessReplyDetailsDto list) {
            List<CgAttachmentDto> attUs = list.getAttUs();
            if (attUs != null) {
                for (CgAttachmentDto us : attUs) {
                    FastByteArrayOutputStream out = new FastByteArrayOutputStream();
                    fileStore.download(us.getPath(),out);
                    ByteArrayResource resource = new ByteArrayResource(FileCipherUtils.decrypt(out.toByteArray()));
                    String path = fileStore.upload("/biddoc", us.getName(), resource);
                    us.setPath(path);
                }
            }
        return list;
    }

}
