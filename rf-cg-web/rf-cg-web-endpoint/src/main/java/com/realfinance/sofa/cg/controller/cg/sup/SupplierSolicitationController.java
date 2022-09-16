package com.realfinance.sofa.cg.controller.cg.sup;


import cn.hutool.core.io.FileUtil;
import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.cg.controller.flow.FlowApi;
import com.realfinance.sofa.cg.model.cg.*;
import com.realfinance.sofa.cg.model.system.DepartmentTreeVo;
import com.realfinance.sofa.cg.model.system.UserQueryCriteriaRequest;
import com.realfinance.sofa.cg.model.system.UserVo;
import com.realfinance.sofa.cg.service.mapstruct.CgSupplierLabelMapper;
import com.realfinance.sofa.cg.service.mapstruct.CgSupplierSolicitationMapper;
import com.realfinance.sofa.cg.service.mapstruct.DepartmentMapper;
import com.realfinance.sofa.cg.service.mapstruct.UserMapper;
import com.realfinance.sofa.cg.sup.facade.CgSupplierLabelFacade;
import com.realfinance.sofa.cg.sup.facade.CgSupplierLabelTypeFacade;
import com.realfinance.sofa.cg.sup.facade.CgSupplierSolicitationFacade;
import com.realfinance.sofa.cg.sup.model.*;
import com.realfinance.sofa.cg.util.LinkUtils;
import com.realfinance.sofa.common.datascope.DataScopeUtils;
import com.realfinance.sofa.common.filestore.FileStore;
import com.realfinance.sofa.common.filetoken.FileToken;
import com.realfinance.sofa.common.filetoken.FileTokens;
import com.realfinance.sofa.flow.facade.FlowFacade;
import com.realfinance.sofa.flow.model.FlowCallbackRequest;
import com.realfinance.sofa.flow.model.FlowCallbackResponse;
import com.realfinance.sofa.system.facade.SystemQueryFacade;
import com.realfinance.sofa.system.facade.UserMngFacade;
import com.realfinance.sofa.system.model.DepartmentSmallTreeDto;
import com.realfinance.sofa.system.model.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cg/sup/solicitation")
@Tag(name = "供应商意向征集")
public class SupplierSolicitationController implements FlowApi {
    private static final Logger log = LoggerFactory.getLogger(SupplierSolicitationController.class);

    //TODO 不知是否需要设置权限
    public static final String MENU_CODE_ROOT = "suppliersolicitation";
    public static final String MENU_CODE_VIEW = MENU_CODE_ROOT + ":view";
    public static final String MENU_CODE_SAVE = MENU_CODE_ROOT + ":save";
    public static final String MENU_CODE_DELETE = MENU_CODE_ROOT + ":delete";
    public static final String MENU_CODE_APPROVE = MENU_CODE_ROOT + ":approve";


