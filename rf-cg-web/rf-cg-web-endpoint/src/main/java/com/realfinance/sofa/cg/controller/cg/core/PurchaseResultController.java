package com.realfinance.sofa.cg.controller.cg.core;

import cn.hutool.core.io.FileUtil;
import com.alipay.sofa.rpc.common.utils.BeanUtils;
import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.cg.controller.flow.FlowApi;
import com.realfinance.sofa.cg.core.facade.*;
import com.realfinance.sofa.cg.core.model.*;
import com.realfinance.sofa.cg.model.CgOAJsonVo;
import com.realfinance.sofa.cg.model.cg.*;
import com.realfinance.sofa.cg.service.mapstruct.*;
import com.realfinance.sofa.cg.sup.facade.CgBusinessProjectFacade;
import com.realfinance.sofa.cg.sup.facade.CgSupplierFacade;
import com.realfinance.sofa.cg.sup.model.CgCreditDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierDetailsDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierDto;
import com.realfinance.sofa.cg.util.DataRuleHelper;
import com.realfinance.sofa.cg.util.LinkUtils;
import com.realfinance.sofa.common.datascope.DataScopeUtils;
import com.realfinance.sofa.common.filestore.FileStore;
import com.realfinance.sofa.common.filetoken.FileToken;
import com.realfinance.sofa.common.filetoken.FileTokens;
import com.realfinance.sofa.flow.facade.FlowFacade;
import com.realfinance.sofa.flow.model.FlowCallbackRequest;
import com.realfinance.sofa.flow.model.FlowCallbackResponse;
import com.realfinance.sofa.system.facade.AssociatedTranFacade;
import com.realfinance.sofa.system.facade.SystemQueryFacade;
import com.realfinance.sofa.system.model.AssociatedTranDto;
import com.realfinance.sofa.system.model.UserSmallDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@Tag(name = "采购结果")
@RequestMapping("/cg/core/purresult")
public class PurchaseResultController implements FlowApi {

    private static final Logger log = LoggerFactory.getLogger(PurchaseResultController.class);

    public static final String MENU_CODE_ROOT = "purresults";
//    public static final String MENU_CODE_VIEW = MENU_CODE_ROOT + ":view";
//    public static final String MENU_CODE_SAVE = MENU_CODE_ROOT + ":save";
//    public static final String MENU_CODE_SAVE = MENU_CODE_ROOT + ":save";

