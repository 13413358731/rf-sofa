package com.realfinance.sofa.cg.controller.cg.exp;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.cg.controller.flow.FlowApi;
import com.realfinance.sofa.cg.core.facade.CgExpertFacade;
import com.realfinance.sofa.cg.core.facade.CgExpertLabelFacade;
import com.realfinance.sofa.cg.core.facade.CgExpertLabelTypeFacade;
import com.realfinance.sofa.cg.core.model.*;
import com.realfinance.sofa.cg.model.cg.*;
import com.realfinance.sofa.cg.model.system.DepartmentTreeVo;
import com.realfinance.sofa.cg.model.system.UserQueryCriteriaRequest;
import com.realfinance.sofa.cg.model.system.UserVo;
import com.realfinance.sofa.cg.service.mapstruct.CgExpertLabelMapper;
import com.realfinance.sofa.cg.service.mapstruct.CgExpertMapper;
import com.realfinance.sofa.cg.service.mapstruct.DepartmentMapper;
import com.realfinance.sofa.cg.service.mapstruct.UserMapper;
import com.realfinance.sofa.cg.util.DataRuleHelper;
import com.realfinance.sofa.common.datascope.DataScopeUtils;
import com.realfinance.sofa.flow.facade.FlowFacade;
import com.realfinance.sofa.flow.model.FlowCallbackRequest;
import com.realfinance.sofa.flow.model.FlowCallbackResponse;
import com.realfinance.sofa.system.facade.SystemQueryFacade;
import com.realfinance.sofa.system.model.DepartmentSmallTreeDto;
import com.realfinance.sofa.system.model.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@Tag(name = "专家档案")
@RequestMapping("/cg/core/expertsdoc")
public class ExpertController implements FlowApi{

    private static final Logger log = LoggerFactory.getLogger(ExpertController.class);

    public static final String MENU_CODE_ROOT = "expertsdoc";
    public static final String MENU_CODE_VIEW = MENU_CODE_ROOT + ":view";

