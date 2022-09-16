package com.realfinance.sofa.cg.controller.cg.core;

import cn.hutool.core.io.FileUtil;
import com.alipay.sofa.rpc.common.utils.BeanUtils;
import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.cg.controller.flow.FlowApi;
import com.realfinance.sofa.cg.core.facade.CgOAJsonFacade;
import com.realfinance.sofa.cg.core.facade.CgProjectFacade;
import com.realfinance.sofa.cg.core.facade.CgPurchaseCatalogFacade;
import com.realfinance.sofa.cg.core.facade.CgRequirementFacade;
import com.realfinance.sofa.cg.core.model.*;
import com.realfinance.sofa.cg.model.CgOAJsonVo;
import com.realfinance.sofa.cg.model.ModifiedRecordVo;
import com.realfinance.sofa.cg.model.cg.*;
import com.realfinance.sofa.cg.model.flow.HistoricActivityInstanceVo;
import com.realfinance.sofa.cg.model.system.DepartmentTreeVo;
import com.realfinance.sofa.cg.security.AuthInfo;
import com.realfinance.sofa.cg.service.mapstruct.*;
import com.realfinance.sofa.cg.sup.facade.CgBusinessProjectFacade;
import com.realfinance.sofa.cg.sup.facade.CgSupplierFacade;
import com.realfinance.sofa.cg.sup.facade.CgSupplierLabelFacade;
import com.realfinance.sofa.cg.sup.facade.CgSupplierLabelTypeFacade;
import com.realfinance.sofa.cg.sup.model.*;
import com.realfinance.sofa.cg.util.DataRuleHelper;
import com.realfinance.sofa.cg.util.LinkUtils;
import com.realfinance.sofa.cg.util.ProjectModifiedRecorder;
import com.realfinance.sofa.common.datascope.DataScopeUtils;
import com.realfinance.sofa.common.filestore.FileStore;
import com.realfinance.sofa.common.filetoken.FileToken;
import com.realfinance.sofa.common.filetoken.FileTokens;
import com.realfinance.sofa.common.util.SpringContextHolder;
import com.realfinance.sofa.flow.facade.FlowFacade;
import com.realfinance.sofa.flow.model.FlowCallbackRequest;
import com.realfinance.sofa.flow.model.FlowCallbackResponse;
import com.realfinance.sofa.flow.model.HistoricActivityInstanceDto;
import com.realfinance.sofa.sdebank.SdebankSDNSPaperless;
import com.realfinance.sofa.system.facade.AssociatedTranFacade;
import com.realfinance.sofa.system.facade.SystemQueryFacade;
import com.realfinance.sofa.system.model.AssociatedTranDto;
import com.realfinance.sofa.system.model.DepartmentSmallTreeDto;
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
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@Tag(name = "采购方案")
@RequestMapping("/cg/core/project")
public class ProjectController implements FlowApi {
    private static final Logger log = LoggerFactory.getLogger(ProjectController.class);

    public static final String MENU_CODE_ROOT = "purproj";
    public static final String MENU_CODE_VIEW = MENU_CODE_ROOT + ":view";
    public static final String MENU_CODE_SAVE = MENU_CODE_ROOT + ":save";
    public static final String MENU_CODE_RETURN = MENU_CODE_ROOT + ":return"; // 退回
    public static final String MENU_CODE_STARTPROC = MENU_CODE_ROOT + ":startproc";