    @SofaReference(interfaceType = CgPurchaseResultFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt", timeout = 300000))
    private CgPurchaseResultFacade cgPurchaseResultFacade;
    @SofaReference(interfaceType = CgPurchaseResultNoticeFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgPurchaseResultNoticeFacade cgPurchaseResultNoticeFacade;
    @SofaReference(interfaceType = CgBusinessProjectFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgBusinessProjectFacade cgBusinessProjectFacade;
    @SofaReference(interfaceType = CgSupplierFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt", timeout = 300000))
    private CgSupplierFacade cgSupplierFacade;
    @SofaReference(interfaceType = CgProjectExecutionFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgProjectExecutionFacade cgProjectExecutionFacade;
    @SofaReference(interfaceType = CgProjectFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgProjectFacade cgProjectFacade;
    @SofaReference(interfaceType = CgMeetingFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgMeetingFacade cgMeetingFacade;
    @SofaReference(interfaceType = FlowFacade.class, uniqueId = "${service.rf-flow.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private FlowFacade flowFacade;
    @SofaReference(interfaceType = AssociatedTranFacade.class, uniqueId = "${service.rf-system.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private AssociatedTranFacade associatedTranFacade;
    @SofaReference(interfaceType = SystemQueryFacade.class, uniqueId = "${service.rf-system.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private SystemQueryFacade systemQueryFacade;
    @SofaReference(interfaceType = CgDrawExpertFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgDrawExpertFacade cgDrawExpertFacade;
    @SofaReference(interfaceType = CgOAJsonFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgOAJsonFacade cgOAJsonFacade;



    @Resource
    private CgPurchaseResultMapper cgPurchaseResultMapper;
    @Resource
    private CgSupplierMapper cgSupplierMapper;
    @Resource
    private DataRuleHelper dataRuleHelper;
    @Resource
    private CgMeetingConfereeMapper cgMeetingConfereeMapper;
    @Resource
    private CgMeetingSupplierMapper cgMeetingSupplierMapper;
    @Resource
    private CgProjectMapper cgProjectMapper;
    @Resource
    private CgRequirementMapper cgRequirementMapper;
    @Resource
    private FileStore fileStore;
    @Resource
    private CgRelationshipMapper cgRelationshipMapper;
    @Resource
    private CgMeetingMapper cgMeetingMapper;
    @Resource
    private CgDrawExpertMapper cgDrawExpertMapper;

    @GetMapping("list")
    @Operation(summary = "查询采购方案执行列表")
    public ResponseEntity<Page<CgPurchaseResultVo>> list(@ParameterObject CgPurchaseResultQueryCriteria queryCriteria,
                                                         Pageable pageable) {
        Page<CgPurchaseResultDto> result = cgPurchaseResultFacade.list(queryCriteria, PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(), Sort.by("createdTime").descending()));
        return ResponseEntity.ok(result.map(cgPurchaseResultMapper::toVo));
    }

    @GetMapping("getdetailsbyid")
    @Operation(summary = "查询采购结果详情")
    public ResponseEntity<CgPurchaseResultVo> getResultDetailsById(@Parameter(description = "ID") @RequestParam Integer id, @AuthenticationPrincipal Authentication authentication) {
//        dataRuleHelper.installDataRule(MENU_CODE_VIEW);
        CgPurchaseResultDetailsDto result = cgPurchaseResultFacade.getDetailsById(id);
        CgProjectDetailsDto cgProjectDetailsDto = cgProjectFacade.getDetailsById(result.getProjectId());
        CgProjectVo cgProjectVo = cgProjectMapper.toVo(cgProjectDetailsDto);
        CgRequirementVo cgRequirementVo = cgRequirementMapper.toVo(cgProjectDetailsDto.getRequirement());
        cgProjectVo.setRequirement(cgRequirementVo);
        CgPurchaseResultVo vo = cgPurchaseResultMapper.toVo(result);
        CgMeetingDetailsDto cgMeetingDetailsDto = cgMeetingFacade.getDetailsByProjectId(vo.getProjectId());
        CgMeetingVo cgMeetingVo = cgMeetingMapper.toVo(cgMeetingDetailsDto);
        CgDrawExpertDetailsDto cgDrawExpertDetailsDto = cgDrawExpertFacade.getDetailsByProjectId(vo.getProjectId());
        CgDrawExpertVo cgDrawExpertVo = cgDrawExpertMapper.toVo(cgDrawExpertDetailsDto);
        vo.setDrawExpert(cgDrawExpertVo);
        vo.setProject(cgProjectVo);
        vo.setMeeting(cgMeetingVo);
        vo.setFlowInfo(getFlowInfo(id.toString()));
        if (vo.getPurResultAtts() != null) {
            for (CgPurResultAttVo purResultAttVo : vo.getPurResultAtts()) {
                FileToken fileToken = FileTokens.create(purResultAttVo.getPath(), purResultAttVo.getName(), authentication.getName());
                purResultAttVo.setLink(LinkUtils.createFileDownloadLink(fileToken));
            }
        }

        //查询部门数据
        for (CgPurResultExpertVo purResultExpert : vo.getPurResultExperts()) {
            purResultExpert.setDepartment(systemQueryFacade.queryDepartmentSmallDto(purResultExpert.getExpertDepartment()));
        }
        return ResponseEntity.ok(vo);
    }



    @GetMapping("getprojdetailsbyid")
    @Operation(summary = "生成基础的采购结果")
    public ResponseEntity<Integer> generateResult(@Parameter(description = "采购方案ID") @RequestParam Integer projectId, @RequestParam Integer projectExeId) {
        //采购方案
        CgProjectDetailsDto projectDetails = cgProjectFacade.getDetailsById(projectId);

        //参会人员
        CgConfereeQueryCriteria confereeQueryCriteria = new CgConfereeQueryCriteria();
        confereeQueryCriteria.setProjectId(projectId);
        confereeQueryCriteria.setType("PBZJ");
        List<CgMeetingConfereeDto> conferees = cgMeetingFacade.confereeListExpert(confereeQueryCriteria);

        //评审会供应商
        CgSupplierQueryCriteria supplierQueryCriteria = new CgSupplierQueryCriteria();
        supplierQueryCriteria.setProjectExecutionId(projectExeId);
        List<CgProjectExecutionSupDto> projectExecutionSups = cgMeetingFacade.listSupplier(supplierQueryCriteria);

        Integer result = cgPurchaseResultFacade.generateResult(projectExeId, projectDetails, conferees, projectExecutionSups);
//        CgProjectVo vo = cgProjectMapper.toVo(result);
        return ResponseEntity.ok(result);
    }

    @PostMapping("save")
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.ProjectExecutionController).MENU_CODE_SAVE)")
    public ResponseEntity<Integer> save(@Validated @RequestBody CgPurchaseResultVo vo) {
        if (vo.getId() != null) {
            checkBusinessDataIsEditable(getFlowInfo(vo.getId().toString()));
        }
        CgPurchaseResultDetailsDto saveDto = cgPurchaseResultMapper.toSaveDto(vo);
        Integer id = cgPurchaseResultFacade.save(saveDto);
        return ResponseEntity.ok(id);
    }

    @GetMapping("getsupdetailsbyid")
    @Operation(summary = "采购结果-查询供应商详情")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.sup.SupplierController).MENU_CODE_VIEW)")
    public ResponseEntity<CgSupplierVo> getDetailsById(@Parameter(description = "供应商ID") @RequestParam Integer id) {
        CgSupplierDetailsDto result = cgSupplierFacade.getDetailsById(id);
        return ResponseEntity.ok(cgSupplierMapper.toVo(result));
    }


    @PostMapping("fileupload")
    @Operation(summary = "文件上传")
    public ResponseEntity<CgPurResultAttVo> fileUpload(@RequestParam("file") MultipartFile file,
                                              @AuthenticationPrincipal Authentication authentication) {
        String id = fileStore.upload("/pur", file.getOriginalFilename(), file.getResource());
        CgPurResultAttVo cgPurResultAttVo = new CgPurResultAttVo();
        cgPurResultAttVo.setExt(FileUtil.extName(file.getOriginalFilename()));
        cgPurResultAttVo.setName(file.getOriginalFilename());
        cgPurResultAttVo.setPath(id);
        cgPurResultAttVo.setSize(file.getSize());
        cgPurResultAttVo.setUploadTime(LocalDateTime.now());
        cgPurResultAttVo.setSource("PURCHASERESULT");
        FileToken fileToken = FileTokens.create(id,file.getOriginalFilename(),authentication.getName());
        cgPurResultAttVo.setLink(LinkUtils.createFileDownloadLink(fileToken));
        return ResponseEntity.ok(cgPurResultAttVo);
    }

    @PostMapping("getOAJson")
    @Operation(summary = "接收OA系统json数据")
    public ResponseEntity<CgAttVo> getOAJson(Authentication authentication,
                                             @RequestBody List<CgOAJsonVo> json) {
        CgOAJsonVo cgOAJsonVo = json.get(0);
        CgOAJsonDto cgOAJsonDto = new CgOAJsonDto();
        BeanUtils.copyProperties(cgOAJsonVo, cgOAJsonDto);
        byte[] bytes = cgOAJsonFacade.getOAJson(cgOAJsonDto);
        InputStream in = new ByteArrayInputStream(bytes);
        MultipartFile file = null;
        try {
            file = new MockMultipartFile(ContentType.APPLICATION_OCTET_STREAM.toString(), in);
        } catch (IOException e) {
            log.error("bytes数组转MultipartFile失败!");
        }
        String fileName = StringUtils.substringAfterLast(cgOAJsonVo.getZIPFILE(), "/");
        String id = fileStore.upload("/pur", fileName, file.getResource());
        CgAttVo cgAttVo = new CgAttVo();
        cgAttVo.setExt(FileUtil.extName(fileName));
        cgAttVo.setName(fileName);
        cgAttVo.setPath(id);
        cgAttVo.setSize(file.getSize());
        cgAttVo.setUploadTime(LocalDateTime.now());
        cgAttVo.setSource("OA");
        FileToken fileToken = FileTokens.create(id, file.getOriginalFilename(), authentication.getName());
        cgAttVo.setLink(LinkUtils.createFileDownloadLink(fileToken));
        return ResponseEntity.ok(cgAttVo);
    }


    @PostMapping("supplierRelationship")
    @Operation(summary = "查询供应商关联关系")
    public ResponseEntity<List<CgPurchaseResultRelationshipVo>> relationship(@RequestBody CgPurchaseResultVo vo) {
        if (vo.getId() == null) {
            throw new RuntimeException("缺少采购方案id!");
        }
        if (vo.getRelationships()==null || vo.getRelationships().size() < 2) {
            throw new RuntimeException("供应商信息传值有误!");
        }
        List<CgPurchaseResultRelationshipDto> list = new ArrayList<>();
        for (CgPurchaseResultRelationshipVo cgPurchaseResultRelationshipVo : vo.getRelationships()) {
            CgPurchaseResultRelationshipDto cgPurchaseResultRelationshipDto = new CgPurchaseResultRelationshipDto();
            cgPurchaseResultRelationshipDto.setName(cgPurchaseResultRelationshipVo.getName());
            if (cgPurchaseResultRelationshipVo.getUnifiedSocialCreditCode() != null && cgPurchaseResultRelationshipVo.getUnifiedSocialCreditCode() != "") {
                cgPurchaseResultRelationshipDto.setUnifiedSocialCreditCode(cgPurchaseResultRelationshipDto.getUnifiedSocialCreditCode());
            }
            list.add(cgPurchaseResultRelationshipDto);
        }
        List<CgPurchaseResultRelationshipDto> dtos = cgPurchaseResultFacade.relationship(vo.getId(), list);
        if (dtos==null){
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.ok(dtos.stream().map(cgRelationshipMapper::toVo).collect(Collectors.toList()));
    }


    @PostMapping("supplierRelatedStatus")
    @Operation(summary = "查询供应商关联交易情况")
    public ResponseEntity<CgPurchaseResultVo> supplierRelatedStatus(@RequestBody CgPurchaseResultVo vo) {
        List<AssociatedTranDto> list = new ArrayList<>();
        for (CgPurResultSupVo supVo : vo.getPurResultSups()) {
            AssociatedTranDto dto = new AssociatedTranDto();
            dto.setSupId(supVo.getId());
            dto.setIdCardNumber(supVo.getSupplierId().getIdCardNumber());
            dto.setType("第二代身份证");
            list.add(dto);
        }
        List<AssociatedTranDto> associatedTranDtos = associatedTranFacade.selectIds(list);
        List<CgPurResultSupDto> dtos = new ArrayList<>();
        for (AssociatedTranDto associatedTranDto : associatedTranDtos) {
            CgPurResultSupDto cgPurResultSupDto = new CgPurResultSupDto();
            cgPurResultSupDto.setId(associatedTranDto.getSupId());
            cgPurResultSupDto.setSupplierRelatedStatus(associatedTranDto.getStatus());
            dtos.add(cgPurResultSupDto);
        }
        List<CgPurResultSupDto> supDtos = cgPurchaseResultFacade.updateProjectSupRelatedStatus(dtos);
        List<CgPurResultSupVo> collect = supDtos.stream().map(cgPurchaseResultMapper::cgPurchaseResultSupDtoToCgPurchaseResultSupVo).collect(Collectors.toList());
        vo.setPurResultSups(collect);
        return ResponseEntity.ok(vo);
    }

    @PostMapping("supplierCreditStatus")
    @Operation(summary = "查询供应商信用信息")
    public ResponseEntity<CgPurchaseResultVo> supplierCreditStatus(@RequestBody CgPurchaseResultVo vo) {
        List<CgCreditDto> cgCreditDtos=new ArrayList<>();
        for (CgPurResultSupVo cgPurResultSupVo : vo.getPurResultSups()) {
            CgCreditDto cgCreditDto = new CgCreditDto();
            cgCreditDto.setId(cgPurResultSupVo.getId());
            CgSupplierDto cgSupplierDto = new CgSupplierDto();
            BeanUtils.copyProperties(cgPurResultSupVo.getSupplierId(),cgSupplierDto);
            cgCreditDto.setSupplier(cgSupplierDto);
            cgCreditDtos.add(cgCreditDto);
        }
        //查询是否供应商是否有处罚记录且更新供应商库子表-信用信息表
        List<CgCreditDto> dtos = cgSupplierFacade.updateSupplierCredit(cgCreditDtos);
        List<CgPurResultSupDto> supDtos=new ArrayList<>();
        for (CgCreditDto dto : dtos) {
            CgPurResultSupDto supDto = new CgPurResultSupDto();
            supDto.setId(dto.getId());
            supDto.setSupplierCreditStatus(dto.getSupplierCreditStatus());
            supDto.setSupplierId(dto.getSupplier().getId());
            supDtos.add(supDto);
        }
        List<CgPurResultSupDto> dtoList = cgPurchaseResultFacade.updateProjectSupCreditStatus(supDtos);
        List<CgPurResultSupVo> collect = dtoList.stream().map(cgPurchaseResultMapper::cgPurchaseResultSupDtoToCgPurchaseResultSupVo).collect(Collectors.toList());
        vo.setPurResultSups(collect);
        return ResponseEntity.ok(vo);
    }

    @Override
    public ResponseEntity<String> flowStartProcess(Integer id, Map<String, String> formData) {
        CgPurchaseResultDetailsDto dto = cgPurchaseResultFacade.getById(id);
        CgPurchaseResultVo vo = cgPurchaseResultMapper.toVo(dto);
        String processInstanceId = getFlowFacade().startProcess(getBusinessCode(), id.toString(), formData,vo.getProject().getName());
        return ResponseEntity.ok(processInstanceId);
    }

    @Override
    public ResponseEntity<FlowCallbackResponse> flowCallback(@RequestBody FlowCallbackRequest request) {
        DataScopeUtils.installTenantId(request.getTenantId());
        if (request.typeIs(FlowCallbackRequest.TYPE_START)) {
            cgPurchaseResultFacade.updateStatus(Integer.parseInt(request.getBusinessKey()), "SUBMITTED",null,null);
        } else if (request.typeIs(FlowCallbackRequest.TYPE_DELETE)) {
            cgPurchaseResultFacade.updateStatus(Integer.parseInt(request.getBusinessKey()), "EDIT",null,null);
        } else if (request.typeIs(FlowCallbackRequest.TYPE_END)) {
            //CgPurchaseResultDetailsDto dto=cgPurchaseResultFacade.getById(Integer.parseInt(request.getBusinessKey()));
            //cgPurchaseResultNoticeFacade.release(request.getBusinessKey(),dto.getProjectId(),dto.getProjectexeId(),dto.getProjectNo(),dto.getName(),dto.getCreatedUserId(),dto.getEvalMethod(),dto.getPurResultSups());
            CgPurchaseResultDetailsDto result = cgPurchaseResultFacade.getDetailsById(Integer.parseInt(request.getBusinessKey()));
            CgPurchaseResultVo vo = cgPurchaseResultMapper.toVo(result);
            //获取(已推荐)供应商名称集合
            List<String> list = vo.getPurResultSups().stream().filter(e->e.getRecommend().equals(true)).map(e->e.getSupplierId().getName()).collect(Collectors.toList());
            cgPurchaseResultFacade.updateStatus(Integer.parseInt(request.getBusinessKey()), "PASS",vo.getProject().getReqUser().getRealname(),list);
        } else if (request.typeIs(FlowCallbackRequest.TYPE_LOAD_DATA)) {
            return ResponseEntity.ok(FlowCallbackResponse.ok(cgPurchaseResultFacade.getById(Integer.parseInt(request.getBusinessKey()))));
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
