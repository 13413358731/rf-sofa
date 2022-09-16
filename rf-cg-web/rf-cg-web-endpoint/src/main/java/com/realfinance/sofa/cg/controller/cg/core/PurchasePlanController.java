package com.realfinance.sofa.cg.controller.cg.core;


import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.cg.controller.flow.FlowApi;
import com.realfinance.sofa.cg.core.facade.CgPurchasePlanFacade;
import com.realfinance.sofa.cg.core.model.*;
import com.realfinance.sofa.cg.model.cg.*;
import com.realfinance.sofa.cg.model.system.DepartmentTreeVo;
import com.realfinance.sofa.cg.model.system.UserQueryCriteriaRequest;
import com.realfinance.sofa.cg.model.system.UserVo;
import com.realfinance.sofa.cg.service.mapstruct.AnnualPlanMapper;
import com.realfinance.sofa.cg.service.mapstruct.CgPurchasePlanMapper;
import com.realfinance.sofa.cg.service.mapstruct.DepartmentMapper;
import com.realfinance.sofa.cg.service.mapstruct.UserMapper;
import com.realfinance.sofa.cg.util.ImportTollBatchBiz;
import com.realfinance.sofa.cg.util.POIUtils;
import com.realfinance.sofa.common.datascope.DataScopeUtils;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@Tag(name = "采购计划")
@RequestMapping("/cg/core/purchaseplan")
public class PurchasePlanController implements FlowApi {

    private static final Logger log = LoggerFactory.getLogger(PurchasePlanController.class);

    //TODO 不知是否需要设置权限
    public static final String MENU_CODE_ROOT = "purchaseplan";
    public static final String MENU_CODE_VIEW = MENU_CODE_ROOT + ":view";
    public static final String MENU_CODE_SAVE = MENU_CODE_ROOT + ":save";
    public static final String MENU_CODE_DELETE = MENU_CODE_ROOT + ":delete";
    public static final String MENU_CODE_APPROVE = MENU_CODE_ROOT + ":approve";

    @SofaReference(interfaceType = FlowFacade.class, uniqueId = "${service.rf-flow.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private FlowFacade flowFacade;
    @SofaReference(interfaceType = CgPurchasePlanFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgPurchasePlanFacade cgPurchasePlanFacade;
    @Resource
    private CgPurchasePlanMapper cgPurchasePlanMapper;
    @Resource
    private AnnualPlanMapper annualPlanMapper;
    @SofaReference(interfaceType = UserMngFacade.class, uniqueId = "${service.rf-system.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private UserMngFacade userMngFacade;
    @SofaReference(interfaceType = SystemQueryFacade.class, uniqueId = "${service.rf-system.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private SystemQueryFacade systemQueryFacade;

    @Resource
    private UserMapper userMapper;

    @Resource
    private DepartmentMapper departmentMapper;

    @Autowired
    private ImportTollBatchBiz importTollBatchBiz;

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

    @GetMapping("listpurchaseplan")
    @Operation(summary = "采购计划列表")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.PurchasePlanController).MENU_CODE_VIEW)")
    public ResponseEntity<Page<CgPurchasePlanVo>> list(@Parameter(description = "年度计划ID") @RequestParam Integer id, Pageable pageable, @ParameterObject CgPurchasePlanQueryCriteria queryCriteria) {
        Page<CgPurchasePlanDto> list = cgPurchasePlanFacade.list(pageable, id, queryCriteria);
        Page<CgPurchasePlanVo> map = list.map(cgPurchasePlanMapper::toVo);
        return ResponseEntity.ok(map);
    }

    @GetMapping("listannualplan")
    @Operation(summary = "年度计划列表")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.PurchasePlanController).MENU_CODE_VIEW)")
    public ResponseEntity<Page<AnnualPlanVo>> list(Pageable pageable) {
        //List<AnnualPlanDto> list = cgPurchasePlanFacade.list(pageable);
        Page<AnnualPlanDto> list = cgPurchasePlanFacade.list(pageable);
        //return ResponseEntity.ok(list.stream().map(annualPlanMapper::toVo).collect(Collectors.toList()));
        return ResponseEntity.ok(list.map(annualPlanMapper::toVo));
    }