    @SofaReference(interfaceType = SystemQueryFacade.class, uniqueId = "${service.rf-system.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private SystemQueryFacade systemQueryFacade;
    @SofaReference(interfaceType = CgProjectFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt", timeout = 300000))
    private CgProjectFacade cgProjectFacade;
    @SofaReference(interfaceType = CgRequirementFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt", timeout = 300000))
    private CgRequirementFacade cgRequirementFacade;
    @SofaReference(interfaceType = CgPurchaseCatalogFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgPurchaseCatalogFacade cgPurchaseCatalogFacade;
    @SofaReference(interfaceType = CgSupplierFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgSupplierFacade cgSupplierFacade;
    @SofaReference(interfaceType = CgSupplierLabelFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgSupplierLabelFacade cgSupplierLabelFacade;
    @SofaReference(interfaceType = CgSupplierLabelTypeFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgSupplierLabelTypeFacade cgSupplierLabelTypeFacade;
    @SofaReference(interfaceType = CgBusinessProjectFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgBusinessProjectFacade cgBusinessProjectFacade;
    @SofaReference(interfaceType = FlowFacade.class, uniqueId = "${service.rf-flow.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private FlowFacade flowFacade;
    @SofaReference(interfaceType = AssociatedTranFacade.class, uniqueId = "${service.rf-system.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private AssociatedTranFacade associatedTranFacade;
    @SofaReference(interfaceType = CgOAJsonFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgOAJsonFacade cgOAJsonFacade;

    @Resource
    private CgProjectMapper cgProjectMapper;
    @Resource
    private CgRequirementMapper cgRequirementMapper;
    @Resource
    private CgPurchaseCatalogMapper cgPurchaseCatalogMapper;
    @Resource
    private CgSupplierMapper cgSupplierMapper;
    @Resource
    private CgSupplierLabelMapper cgSupplierLabelMapper;
    @Resource
    private DepartmentMapper departmentMapper;
    @Resource
    private CgRelationshipMapper cgRelationshipMapper;

    @Resource
    private DataRuleHelper dataRuleHelper;

    @Resource
    private FileStore fileStore;

    @GetMapping("list")
    @Operation(summary = "查询采购方案列表")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.ProjectController).MENU_CODE_VIEW)")
    public ResponseEntity<Page<CgProjectVo>> list(@ParameterObject CgProjectQueryCriteria queryCriteria,
                                                  Pageable pageable) {
        dataRuleHelper.installDataRule(MENU_CODE_VIEW);
        Page<CgProjectDto> result = cgProjectFacade.list(queryCriteria, PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(), Sort.by("modifiedTime").descending()));
        return ResponseEntity.ok(result.map(cgProjectMapper::toVo));
    }

    @GetMapping("getdetailsbyid")
    @Operation(summary = "查询采购方案详情")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.ProjectController).MENU_CODE_VIEW)")
    public ResponseEntity<CgProjectVo> getDetailsById(@Parameter(description = "采购方案ID") @RequestParam Integer id) {
        dataRuleHelper.installDataRule(MENU_CODE_VIEW);
        CgProjectDetailsDto result = cgProjectFacade.getDetailsById(id);
        CgRequirementDetailsDto requirementDetailsDto = cgRequirementFacade.getDetailsById(result.getRequirement().getId());
        CgRequirementVo cgRequirementVo = cgRequirementMapper.toVo(requirementDetailsDto);
        CgProjectVo vo = cgProjectMapper.toVo(result);
        vo.setRequirement(cgRequirementVo);
        List<CgAttVo> projectAtts = vo.getProjectAtts();
        if(projectAtts!=null&&!projectAtts.isEmpty()){
            for(CgAttVo cgAttVo:projectAtts){
                FileToken fileToken = FileTokens.create(cgAttVo.getPath(), cgAttVo.getName());
                cgAttVo.setLink(LinkUtils.createFileDownloadLink(fileToken));
            }
        }
        vo.setFlowInfo(getFlowInfo(id.toString()));
        //获取采购申请中的追踪流程信息
        List<HistoricActivityInstanceDto> historicActivityInstance = getFlowFacade().listHistoricActivityInstance("purreq", vo.getRequirement().getId().toString(),true);
        FlowMapper flowMapper = SpringContextHolder.getBean(FlowMapper.class);
        List<HistoricActivityInstanceVo> requirementHistoricActivityInstanceVoList = historicActivityInstance.stream().map(flowMapper::historicActivityInstanceDto2HistoricActivityInstanceVo).collect(Collectors.toList());
        vo.getRequirement().setHistoricActivityInstanceVo(requirementHistoricActivityInstanceVoList);
        return ResponseEntity.ok(vo);
    }

    @GetMapping("getmodifyrecords")
    @Operation(summary = "修改记录", description = "与最近一次审批通过时的数据做对比")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.RequirementController).MENU_CODE_VIEW)")
    public ResponseEntity<List<ModifiedRecordVo>> getModifyRecords(@Parameter(description = "采购需求ID") @RequestParam Integer id,
                                                                   @Parameter(description = "是否查询所有历史，如果false则查询最新一次，如果true则查询所有") @RequestParam(defaultValue = "false") Boolean all) {
        dataRuleHelper.installDataRule(MENU_CODE_VIEW);
        List<CgProjectDetailsDto> data = cgProjectFacade.listHistoricDetailsById(id, all);
        List<ModifiedRecordVo> result= ProjectModifiedRecorder.getRecords(data.stream().map(cgProjectMapper::toVo).collect(Collectors.toList()));
        return ResponseEntity.ok(result);
    }

    @PostMapping("save")
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.ProjectController).MENU_CODE_SAVE)")
    public ResponseEntity<Integer> save(@Validated @RequestBody CgProjectVo vo) {
        if (vo.getId() != null) {
            checkBusinessDataIsEditable(getFlowInfo(vo.getId().toString()));
        }
        /*List<CgRequirementOaDatumDto> requirementOaData = vo.getRequirementOaData();
        if (requirementOaData == null || requirementOaData.isEmpty()) {
            throw new RuntimeException("缺少立项审批号");
        }*/
        List<CgProjectOaDatumDto> projectOaData=vo.getProjectOaData();
        if (projectOaData == null || projectOaData.isEmpty()) {
            throw new RuntimeException("缺少立项审批号");
        }
        CgProjectDetailsSaveDto saveDto = cgProjectMapper.toSaveDto(vo);
        Integer id = cgProjectFacade.save(saveDto);
        return ResponseEntity.ok(id);
    }

  /*  @GetMapping("getmodifyrecords")
    @Operation(summary = "修改记录", description = "与最近一次审批通过时的数据做对比")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.ProjectController).MENU_CODE_VIEW)")
    public ResponseEntity<List<ModifiedRecordVo>> getModifyRecords(@Parameter(description = "采购方案ID") @RequestParam Integer id,
                                                                   @Parameter(description = "是否查询所有历史，如果false则查询最新一次，如果true则查询所有") @RequestParam(defaultValue = "false") Boolean all) {
        dataRuleHelper.installDataRule(MENU_CODE_VIEW);
        List<CgRequirementDetailsDto> data = cgProjectFacade.listHistoricDetailsById(id,all);
        if (data.size() < 2) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        List<ModifiedRecordVo> result = RequirementRecorder.getRecords(data.stream().map(cgRequirementMapper::toVo).collect(Collectors.toList()));
        return ResponseEntity.ok(result);
    }
*/
    @GetMapping("getreqdetailsbyid")
    @Operation(summary = "查询采购需求详情")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.RequirementController).MENU_CODE_VIEW)")
    public ResponseEntity<CgRequirementVo> getRequirementDetailsById(@Parameter(description = "采购方案ID") @RequestParam Integer id) {
        dataRuleHelper.installDataRule(MENU_CODE_VIEW);
        CgRequirementDetailsDto result = cgProjectFacade.getRequirementDetailsById(id);
        CgRequirementVo vo = cgRequirementMapper.toVo(result);
        vo.setFlowInfo(getFlowInfo(id.toString()));
        return ResponseEntity.ok(vo);
    }

