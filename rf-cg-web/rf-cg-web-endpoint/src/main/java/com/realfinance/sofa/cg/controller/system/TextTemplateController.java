package com.realfinance.sofa.cg.controller.system;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.cg.model.system.*;
import com.realfinance.sofa.cg.service.mapstruct.DepartmentMapper;
import com.realfinance.sofa.cg.service.mapstruct.RoleMapper;
import com.realfinance.sofa.cg.service.mapstruct.TextTemplateMapper;
import com.realfinance.sofa.cg.service.mapstruct.UserMapper;
import com.realfinance.sofa.system.facade.SystemQueryFacade;
import com.realfinance.sofa.system.facade.TextTemplateFacade;
import com.realfinance.sofa.system.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@Tag(name = "文本模板")
@RequestMapping("/system/texttemp")
public class TextTemplateController {

    private static final Logger log = LoggerFactory.getLogger(TextTemplateController.class);

    public static final String MENU_CODE_ROOT = "texttemp";
    public static final String MENU_CODE_VIEW = MENU_CODE_ROOT + ":view";
    public static final String MENU_CODE_SAVE = MENU_CODE_ROOT + ":save";
    public static final String MENU_CODE_DELETE = MENU_CODE_ROOT + ":delete";

    @SofaReference(interfaceType = TextTemplateFacade.class, uniqueId = "${service.rf-system.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private TextTemplateFacade textTemplateFacade;
    @SofaReference(interfaceType = SystemQueryFacade.class, uniqueId = "${service.rf-system.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private SystemQueryFacade systemQueryFacade;

    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private DepartmentMapper departmentMapper;
    @Resource
    private TextTemplateMapper textTemplateMapper;

    @GetMapping("list")
    @Operation(summary = "查询模板列表")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.system.TextTemplateController).MENU_CODE_VIEW)")
    public ResponseEntity<Page<TextTemplateVo>> list(TextTemplateQueryCriteria queryCriteria,
                                                     Pageable pageable) {
        Page<TextTemplateDto> result = textTemplateFacade.list(queryCriteria, pageable);
        return ResponseEntity.ok(result.map(textTemplateMapper::toVo));
    }

    @GetMapping("get")
    @Operation(summary = "查询模板详情")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.system.TextTemplateController).MENU_CODE_VIEW)")
    public ResponseEntity<TextTemplateVo> get(@Parameter(description = "ID") @RequestParam Integer id) {
        TextTemplateDetailsDto result = textTemplateFacade.getDetailsById(id);
        return ResponseEntity.ok(textTemplateMapper.toVo(result));
    }

    @GetMapping("queryuserrefer")
    @Operation(summary = "查询用户参照", description = "非系统租户只返回当前登录的法人下的用户")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<UserVo>> queryUserRefer(UserQueryCriteriaRequest queryCriteria,
                                                       Pageable pageable) {
        Page<UserDto> result = systemQueryFacade.queryUserRefer(queryCriteria,pageable);
        return ResponseEntity.ok(result.map(userMapper::userDto2UserVo));
    }

    @GetMapping("queryrolerefer")
    @Operation(summary = "查询角色参照", description = "非系统租户只返回当前登录的法人下的角色")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<RoleVo>> queryRoleRefer(RoleQueryCriteriaRequest queryCriteria,
                                                       Pageable pageable) {
        Page<RoleSmallDto> result = systemQueryFacade.queryRoleRefer(queryCriteria,pageable);
        return ResponseEntity.ok(result.map(roleMapper::roleSmallDto2RoleVo));
    }

    @GetMapping("querydepartmentrefer")
    @Operation(summary = "查询部门参照", description = "返回部门树，非系统租户只能返回当前登录的法人下的部门树")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.RequirementController).MENU_CODE_SAVE)")
    public ResponseEntity<List<DepartmentTreeVo>> queryDepartmentRefer() {
        List<DepartmentSmallTreeDto> result = systemQueryFacade.queryDepartmentRefer();
        return ResponseEntity.ok(result.stream().map(departmentMapper::departmentSmallTreeDto2DepartmentTreeVo).collect(Collectors.toList()));
    }

    @PostMapping("save")
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.system.TextTemplateController).MENU_CODE_SAVE)")
    public ResponseEntity<Integer> save(@Validated @RequestBody TextTemplateVo vo) {
        Integer id = textTemplateFacade.save(textTemplateMapper.toSaveDto(vo));
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("delete")
    @Operation(summary = "删除")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.system.TextTemplateController).MENU_CODE_DELETE)")
    public ResponseEntity<?> delete(@Parameter(description = "模板ID") @RequestParam Set<Integer> id) {
        textTemplateFacade.delete(id);
        return ResponseEntity.ok().build();
    }
}