    @GetMapping("getdetailsbyid")
    @Operation(summary = "查询年度计划详情")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.PurchasePlanController).MENU_CODE_VIEW)")
    public ResponseEntity<AnnualPlanVo> getDetailsById(@Parameter(description = "年度计划ID") @RequestParam Integer id) {
        AnnualPlanDto dto = cgPurchasePlanFacade.getDetailsById(id);
        AnnualPlanVo vo = annualPlanMapper.toVo(dto);
        vo.setFlowInfo(getFlowInfo(id.toString()));
        return ResponseEntity.ok(vo);
    }


    @PostMapping("save")
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.PurchasePlanController).MENU_CODE_SAVE)")
    public ResponseEntity<Integer> save(@RequestBody CgAnnualPlanSaveRequest request) {
        AnnualPlanSaveDto saveDto = cgPurchasePlanMapper.toSaveDto(request);
        Integer id = cgPurchasePlanFacade.save(saveDto);
        return ResponseEntity.ok(id);
    }


    @PostMapping("saveurchaseplan")
    @Operation(summary = "新增修改采购计划")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.PurchasePlanController).MENU_CODE_SAVE)")
    public ResponseEntity<Integer> save(@RequestBody CgPurchasePlanSaveRequest request) {
        CgPurchasePlanSaveDto saveDto = cgPurchasePlanMapper.toSaveDto(request);
        Integer id = cgPurchasePlanFacade.save(saveDto);
        return ResponseEntity.ok(id);
    }


    @PostMapping("import")
    @Operation(summary = "导入")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.PurchasePlanController).MENU_CODE_SAVE)")
    public ResponseEntity<Integer> leading(@RequestParam("file") MultipartFile file, @Parameter(description = "年度计划ID") @RequestParam Integer id) {
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (Exception e) {
            throw new RuntimeException("导入失败");
        }
        List<CgPurchasePlanImportDto> list = importTollBatchBiz.readExcel(inputStream);
        Integer size = cgPurchasePlanFacade.saveList(list, id);
        return ResponseEntity.ok(list.size());
    }

    @GetMapping("export")
    @Operation(summary = "导出")
    public ResponseEntity<byte[]> exportData(CgPurchasePlanQueryCriteriaRequest criteriaRequest){
        List<CgPurchasePlanDto> dto = cgPurchasePlanFacade.getList(criteriaRequest);
        List<CgPurchasePlanVo> list = dto.stream().map(cgPurchasePlanMapper::toVo).collect(Collectors.toList());
        return POIUtils.purchasePlan2Excel(list);
    }

    @DeleteMapping("delete")
    @Operation(summary = "删除")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.PurchasePlanController).MENU_CODE_DELETE)")
    public ResponseEntity<Integer> delete(@RequestParam Set<Integer> ids) {
        cgPurchasePlanFacade.delete(ids);
        return ResponseEntity.ok().build();
    }


    @Override
    public ResponseEntity<FlowCallbackResponse> flowCallback(@RequestBody FlowCallbackRequest request) {
        DataScopeUtils.installTenantId(request.getTenantId());
        if (request.typeIs(FlowCallbackRequest.TYPE_START)) {
            cgPurchasePlanFacade.updateStatus(Integer.parseInt(request.getBusinessKey()), "SUBMITTED");
        } else if (request.typeIs(FlowCallbackRequest.TYPE_DELETE)) {
            cgPurchasePlanFacade.updateStatus(Integer.parseInt(request.getBusinessKey()), "EDIT");
        } else if (request.typeIs(FlowCallbackRequest.TYPE_END)) {
            cgPurchasePlanFacade.updateStatus(Integer.parseInt(request.getBusinessKey()), "PASS");
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