    @PostMapping("return")
    @Operation(summary = "退回需求")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.ProjectController).MENU_CODE_RETURN)")
    public ResponseEntity<Void> returnReq(@RequestBody ReturnRequirementRequest returnRequirementRequest,
                                          @AuthenticationPrincipal AuthInfo authInfo) {
        String reason = String.format("%s(%s) 于 %s 退回，原因：%s",
                authInfo.getUser().getRealname(),authInfo.getUser().getUsername(),
                LocalDateTime.now().toString(),returnRequirementRequest.getReason());
        //dataRuleHelper.installDataRule(MENU_CODE_RETURN);
        cgProjectFacade.returnRequirement(returnRequirementRequest.getId(),reason);
        return ResponseEntity.ok().build();
    }

    @GetMapping("querysupplierrefer")
    @Operation(summary = "查询供应商参照")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.ProjectController).MENU_CODE_SAVE)")
    public ResponseEntity<Page<CgSupplierVo>> querySupplierRefer(CgSupplierQueryCriteriaRequest queryCriteria,
                                                                 Pageable pageable) {
        Page<CgSupplierDto> result = cgSupplierFacade.list(queryCriteria, pageable);
        return ResponseEntity.ok(result.map(cgSupplierMapper::toVo));
    }

    @GetMapping("querysuppliercontacts")
    @Operation(summary = "查询供应商联系人列表")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.ProjectController).MENU_CODE_SAVE)")
    public ResponseEntity<List<CgSupplierContactsDto>> querySupplierContacts(Integer supplierId) {
        CgSupplierDetailsDto cgSupplierDetailsDto = cgSupplierFacade.getDetailsById(supplierId);
        List<CgSupplierContactsDto> contacts = cgSupplierDetailsDto.getContacts();
        return ResponseEntity.ok(contacts);
    }

    @GetMapping("querypurchasecatalogrefer")
    @Operation(summary = "查询采购目录参照")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.ProjectController).MENU_CODE_SAVE)")
    public ResponseEntity<List<CgPurchaseCatalogTreeVo>> queryPurchaseCatalogRefer() {
        CgPurchaseCatalogQueryCriteria queryCriteria = new CgPurchaseCatalogQueryCriteria();
        queryCriteria.setEnabled(true);
        List<CgPurchaseCatalogDto> result = cgPurchaseCatalogFacade.list(queryCriteria);
        return ResponseEntity.ok(cgPurchaseCatalogMapper.buildTree(result));
    }

    @GetMapping("querypriceevalformula")
    @Operation(summary = "查询价格计算公式")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.ProjectController).MENU_CODE_SAVE)")
    public ResponseEntity<List<CgPriceEvalFormulaDto>> queryPriceEvalFormula() {
        List<CgPriceEvalFormulaDto> result = cgProjectFacade.listPriceEvalFormula();
        return ResponseEntity.ok(result);
    }

    @GetMapping("querysupplierlabeltyperefer")
    @Operation(summary = "查询供应商标签类型")
    // TODO: 2020/12/15 权限
    public ResponseEntity<Page<CgSupplierLabelTypeVo>> querySupplierLabelTypeRefer(@Parameter(description = "过滤条件") @RequestParam(required = false) String filter,
                                                                                   Pageable pageable) {
        Page<CgSupplierLabelTypeDto> result = cgSupplierLabelTypeFacade.list(filter, pageable);
        return ResponseEntity.ok(result.map(cgSupplierLabelMapper::toVo));
    }

    @GetMapping("querysupplierlabelrefer")
    @Operation(summary = "查询供应商标签")
    // TODO: 2020/12/15 权限
    public ResponseEntity<List<CgSupplierLabelTreeVo>> querySupplierLabelRefer(@Parameter(description = "供应商标签类型ID") @RequestParam Integer supplierLabelTypeId) {
        CgSupplierLabelQueryCriteria queryCriteria = new CgSupplierLabelQueryCriteria();
        queryCriteria.setSupplierLabelTypeId(supplierLabelTypeId);
        List<CgSupplierLabelDto> all = cgSupplierLabelFacade.list(queryCriteria);
        List<CgSupplierLabelTreeVo> result = cgSupplierLabelMapper.buildSmallTree(all);
        return ResponseEntity.ok(result);
    }


    @GetMapping("querydepartmentrefer")
    @Operation(summary = "查询部门参照", description = "返回部门树，非系统租户只能返回当前登录的法人下的部门树")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.ProjectController).MENU_CODE_SAVE)")
    public ResponseEntity<List<DepartmentTreeVo>> queryDepartmentRefer() {
        List<DepartmentSmallTreeDto> result = systemQueryFacade.queryDepartmentRefer();
        return ResponseEntity.ok(result.stream().map(departmentMapper::departmentSmallTreeDto2DepartmentTreeVo).collect(Collectors.toList()));
    }

    @PostMapping("fileupload")
    @Operation(summary = "文件上传")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.ProjectController).MENU_CODE_SAVE)")
    public ResponseEntity<CgAttVo> fileUpload(@RequestParam("file") MultipartFile file,
                                              @AuthenticationPrincipal Authentication authentication) {
        // TODO: 2021/2/23 上传路径
        String id = fileStore.upload("/proj", file.getOriginalFilename(), file.getResource());
        CgAttVo cgAttVo = new CgAttVo();
        cgAttVo.setExt(FileUtil.extName(file.getOriginalFilename()));
        cgAttVo.setName(file.getOriginalFilename());
        cgAttVo.setPath(id);
        cgAttVo.setSize(file.getSize());
        cgAttVo.setSource("PROJECT");
        cgAttVo.setUploadTime(LocalDateTime.now());
        FileToken fileToken = FileTokens.create(id,file.getOriginalFilename(),authentication.getName());
        cgAttVo.setLink(LinkUtils.createFileDownloadLink(fileToken));
        return ResponseEntity.ok(cgAttVo);
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
        String id = fileStore.upload("/proj", fileName, file.getResource());
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
    public ResponseEntity<List<CgProjectRelationshipVo>> relationship(@RequestBody CgProjectVo vo) {
        if (vo.getId() == null) {
            throw new RuntimeException("缺少采购方案id!");
        }
        if (vo.getRelationships()==null || vo.getRelationships().size() < 2) {
            throw new RuntimeException("供应商信息传值有误!");
        }
        List<CgProjectRelationshipDto> list = new ArrayList<>();
        for (CgProjectRelationshipVo cgProjectRelationshipVo : vo.getRelationships()) {
            CgProjectRelationshipDto cgProjectRelationshipDto = new CgProjectRelationshipDto();
            cgProjectRelationshipDto.setName(cgProjectRelationshipVo.getName());
            if (cgProjectRelationshipVo.getUnifiedSocialCreditCode() != null && cgProjectRelationshipVo.getUnifiedSocialCreditCode() != "") {
                cgProjectRelationshipDto.setUnifiedSocialCreditCode(cgProjectRelationshipDto.getUnifiedSocialCreditCode());
            }
            list.add(cgProjectRelationshipDto);
        }
        List<CgProjectRelationshipDto> dtos = cgProjectFacade.relationship(vo.getId(), list);
        if (dtos==null){
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.ok(dtos.stream().map(cgRelationshipMapper::toVo).collect(Collectors.toList()));
    }

    @PostMapping("supplierRelatedStatus")
    @Operation(summary = "查询供应商关联交易情况")
    public ResponseEntity<CgProjectVo> supplierRelatedStatus(@RequestBody CgProjectVo vo) {
        List<AssociatedTranDto> list = new ArrayList<>();
        for (CgProjectSupVo supVo : vo.getProjectSups()) {
            AssociatedTranDto dto = new AssociatedTranDto();
            dto.setSupId(supVo.getId());
            dto.setIdCardNumber(supVo.getSupplier().getIdCardNumber());
            dto.setType("第二代身份证");
            list.add(dto);
        }
        List<AssociatedTranDto> associatedTranDtos = associatedTranFacade.selectIds(list);
        List<CgProjectSupDto> dtos = new ArrayList<>();
        for (AssociatedTranDto associatedTranDto : associatedTranDtos) {
            CgProjectSupDto cgProjectSupDto = new CgProjectSupDto();
            cgProjectSupDto.setId(associatedTranDto.getSupId());
            cgProjectSupDto.setSupplierRelatedStatus(associatedTranDto.getStatus());
            dtos.add(cgProjectSupDto);
        }
        List<CgProjectSupDto> supDtos = cgProjectFacade.updateProjectSupRelatedStatus(dtos);
        List<CgProjectSupVo> collect = supDtos.stream().map(cgProjectMapper::cgProjectSupDtoToCgProjectSupVo).collect(Collectors.toList());
        vo.setProjectSups(collect);
        return ResponseEntity.ok(vo);
    }

    @PostMapping("supplierCreditStatus")
    @Operation(summary = "查询供应商信用信息")
    public ResponseEntity<CgProjectVo> supplierCreditStatus(@RequestBody CgProjectVo vo) {
        List<CgCreditDto> cgCreditDtos=new ArrayList<>();
        for (CgProjectSupVo cgProjectSupVo : vo.getProjectSups()) {
            CgCreditDto cgCreditDto = new CgCreditDto();
            cgCreditDto.setId(cgProjectSupVo.getId());
            CgSupplierDto cgSupplierDto = new CgSupplierDto();
            BeanUtils.copyProperties(cgProjectSupVo.getSupplier(),cgSupplierDto);
            cgCreditDto.setSupplier(cgSupplierDto);
            cgCreditDtos.add(cgCreditDto);
        }
        //查询是否供应商是否有处罚记录且更新供应商库子表-信用信息表
        List<CgCreditDto> dtos = cgSupplierFacade.updateSupplierCredit(cgCreditDtos);
        List<CgProjectSupDto> supDtos=new ArrayList<>();
        for (CgCreditDto dto : dtos) {
            CgProjectSupDto supDto = new CgProjectSupDto();
            supDto.setId(dto.getId());
            supDto.setSupplierCreditStatus(dto.getSupplierCreditStatus());
            supDto.setSupplierId(dto.getSupplier().getId());
            supDtos.add(supDto);
        }
        List<CgProjectSupDto> dtoList = cgProjectFacade.updateProjectSupCreditStatus(supDtos);
        List<CgProjectSupVo> collect = dtoList.stream().map(cgProjectMapper::cgProjectSupDtoToCgProjectSupVo).collect(Collectors.toList());
        vo.setProjectSups(collect);
        return ResponseEntity.ok(vo);
    }



   /* @GetMapping("exportWord")
    @Operation(summary = "导出采购方案呈批表")
    public ResponseEntity<byte[]> exportWord(@Parameter(description = "id") @RequestParam Integer id){
        CgProjectDetailsDto dto = cgProjectFacade.getDetailsById(id);
        CgRequirementVo cgRequirementVo = cgRequirementMapper.toVo(dto.getRequirement());
        CgProjectVo vo = cgProjectMapper.toVo(dto);
        vo.setRequirement(cgRequirementVo);
        return POIUtils.projectWord(vo);
    }*/

    @PostMapping("text")
    public void text(@RequestParam("file") MultipartFile file){
        byte[] pdf=null;
        try {
            byte[] bytes = file.getBytes();
            SdebankSDNSPaperless sd = new SdebankSDNSPaperless();
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
            pdf = sd.sealAutoPdf(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ByteArrayInputStream in = new ByteArrayInputStream(pdf);
        fileStore.upload("/req", file.getOriginalFilename(), in);
    }
    @Override
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.RequirementController).MENU_CODE_STARTPROC)")
    public ResponseEntity<String> flowStartProcess(@Parameter(description = "ID") @RequestParam Integer id,
                                                   @RequestBody(required = false) Map<String, String> formData) {
        CgProjectDto dto = cgProjectFacade.getById(id);
        String processInstanceId = getFlowFacade().startProcess(getBusinessCode(), id.toString(), formData,dto.getName());
        return ResponseEntity.ok(processInstanceId);
    }

    @Override
    public ResponseEntity<FlowCallbackResponse> flowCallback(@RequestBody FlowCallbackRequest request) {
        DataScopeUtils.installTenantId(request.getTenantId());
        if (request.typeIs(FlowCallbackRequest.TYPE_START)) {
            cgProjectFacade.updateStatus(Integer.parseInt(request.getBusinessKey()),"SUBMITTED");
        } else if (request.typeIs(FlowCallbackRequest.TYPE_DELETE)) {
            cgProjectFacade.updateStatus(Integer.parseInt(request.getBusinessKey()),"EDIT");
        } else if (request.typeIs(FlowCallbackRequest.TYPE_END)) {
            CgProjectDto dto = cgProjectFacade.getById(Integer.parseInt(request.getBusinessKey()));
            cgBusinessProjectFacade.save(request.getBusinessKey(),dto.getProjectNo(),dto.getName());
            cgProjectFacade.updateStatus(Integer.parseInt(request.getBusinessKey()),"PASS");
        } else if (request.typeIs(FlowCallbackRequest.TYPE_LOAD_DATA)) {
            return ResponseEntity.ok(FlowCallbackResponse.ok(cgProjectFacade.getById(Integer.parseInt(request.getBusinessKey()))));
        } else if (request.typeIs("LOAD_FLOW_VARIABLE")) {
            Map<String, String> data = loadFlowVariable(Integer.parseInt(request.getBusinessKey()));
            return ResponseEntity.ok(FlowCallbackResponse.ok(data));
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
     * 加载流程需要的变量
     * @param id
     * @return
     */
    private Map<String,String> loadFlowVariable(Integer id) {
        Map<String, String> data = new HashMap<>();
        CgProjectDetailsDto detailsById = cgProjectFacade.getDetailsById(id);
        data.put("purMode", detailsById.getPurMode());
        data.put("purType", detailsById.getPurType());
        data.put("reqTotalAmount", detailsById.getReqTotalAmount() != null ? detailsById.getReqTotalAmount().toString() : "0");
        boolean matches = true;
        if (!"DYLY".equals(detailsById.getPurMode())) {
            matches = false;
            Set<Integer> supplierIds = detailsById.getProjectSups() == null
                    ? Collections.emptySet()
                    : detailsById.getProjectSups().stream().map(CgProjectSupDto::getSupplierId).collect(Collectors.toSet());
            if (detailsById.getSupLabelId() != null && !supplierIds.isEmpty()) {
                matches = cgSupplierFacade.matchesSupplierLabel(supplierIds, detailsById.getSupLabelId());
            }
        }
        data.put("supplierLabelMatches", String.valueOf(matches));
        return data;
    }

}