    @SofaReference(interfaceType = CgExpertFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgExpertFacade cgExpertFacade;
    @SofaReference(interfaceType = CgExpertLabelFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgExpertLabelFacade cgExpertLabelFacade;
    @SofaReference(interfaceType = CgExpertLabelTypeFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgExpertLabelTypeFacade cgExpertLabelTypeFacade;
    @SofaReference(interfaceType = FlowFacade.class, uniqueId = "${service.rf-flow.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private FlowFacade flowFacade;
    @SofaReference(interfaceType = SystemQueryFacade.class, uniqueId = "${service.rf-system.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private SystemQueryFacade systemQueryFacade;

    @Resource
    private DataRuleHelper dataRuleHelper;

    @Resource
    private CgExpertMapper cgExpertMapper;

    @Resource
    private CgExpertLabelMapper cgExpertLabelMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private DepartmentMapper departmentMapper;

    @GetMapping("list")
    @Operation(summary = "查询专家库列表")
    public ResponseEntity<Page<CgExpertVo>> list(CgExpertQueryCriteriaRequest queryCriteria,
                                                   Pageable pageable) {
        dataRuleHelper.installDataRule(MENU_CODE_VIEW);
        Page<CgExpertDto> result = cgExpertFacade.list(queryCriteria, pageable);
        return ResponseEntity.ok(result.map(cgExpertMapper::toVo));
    }

    @GetMapping("getdetailsbyid")
    @Operation(summary = "查询专家详情")
    public ResponseEntity<CgExpertVo> getDetailsById(@Parameter(description = "专家ID") @RequestParam Integer id) {
        dataRuleHelper.installDataRule(MENU_CODE_VIEW);
        CgExpertDetailsDto result = cgExpertFacade.getDetailsById(id);
        CgExpertVo vo = cgExpertMapper.toVo(result);
        vo.setFlowInfo(getFlowInfo(id.toString()));
        return ResponseEntity.ok(vo);
    }

    @GetMapping("querydepartmentrefer")
    @Operation(summary = "查询部门参照",description = "返回部门树，非系统租户只能返回当前登录的法人下的部门树")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<DepartmentTreeVo>> queryDepartmentRefer() {
        List<DepartmentSmallTreeDto> result = systemQueryFacade.queryDepartmentRefer();
        return ResponseEntity.ok(result.stream().map(departmentMapper::departmentSmallTreeDto2DepartmentTreeVo).collect(Collectors.toList()));
    }

    @GetMapping("queryexpertlabeltyperefer")
    @Operation(summary = "查询标签类型")
    public ResponseEntity<Page<CgExpertLabelTypeVo>> queryExpertLabelTypeRefer(@Parameter(description = "过滤条件") @RequestParam(required = false) String filter,
                                                                                   Pageable pageable) {
        Page<CgExpertLabelTypeDto> result = cgExpertLabelTypeFacade.list(filter, pageable);
        return ResponseEntity.ok(result.map(cgExpertLabelMapper::toVo));
    }
    
    @GetMapping("queryexpertlabelrefer")
    @Operation(summary = "查询标签")
    public ResponseEntity<List<CgExpertLabelTreeVo>> queryExpertLabelRefer(@Parameter(description = "供应商标签类型ID") @RequestParam Integer expertLabelTypeId) {
        CgExpertLabelQueryCriteria queryCriteria = new CgExpertLabelQueryCriteria();
        queryCriteria.setExpertLabelTypeId(expertLabelTypeId);
        List<CgExpertLabelDto> all = cgExpertLabelFacade.list(queryCriteria);
        List<CgExpertLabelTreeVo> result = cgExpertLabelMapper.buildSmallTree(all);
        return ResponseEntity.ok(result);
    }

    @GetMapping("queryuserrefer")
    @Operation(summary = "查询用户参照", description = "非系统租户只返回当前登录的法人下的用户")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<UserVo>> queryUserRefer(UserQueryCriteriaRequest queryCriteria,
                                                       Pageable pageable) {
        Page<UserDto> result = systemQueryFacade.queryUserRefer(queryCriteria,pageable);
        return ResponseEntity.ok(result.map(userMapper::userDto2UserVo));
    }

    @PostMapping("save")
    @Operation(summary = "保存专家")
    public ResponseEntity<Integer> save(@Validated(CgExpertVo.Save.class)
                                            @RequestBody CgExpertVo vo) {
        if (vo.getId() != null) {
            checkBusinessDataIsEditable(getFlowInfo(vo.getId().toString()));
        }
        CgExpertSaveDto saveDto = cgExpertMapper.toSaveDto(vo);
        Integer id = cgExpertFacade.save(saveDto);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("delete")
    @Operation(summary = "删除")
    public ResponseEntity<?> delete(@Parameter(description = "专家ID") @RequestParam Set<Integer> id) {
        cgExpertFacade.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("addexpertlabels")
    @Operation(summary = "添加标签关联")
    public ResponseEntity<?> addExpertLabels(@Parameter(description = "专家ID") @RequestParam Integer id,
                                               @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "标签ID集合") @RequestBody Set<Integer> expertLabelId) {
        cgExpertFacade.addExpertLabels(id,expertLabelId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("removeexpertlabels")
    @Operation(summary = "删除标签关联")
    public ResponseEntity<?> removeExpertLabels(@Parameter(description = "专家ID") @RequestParam Integer id,
                                                  @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "标签ID集合") @RequestBody Set<Integer> expertLabelId) {
        cgExpertFacade.removeExpertLabels(id,expertLabelId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<FlowCallbackResponse> flowCallback(@RequestBody FlowCallbackRequest request) {
        DataScopeUtils.installTenantId(request.getTenantId());
        if (request.typeIs(FlowCallbackRequest.TYPE_START)) {
            cgExpertFacade.updateStatus(Integer.parseInt(request.getBusinessKey()),"SUBMITTED");
        } else if (request.typeIs(FlowCallbackRequest.TYPE_DELETE)) {
            cgExpertFacade.updateStatus(Integer.parseInt(request.getBusinessKey()),"EDIT");
        } else if (request.typeIs(FlowCallbackRequest.TYPE_END)) {
            cgExpertFacade.updateStatus(Integer.parseInt(request.getBusinessKey()),"PASS");
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
