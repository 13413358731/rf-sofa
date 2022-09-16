package com.realfinance.sofa.cg.controller.cg.core;

import cn.hutool.core.io.FileUtil;
import com.alipay.sofa.rpc.common.utils.BeanUtils;
import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.cg.controller.flow.FlowApi;
import com.realfinance.sofa.cg.core.facade.CgOAJsonFacade;
import com.realfinance.sofa.cg.core.facade.CgPurchaseCatalogFacade;
import com.realfinance.sofa.cg.core.facade.CgPurchasePlanFacade;
import com.realfinance.sofa.cg.core.facade.CgRequirementFacade;
import com.realfinance.sofa.cg.core.model.*;
import com.realfinance.sofa.cg.model.CgOAJsonVo;
import com.realfinance.sofa.cg.model.ModifiedRecordVo;
import com.realfinance.sofa.cg.model.cg.*;
import com.realfinance.sofa.cg.model.system.DepartmentTreeVo;
import com.realfinance.sofa.cg.model.system.UserQueryCriteriaRequest;
import com.realfinance.sofa.cg.model.system.UserVo;
import com.realfinance.sofa.cg.security.AuthInfo;
import com.realfinance.sofa.cg.service.mapstruct.*;
import com.realfinance.sofa.cg.sup.facade.CgSupplierFacade;
import com.realfinance.sofa.cg.sup.model.CgCreditDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierContactsDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierDetailsDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierDto;
import com.realfinance.sofa.cg.util.*;
import com.realfinance.sofa.common.datascope.DataScopeUtils;
import com.realfinance.sofa.common.filestore.FileStore;
import com.realfinance.sofa.common.filetoken.FileToken;
import com.realfinance.sofa.common.filetoken.FileTokens;
import com.realfinance.sofa.common.notice.email.Email;
import com.realfinance.sofa.flow.facade.FlowFacade;
import com.realfinance.sofa.flow.model.FlowCallbackRequest;
import com.realfinance.sofa.flow.model.FlowCallbackResponse;
import com.realfinance.sofa.sdebank.SdebankEmailSender;
import com.realfinance.sofa.system.facade.AssociatedTranFacade;
import com.realfinance.sofa.system.facade.SystemQueryFacade;
import com.realfinance.sofa.system.model.AssociatedTranDto;
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
import org.springframework.beans.factory.annotation.Autowired;
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
@Tag(name = "采购需求")
@RequestMapping("/cg/core/requirement")
public class RequirementController implements FlowApi {

    private static final Logger log = LoggerFactory.getLogger(RequirementController.class);

    public static final String MENU_CODE_ROOT = "purreq";
    public static final String MENU_CODE_VIEW = MENU_CODE_ROOT + ":view";
    public static final String MENU_CODE_SAVE = MENU_CODE_ROOT + ":save";
    public static final String MENU_CODE_DELETE = MENU_CODE_ROOT + ":delete";
    public static final String MENU_CODE_STARTPROC = MENU_CODE_ROOT + ":startproc";