    @SofaReference(interfaceType = CgSupplierSolicitationFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgSupplierSolicitationFacade solicitationFacade;
    @SofaReference(interfaceType = CgSupplierLabelFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgSupplierLabelFacade labelFacade;
    @SofaReference(interfaceType = FlowFacade.class, uniqueId = "${service.rf-flow.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private FlowFacade flowFacade;
    @Resource
    private CgSupplierSolicitationMapper solicitationMapper;
    @Resource
    private CgSupplierLabelMapper cgSupplierLabelMapper;
    @SofaReference(interfaceType = UserMngFacade.class, uniqueId = "${service.rf-system.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private UserMngFacade userMngFacade;
    @SofaReference(interfaceType = SystemQueryFacade.class, uniqueId = "${service.rf-system.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private SystemQueryFacade systemQueryFacade;
    @Resource
    private UserMapper userMapper;
    @Resource
    private DepartmentMapper departmentMapper;
    @Resource
    private FileStore fileStore;
    @SofaReference(interfaceType = CgSupplierLabelFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgSupplierLabelFacade cgSupplierLabelFacade;
    @SofaReference(interfaceType = CgSupplierLabelTypeFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgSupplierLabelTypeFacade cgSupplierLabelTypeFacade;

    @GetMapping("queryuserrefer")
    @Operation(summary = "查询用户列表")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<UserVo>> list(UserQueryCriteriaRequest queryCriteria,
                                             Pageable pageable) {
        Page<UserDto> result = userMngFacade.list(queryCriteria, pageable);
        return ResponseEntity.ok(result.map(userMapper::userDto2UserVo));
    }

    @GetMapping("querydepartmentrefer")
    @Operation(summary = "查询部门参照", description = "返回部门树，非系统租户只能返回当前登录的法人下的部门树")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<DepartmentTreeVo>> queryDepartmentRefer() {
        List<DepartmentSmallTreeDto> result = systemQueryFacade.queryDepartmentRefer();
        return ResponseEntity.ok(result.stream().map(departmentMapper::departmentSmallTreeDto2DepartmentTreeVo).collect(Collectors.toList()));
    }

    // TODO: 2020/12/15 权限
    @GetMapping("querysupplierlabeltyperefer")
    @Operation(summary = "查询标签类型")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<CgSupplierLabelTypeVo>> querySupplierLabelTypeRefer(@Parameter(description = "过滤条件") @RequestParam(required = false) String filter, Pageable pageable) {
        Page<CgSupplierLabelTypeDto> result = cgSupplierLabelTypeFacade.list(filter, pageable);
        return ResponseEntity.ok(result.map(cgSupplierLabelMapper::toVo));
    }

    // TODO: 2020/12/15 权限
    @GetMapping("querysupplierlabelrefer")
    @Operation(summary = "查询标签")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<CgSupplierLabelTreeVo>> querySupplierLabelRefer(@Parameter(description = "供应商标签类型ID") @RequestParam Integer supplierLabelTypeId) {
        CgSupplierLabelQueryCriteria queryCriteria = new CgSupplierLabelQueryCriteria();
        queryCriteria.setSupplierLabelTypeId(supplierLabelTypeId);
        List<CgSupplierLabelDto> all = cgSupplierLabelFacade.list(queryCriteria);
        List<CgSupplierLabelTreeVo> result = cgSupplierLabelMapper.buildSmallTree(all);
        return ResponseEntity.ok(result);
    }


    @GetMapping("list")
    @Operation(summary = "意向征集列表")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.sup.SupplierSolicitationController).MENU_CODE_VIEW)")
    public ResponseEntity<Page<CgSupplierSolicitationVo>> list(Pageable pageable,
                                                               @ParameterObject CgSupplierSolicitationQueryCriteria queryCriteria) {
        Page<CgSupplierSolicitationDto> dtos = solicitationFacade.list(pageable, queryCriteria);
        Page<CgSupplierSolicitationVo> vos = dtos.map(solicitationMapper::SolicitationDetailsDto2SolicitationVo);

        return ResponseEntity.ok(vos);
    }

    @PostMapping("save")
    @Operation(summary = "保存更新")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.sup.SupplierSolicitationController).MENU_CODE_SAVE)")
    public ResponseEntity<Integer> save(@RequestBody CgSupplierSolicitationSaveRequest saveRequest) {
        if (saveRequest.getId() != null) {
            checkBusinessDataIsEditable(getFlowInfo(saveRequest.getId().toString()));
        }
        if (saveRequest.getStartTime().toInstant(ZoneOffset.of("+8")).toEpochMilli()>saveRequest.getEndTime().toInstant(ZoneOffset.of("+8")).toEpochMilli()){
            throw new RuntimeException("截止报名时间必须大于发布时间");
        }
        CgSupplierSolicitationSaveDto SaveDto = solicitationMapper.SolicitationSaveRequest2SolicitationSaveDto(saveRequest);
        Integer save = solicitationFacade.save(SaveDto);
        return ResponseEntity.ok(save);
    }


    @DeleteMapping("delete")
    @Operation(summary = "删除")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.sup.SupplierSolicitationController).MENU_CODE_DELETE)")
    public ResponseEntity<?> delete(@RequestParam Set<Integer> ids) {
        solicitationFacade.delete(ids);
        return ResponseEntity.ok().build();
    }


    @GetMapping("getdetail")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.sup.SupplierSolicitationController).MENU_CODE_VIEW)")
    @Operation(summary = "获取意向征集详情")
    public ResponseEntity<CgSupplierSolicitationVo> getdetail(@RequestParam Integer id, @AuthenticationPrincipal Authentication authentication) {
        CgSupplierSolicitationDetailsDto detailsDto = solicitationFacade.getdetail(id);
        CgSupplierSolicitationVo solicitationVo = solicitationMapper.SolicitationDetailsDto2SolicitationVo(detailsDto);
        solicitationVo.setFlowInfo(getFlowInfo(id.toString()));
        if (solicitationVo.getAttachments() != null) {
            for (CgAttVo Att : solicitationVo.getAttachments()) {
                FileToken fileToken = FileTokens.create(Att.getPath(), Att.getName(), authentication.getName());
                Att.setLink(LinkUtils.createFileDownloadLink(fileToken));
            }
        }
        return ResponseEntity.ok(solicitationVo);
    }


    @GetMapping("getsupplier")
    @Operation(summary = "获取已报名的供应商")
    public ResponseEntity<Page<SolicitationEnrollDto>> getsup(Integer SolicitationId, Pageable pageable) {
        Page<SolicitationEnrollDto> dtoList = solicitationFacade.findById(SolicitationId, pageable);
        return ResponseEntity.ok(dtoList);
    }


    @GetMapping("release")
    @Operation(summary = "发布/停用")
    public ResponseEntity<?> release(@Parameter(description = "供应商意向征集ID") @RequestParam Integer id, @Parameter(description = "发布状态") @RequestParam String releaseStatus) {
        solicitationFacade.release(id, releaseStatus);
        return ResponseEntity.ok().build();
    }

    @PostMapping("stops")
    @Operation(summary = "批量停用")
    public ResponseEntity<?> stops(@RequestBody List<Integer> ids) {
        solicitationFacade.stops(ids);
        return ResponseEntity.ok().build();
    }

    @PostMapping("fileupload")
    @Operation(summary = "文件上传")
    public ResponseEntity<CgAttVo> fileUpload(@RequestParam("file") MultipartFile file,
                                              @AuthenticationPrincipal Authentication authentication) {
        // TODO: 2021/2/23 上传路径
        String id = fileStore.upload("/solic", file.getOriginalFilename(), file.getResource());
        CgAttVo cgAttVo = new CgAttVo();
        cgAttVo.setExt(FileUtil.extName(file.getOriginalFilename()));
        cgAttVo.setName(file.getOriginalFilename());
        cgAttVo.setPath(id);
        cgAttVo.setSize(file.getSize());
        cgAttVo.setSource("SOLICITATION");
        cgAttVo.setUploadTime(LocalDateTime.now());
        FileToken fileToken = FileTokens.create(id, file.getOriginalFilename(), null);
        cgAttVo.setLink(LinkUtils.createFileDownloadLink(fileToken));
        return ResponseEntity.ok(cgAttVo);
    }


    @Override
    public ResponseEntity<String> flowStartProcess(Integer id, Map<String, String> formData) {
        CgSupplierSolicitationDetailsDto dto = solicitationFacade.getdetail(id);
        String processInstanceId = getFlowFacade().startProcess(getBusinessCode(), id.toString(), formData,dto.getTitle());
        return ResponseEntity.ok(processInstanceId);
    }

    @Override
    public ResponseEntity<FlowCallbackResponse> flowCallback(@RequestBody FlowCallbackRequest request) {
        DataScopeUtils.installTenantId(request.getTenantId());
        if (request.typeIs(FlowCallbackRequest.TYPE_START)) {
            solicitationFacade.updateStatus(Integer.parseInt(request.getBusinessKey()), "SUBMITTED");
        } else if (request.typeIs(FlowCallbackRequest.TYPE_DELETE)) {
            solicitationFacade.updateStatus(Integer.parseInt(request.getBusinessKey()), "EDIT");
        } else if (request.typeIs(FlowCallbackRequest.TYPE_END)) {
            solicitationFacade.updateStatus(Integer.parseInt(request.getBusinessKey()), "PASS");
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