    @SofaReference(interfaceType = SystemQueryFacade.class, uniqueId = "${service.rf-system.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private SystemQueryFacade systemQueryFacade;
    @SofaReference(interfaceType = CgRequirementFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt", timeout = 300000))
    private CgRequirementFacade cgRequirementFacade;
    @SofaReference(interfaceType = CgSupplierFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt", timeout = 300000))
    private CgSupplierFacade cgSupplierFacade;
    @SofaReference(interfaceType = CgPurchaseCatalogFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgPurchaseCatalogFacade cgPurchaseCatalogFacade;
    @SofaReference(interfaceType = FlowFacade.class, uniqueId = "${service.rf-flow.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private FlowFacade flowFacade;
    @SofaReference(interfaceType = CgOAJsonFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgOAJsonFacade cgOAJsonFacade;
    @SofaReference(interfaceType = CgPurchasePlanFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgPurchasePlanFacade cgPurchasePlanFacade;
    @SofaReference(interfaceType = AssociatedTranFacade.class, uniqueId = "${service.rf-system.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private AssociatedTranFacade associatedTranFacade;

    @Resource
    private DataRuleHelper dataRuleHelper;
    @Resource
    private FileStore fileStore;

    @Resource
    private CgPurchaseCatalogMapper cgPurchaseCatalogMapper;
    @Resource
    private CgRequirementMapper cgRequirementMapper;
    @Resource
    private CgSupplierMapper cgSupplierMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private DepartmentMapper departmentMapper;
    @Resource
    private AnnualPlanMapper annualPlanMapper;
    @Resource
    private CgPurchasePlanMapper cgPurchasePlanMapper;
    @Resource
    private CgRelationshipMapper cgRelationshipMapper;

    @Autowired
    private ImportTollRequirementItems importTollRequirementItems;

    @GetMapping("list")
    @Operation(summary = "查询采购需求列表")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.RequirementController).MENU_CODE_VIEW)")
    public ResponseEntity<Page<CgRequirementVo>> list(@ParameterObject CgRequirementQueryCriteria queryCriteria,
                                                      Pageable pageable) {
        dataRuleHelper.installDataRule(MENU_CODE_VIEW);
        Page<CgRequirementDto> result = cgRequirementFacade.list(queryCriteria, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("modifiedTime").descending()));
        return ResponseEntity.ok(result.map(cgRequirementMapper::toVo));
    }

    @GetMapping("getdetailsbyid")
    @Operation(summary = "查询采购需求详情")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.RequirementController).MENU_CODE_VIEW)")
    public ResponseEntity<CgRequirementVo> getDetailsById(@Parameter(description = "采购需求ID") @RequestParam Integer id,
                                                          @AuthenticationPrincipal Authentication authentication) {
//        dataRuleHelper.installDataRule(MENU_CODE_VIEW);
        CgRequirementDetailsDto result = cgRequirementFacade.getDetailsById(id);
        CgRequirementVo vo = cgRequirementMapper.toVo(result);
        vo.setFlowInfo(getFlowInfo(id.toString()));
        if (vo.getRequirementAtts() != null) {
            for (CgAttVo requirementAtt : vo.getRequirementAtts()) {
                FileToken fileToken = FileTokens.create(requirementAtt.getPath(), requirementAtt.getName(), authentication.getName());
                requirementAtt.setLink(LinkUtils.createFileDownloadLink(fileToken));
            }
        }
        //根据立项审批号,查询其他单据(非编辑状态的采购申请的立项审批数据)
        if (vo.getRequirementOaData() != null && vo.getRequirementOaData().size() != 0) {
            List<String> approvalNoList = vo.getRequirementOaData().stream().map(e -> e.getApprovalNo()).collect(Collectors.toList());
            List<CgRequirementOaDatumDto> oaDatumList = cgRequirementFacade.findOaDatumList(approvalNoList);
            vo.setRequirementOaDataPast(oaDatumList);
        }
        return ResponseEntity.ok(vo);
    }

    @GetMapping("getOaDataPast")
    @Operation(summary = "查询立项审批数据")
    public ResponseEntity<List<CgRequirementOaDatumDto>> getOaDataPast(@Parameter(description = "立项审批号")@RequestParam List<String> list){
        return  ResponseEntity.ok(cgRequirementFacade.findOaDatumList(list));
    }

    @GetMapping("getmodifyrecords")
    @Operation(summary = "修改记录", description = "与最近一次审批通过时的数据做对比")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.RequirementController).MENU_CODE_VIEW)")
    public ResponseEntity<List<ModifiedRecordVo>> getModifyRecords(@Parameter(description = "采购需求ID") @RequestParam Integer id,
                                                                   @Parameter(description = "是否查询所有历史，如果false则查询最新一次，如果true则查询所有") @RequestParam(defaultValue = "false") Boolean all) {
        dataRuleHelper.installDataRule(MENU_CODE_VIEW);
        List<CgRequirementDetailsDto> data = cgRequirementFacade.listHistoricDetailsById(id, all);
        List<ModifiedRecordVo> result = RequirementModifiedRecorder.getRecords(data.stream().map(cgRequirementMapper::toVo).collect(Collectors.toList()));
        return ResponseEntity.ok(result);
    }

    @GetMapping("getOaData")
    @Operation(summary = "根据立项审批号获取OA数据")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.RequirementController).MENU_CODE_SAVE)")
    public ResponseEntity<CgRequirementVo> getOaData(@RequestParam Set<String> approvalNo, @AuthenticationPrincipal AuthInfo authInfo) {
        UserVo userVo = new UserVo();
        userVo.setId(authInfo.getUser().getId());
        userVo.setUsername(authInfo.getUser().getUsername());
        userVo.setRealname(authInfo.getUser().getRealname());
        CgRequirementVo cgRequirementVo = new CgRequirementVo();
        List<CgAttVo> requirementAtts = approvalNo.stream().map(e -> {
            String uuid = UUID.randomUUID().toString();
            CgAttVo cgAttVo = new CgAttVo();
            cgAttVo.setPath("/fake/" + uuid + ".rar");
            cgAttVo.setExt(".rar");
            cgAttVo.setName("假的");
            cgAttVo.setSize(1L);
            cgAttVo.setSource("需求");
            cgAttVo.setUploadTime(LocalDateTime.now());
            return cgAttVo;
        }).collect(Collectors.toList());
        cgRequirementVo.setRequirementAtts(requirementAtts);
        List<CgRequirementOaDatumDto> oaData = approvalNo.stream().map(e -> {
            CgRequirementOaDatumDto cgRequirementOaDatumDto = new CgRequirementOaDatumDto();
            cgRequirementOaDatumDto.setApprovalNo(e);
            return cgRequirementOaDatumDto;
        }).collect(Collectors.toList());
        cgRequirementVo.setRequirementOaData(oaData);
        return ResponseEntity.ok(cgRequirementVo);
    }

    @GetMapping("querypurchasecatalogrefer")
    @Operation(summary = "查询采购目录参照")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.RequirementController).MENU_CODE_SAVE)")
    public ResponseEntity<List<CgPurchaseCatalogTreeVo>> queryPurchaseCatalogRefer() {
        CgPurchaseCatalogQueryCriteria queryCriteria = new CgPurchaseCatalogQueryCriteria();
        queryCriteria.setEnabled(true);
        List<CgPurchaseCatalogDto> result = cgPurchaseCatalogFacade.list(queryCriteria);
        return ResponseEntity.ok(cgPurchaseCatalogMapper.buildTree(result));
    }

    @GetMapping("querysupplierrefer")
    @Operation(summary = "查询供应商参照")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.RequirementController).MENU_CODE_SAVE)")
    public ResponseEntity<Page<CgSupplierVo>> querySupplierRefer(CgSupplierQueryCriteriaRequest queryCriteria,
                                                                 Pageable pageable) {
        Page<CgSupplierDto> result = cgSupplierFacade.list(queryCriteria, pageable);
        return ResponseEntity.ok(result.map(cgSupplierMapper::toVo));
    }

    @GetMapping("queryannualrefer")
    @Operation(summary = "查询年度计划参照")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.RequirementController).MENU_CODE_SAVE)")
    public ResponseEntity<List<AnnualPlanVo>> queryAnnualRefer() {
        List<AnnualPlanDto> list = cgPurchasePlanFacade.list();
        return ResponseEntity.ok(list.stream().map(annualPlanMapper::toVo).collect(Collectors.toList()));
    }

    @GetMapping("querypurplanrefer")
    @Operation(summary = "查询采购计划参照")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.RequirementController).MENU_CODE_SAVE)")
    public ResponseEntity<Page<CgPurchasePlanVo>> queryPurPlanRefer(CgPurchasePlanQueryCriteriaRequest queryCriteria, Pageable pageable) {
        Page<CgPurchasePlanDto> list = cgPurchasePlanFacade.list(queryCriteria, pageable);
        Page<CgPurchasePlanVo> map = list.map(cgPurchasePlanMapper::toVo);
        return ResponseEntity.ok(map);
    }

    @GetMapping("querydepartmentrefer")
    @Operation(summary = "查询部门参照", description = "返回部门树，非系统租户只能返回当前登录的法人下的部门树")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.RequirementController).MENU_CODE_SAVE)")
    public ResponseEntity<List<DepartmentTreeVo>> queryDepartmentRefer() {
        List<DepartmentSmallTreeDto> result = systemQueryFacade.queryDepartmentRefer();
        return ResponseEntity.ok(result.stream().map(departmentMapper::departmentSmallTreeDto2DepartmentTreeVo).collect(Collectors.toList()));
    }

    @GetMapping("queryuserrefer")
    @Operation(summary = "查询用户参照", description = "非系统租户只返回当前登录的法人下的用户")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.RequirementController).MENU_CODE_SAVE)")
    public ResponseEntity<Page<UserVo>> queryUserRefer(UserQueryCriteriaRequest queryCriteria,
                                                       Pageable pageable) {
        Page<UserDto> result = systemQueryFacade.queryUserRefer(queryCriteria, pageable);
        return ResponseEntity.ok(result.map(userMapper::userDto2UserVo));
    }

    @GetMapping("queryallusersrefer")
    @Operation(summary = "查询全部用户参照")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.RequirementController).MENU_CODE_SAVE)")
    public ResponseEntity<Page<UserVo>> queryAllUsersRefer(UserQueryCriteriaRequest queryCriteria,
                                                           Pageable pageable) {
        Page<UserDto> result = systemQueryFacade.queryAllUsersRefer(queryCriteria, pageable);
        return ResponseEntity.ok(result.map(userMapper::userDto2UserVo));
    }


    @GetMapping("querysuppliercontacts")
    @Operation(summary = "查询供应商联系人列表")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.RequirementController).MENU_CODE_SAVE)")
    public ResponseEntity<List<CgSupplierContactsDto>> querySupplierContacts(Integer supplierId) {
        CgSupplierDetailsDto cgSupplierDetailsDto = cgSupplierFacade.getDetailsById(supplierId);
        List<CgSupplierContactsDto> contacts = cgSupplierDetailsDto.getContacts();
        return ResponseEntity.ok(contacts);
    }

    @PostMapping("save")
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.RequirementController).MENU_CODE_SAVE)")
    public ResponseEntity<Integer> save(@Validated @RequestBody CgRequirementVo vo) {
        if (vo.getId() != null) {
            checkBusinessDataIsEditable(getFlowInfo(vo.getId().toString()));
        }
        List<CgRequirementOaDatumDto> requirementOaData = vo.getRequirementOaData();
        if (requirementOaData == null || requirementOaData.isEmpty()) {
            throw new RuntimeException("缺少立项审批号");
        }
        if (vo.getPurchasePlan() == null) {
            vo.setPlanStatus("JHW");
        } else {
            vo.setPlanStatus("JHN");
        }
        //清空可用金额,已占用金额
        for (CgRequirementOaDatumDto requirementOaDatum : vo.getRequirementOaData()) {
            requirementOaDatum.setRemainAmount(null);
            requirementOaDatum.setUsedAmount(null);
        }
        CgRequirementDetailsSaveDto saveDto = cgRequirementMapper.toSaveDto(vo);
        Integer id = cgRequirementFacade.save(saveDto);
        return ResponseEntity.ok(id);
    }

    @PostMapping("startprocess")
    @Operation(summary = "启动流程")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.RequirementController).MENU_CODE_STARTPROC)")
    public ResponseEntity<String> flowStartProcess(@RequestBody CgRequirementFlowStartVo vo) {
        List<CgRequirementOaDatumDto> requirementOaData = vo.getCgRequirementVo().getRequirementOaData();
        if (requirementOaData == null || requirementOaData.isEmpty()) {
            throw new RuntimeException("缺少立项审批号");
        }
        if (vo.getCgRequirementVo().getPurchasePlan() == null) {
            vo.getCgRequirementVo().setPlanStatus("JHW");
        } else {
            vo.getCgRequirementVo().setPlanStatus("JHN");
        }
        CgRequirementDetailsSaveDto saveDto = cgRequirementMapper.toSaveDto(vo.getCgRequirementVo());
        Integer id = cgRequirementFacade.save(saveDto);
        String processInstanceId = getFlowFacade().startProcess(getBusinessCode(), vo.getCgRequirementVo().getId().toString(), vo.getFormData(), vo.getCgRequirementVo().getName());
        return ResponseEntity.ok(processInstanceId);
    }

    @DeleteMapping("deleteprocess")
    @Operation(summary = "终止流程")
    public ResponseEntity<?> flowDeleteProcess(@Parameter(description = "ID") @RequestParam String processInstanceId,
                                               @Parameter(description = "采购需求id")@RequestParam Integer id,
                                               @RequestBody(required = false) Map<String, String> body) {
        //清空可用金额,已占用金额
        cgRequirementFacade.saveAmount(id);
        getFlowFacade().deleteProcess(getBusinessCode(), processInstanceId, Optional.ofNullable(body).map(e -> e.get("comment")).orElse(null));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("delete")
    @Operation(summary = "删除")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.RequirementController).MENU_CODE_DELETE)")
    public ResponseEntity<?> delete(@Parameter(description = "采购需求单ID") @RequestParam Set<Integer> id) {
        cgRequirementFacade.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("fileupload")
    @Operation(summary = "文件上传")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.RequirementController).MENU_CODE_SAVE)")
    public ResponseEntity<CgAttVo> fileUpload(@RequestParam("file") MultipartFile file,
                                              @AuthenticationPrincipal Authentication authentication) {
        // TODO: 2021/2/23 上传路径
        String id = fileStore.upload("/req", file.getOriginalFilename(), file.getResource());
        CgAttVo cgAttVo = new CgAttVo();
        cgAttVo.setExt(FileUtil.extName(file.getOriginalFilename()));
        cgAttVo.setName(file.getOriginalFilename());
        cgAttVo.setPath(id);
        cgAttVo.setSize(file.getSize());
        cgAttVo.setUploadTime(LocalDateTime.now());
        cgAttVo.setSource("REQUIREMENT");
        FileToken fileToken = FileTokens.create(id, file.getOriginalFilename(), authentication.getName());
        cgAttVo.setLink(LinkUtils.createFileDownloadLink(fileToken));
        return ResponseEntity.ok(cgAttVo);
    }


    @PostMapping("getOAJson")
    @Operation(summary = "接收OA系统json数据")
    public ResponseEntity<CgRequirementVo> getOAJson(Authentication authentication,
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
        String id = fileStore.upload("/req", fileName, file.getResource());
        CgRequirementVo vo = new CgRequirementVo();
        List<String> approvalNoList = new ArrayList<>();
        approvalNoList.add(cgOAJsonDto.getDOCNO());
        List<CgRequirementOaDatumDto> oaDatumList = cgRequirementFacade.findOaDatumList(approvalNoList);
        vo.setRequirementOaDataPast(oaDatumList);
        List<CgAttVo> cgAttVos = new ArrayList<>();
        CgAttVo cgAttVo = new CgAttVo();
        cgAttVo.setExt(FileUtil.extName(fileName));
        cgAttVo.setName(fileName);
        cgAttVo.setPath(id);
        cgAttVo.setSize(file.getSize());
        cgAttVo.setUploadTime(LocalDateTime.now());
        cgAttVo.setSource("OA");
        FileToken fileToken = FileTokens.create(id, file.getOriginalFilename(), authentication.getName());
        cgAttVo.setLink(LinkUtils.createFileDownloadLink(fileToken));
        cgAttVos.add(cgAttVo);
        vo.setRequirementAtts(cgAttVos);
        return ResponseEntity.ok(vo);
    }

    @PostMapping("supplierRelationship")
    @Operation(summary = "查询供应商关联关系")
    public ResponseEntity<List<CgRequirementRelationshipVo>> relationship(@RequestBody CgRequirementVo vo) {
        if (vo.getId() == null) {
            throw new RuntimeException("缺少采购需求id!");
        }
        if (vo.getRelationships() == null || vo.getRelationships().size() < 2) {
            throw new RuntimeException("供应商信息传值有误!");
        }
        List<CgRequirementRelationshipDto> list = new ArrayList<>();
        for (CgRequirementRelationshipVo cgRequirementRelationshipVo : vo.getRelationships()) {
            CgRequirementRelationshipDto cgRequirementRelationshipDto = new CgRequirementRelationshipDto();
            cgRequirementRelationshipDto.setName(cgRequirementRelationshipVo.getName());
            if (cgRequirementRelationshipVo.getUnifiedSocialCreditCode() != null && cgRequirementRelationshipVo.getUnifiedSocialCreditCode() != "") {
                cgRequirementRelationshipDto.setUnifiedSocialCreditCode(cgRequirementRelationshipVo.getUnifiedSocialCreditCode());
            }
            list.add(cgRequirementRelationshipDto);
        }
        List<CgRequirementRelationshipDto> dtos = cgRequirementFacade.relationship(vo.getId(), list);
        if (dtos == null) {
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.ok(dtos.stream().map(cgRelationshipMapper::toVo).collect(Collectors.toList()));
    }

    @PostMapping("supplierRelatedStatus")
    @Operation(summary = "查询供应商关联交易情况")
    public ResponseEntity<CgRequirementVo> supplierRelatedStatus(@RequestBody CgRequirementVo vo) {
        List<AssociatedTranDto> list = new ArrayList<>();
        for (CgRequirementSupVo supVo : vo.getRequirementSups()) {
            AssociatedTranDto dto = new AssociatedTranDto();
            dto.setSupId(supVo.getId());
            dto.setIdCardNumber(supVo.getSupplier().getIdCardNumber());
            dto.setType("第二代身份证");
            list.add(dto);
        }
        List<AssociatedTranDto> associatedTranDtos = associatedTranFacade.selectIds(list);
        List<CgRequirementSupDto> dtos = new ArrayList<>();
        for (AssociatedTranDto associatedTranDto : associatedTranDtos) {
            CgRequirementSupDto cgRequirementSupDto = new CgRequirementSupDto();
            cgRequirementSupDto.setId(associatedTranDto.getSupId());
            cgRequirementSupDto.setSupplierRelatedStatus(associatedTranDto.getStatus());
            dtos.add(cgRequirementSupDto);
        }
        List<CgRequirementSupDto> supDtos = cgRequirementFacade.updateRequirementSupRelatedStatus(dtos);
        List<CgRequirementSupVo> collect = supDtos.stream().map(cgRequirementMapper::cgRequirementSupDtoToCgRequirementSupVo).collect(Collectors.toList());
        vo.setRequirementSups(collect);
        return ResponseEntity.ok(vo);
    }

    @PostMapping("supplierCreditStatus")
    @Operation(summary = "查询供应商信用信息")
    public ResponseEntity<CgRequirementVo> supplierCreditStatus(@RequestBody CgRequirementVo vo) {
        List<CgCreditDto> cgCreditDtos = new ArrayList<>();
        for (CgRequirementSupVo requirementSup : vo.getRequirementSups()) {
            CgCreditDto cgCreditDto = new CgCreditDto();
            cgCreditDto.setId(requirementSup.getId());
            CgSupplierDto cgSupplierDto = new CgSupplierDto();
            BeanUtils.copyProperties(requirementSup.getSupplier(), cgSupplierDto);
            cgCreditDto.setSupplier(cgSupplierDto);
            cgCreditDtos.add(cgCreditDto);
        }
        //查询是否供应商是否有处罚记录且更新供应商库子表-信用信息表
        List<CgCreditDto> dtos = cgSupplierFacade.updateSupplierCredit(cgCreditDtos);
        List<CgRequirementSupDto> supDtos = new ArrayList<>();
        for (CgCreditDto dto : dtos) {
            CgRequirementSupDto supDto = new CgRequirementSupDto();
            supDto.setId(dto.getId());
            supDto.setSupplierCreditStatus(dto.getSupplierCreditStatus());
            supDto.setSupplierId(dto.getSupplier().getId());
            supDtos.add(supDto);
        }
        List<CgRequirementSupDto> dtoList = cgRequirementFacade.updateRequirementSupCreditStatus(supDtos);
        List<CgRequirementSupVo> collect = dtoList.stream().map(cgRequirementMapper::cgRequirementSupDtoToCgRequirementSupVo).collect(Collectors.toList());
        vo.setRequirementSups(collect);
        return ResponseEntity.ok(vo);
    }


    @PostMapping("importData")
    @Operation(summary = "导入清单")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.RequirementController).MENU_CODE_SAVE)")
    public ResponseEntity<Integer> importData(@RequestParam("file") MultipartFile file, @Parameter(description = "采购申请ID") @RequestParam Integer id) {
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (Exception e) {
            throw new RuntimeException("导入失败");
        }
        List<CgRequirementItemImportDto> list = importTollRequirementItems.readExcel(inputStream);
        if (list.size() <= 0) {
            throw new RuntimeException("数据有误");
        }
        Integer size = cgRequirementFacade.saveList(list, id);
        return ResponseEntity.ok(size);
    }


    @GetMapping("exportData")
    @Operation(summary = "导出清单")
    public ResponseEntity<byte[]> exportData(@Parameter(description = "采购申请ID") @RequestParam Integer id) {
        CgRequirementDetailsDto dto = cgRequirementFacade.getDetailsById(id);
        if (dto.getRequirementItems().size() <= 0) {
            throw new RuntimeException("没有数据可以导出!");
        }
        CgRequirementVo vo = cgRequirementMapper.toVo(dto);
        return POIUtils.requirementItem2Excel(vo.getRequirementItems());
    }


    @GetMapping("emailText")
    @Operation(summary = "邮箱测试接口")
    public void emailText(){
        Email email = new Email();
        email.setId("1");
        email.setBody("测试");
        email.setSubject("主题1");
        email.setTenantId("01");
        List<String> list=new ArrayList<>();
        list.add("liaojya@sdebank.com");
        email.setDestAddress(list);
        SdebankEmailSender sd = new SdebankEmailSender();
        sd.send(email);
        System.out.println("发送成功!");

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
            cgRequirementFacade.updateStatus(Integer.parseInt(request.getBusinessKey()), "SUBMITTED");
        } else if (request.typeIs(FlowCallbackRequest.TYPE_DELETE)) {
            cgRequirementFacade.updateStatus(Integer.parseInt(request.getBusinessKey()), "EDIT");
        } else if (request.typeIs(FlowCallbackRequest.TYPE_END)) {
            cgRequirementFacade.updateStatus(Integer.parseInt(request.getBusinessKey()), "PASS");
        } else if (request.typeIs(FlowCallbackRequest.TYPE_LOAD_DATA)) {
            return ResponseEntity.ok(FlowCallbackResponse.ok(cgRequirementFacade.getById(Integer.parseInt(request.getBusinessKey()))));
        } else if (request.typeIs("LOAD_FLOW_VARIABLE")) {
            Map<String, String> data = loadFlowVariable(Integer.parseInt(request.getBusinessKey()));
            return ResponseEntity.ok(FlowCallbackResponse.ok(data));
        }
        return ResponseEntity.ok(FlowCallbackResponse.ok());
    }

    /**
     * 加载流程需要的变量
     *
     * @param id
     * @return
     */
    private Map<String, String> loadFlowVariable(Integer id) {
        Map<String, String> data = new HashMap<>();
        CgRequirementDto dto = cgRequirementFacade.getById(id);
        String code = systemQueryFacade.findDepartmentCodeToUserId(dto.getCreatedUserId());
        data.put("departmentCode", code);
        return data;
    }

}
