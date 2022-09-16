package com.realfinance.sofa.cg.controller.system;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.cg.model.system.*;
import com.realfinance.sofa.cg.service.mapstruct.RoleGroupMapper;
import com.realfinance.sofa.cg.service.mapstruct.RoleMapper;
import com.realfinance.sofa.cg.service.mapstruct.TenantMapper;
import com.realfinance.sofa.cg.service.mapstruct.UserMapper;
import com.realfinance.sofa.system.facade.RoleGroupMngFacade;
import com.realfinance.sofa.system.facade.SystemQueryFacade;
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
import java.util.Set;

@RestController
@Tag(name = "角色组管理")
@RequestMapping("/system/rolegroupmng")
public class RoleGroupMngController {

    private static final Logger log = LoggerFactory.getLogger(RoleGroupMngController.class);

    public static final String MENU_CODE_ROOT = "rolegroupmng";
    public static final String MENU_CODE_VIEW = MENU_CODE_ROOT + ":view";
    public static final String MENU_CODE_SAVE = MENU_CODE_ROOT + ":save";
    public static final String MENU_CODE_DELETE = MENU_CODE_ROOT + ":delete";
    public static final String MENU_CODE_USERS = MENU_CODE_ROOT + ":users";
    public static final String MENU_CODE_ROLES = MENU_CODE_ROOT + ":roles";

    @SofaReference(interfaceType = RoleGroupMngFacade.class, uniqueId = "${service.rf-system.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private RoleGroupMngFacade roleGroupMngFacade;

    @SofaReference(interfaceType = SystemQueryFacade.class, uniqueId = "${service.rf-system.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private SystemQueryFacade systemQueryFacade;

    @Resource
    private UserMapper userMapper;
    @Resource
    private TenantMapper tenantMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RoleGroupMapper roleGroupMapper;

    @GetMapping("list")
    @Operation(summary = "查询角色组列表")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.system.RoleGroupMngController).MENU_CODE_VIEW)")
    public ResponseEntity<Page<RoleGroupVo>> list(RoleGroupQueryCriteriaRequest queryCriteria, Pageable pageable) {
        Page<RoleGroupDto> result = roleGroupMngFacade.list(queryCriteria, pageable);
        return ResponseEntity.ok(result.map(roleGroupMapper::roleGroupDto2RoleGroupVo));
    }

    @GetMapping("querytenantrefer")
    @Operation(summary = "查询法人参照", description = "非系统租户只返回当前登录的法人")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<TenantVo>> queryTenantRefer(TenantQueryCriteriaRequest queryCriteria,
                                                           Pageable pageable) {
        Page<TenantSmallDto> result = systemQueryFacade.queryTenantRefer(queryCriteria,pageable);
        return ResponseEntity.ok(result.map(tenantMapper::tenantSmallDto2TenantVo));
    }

    @PostMapping("save")
    @Operation(summary = "保存角色组")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.system.RoleGroupMngController).MENU_CODE_SAVE)")
    public ResponseEntity<Integer> save(@Validated @RequestBody RoleGroupSaveRequest roleGroupSaveRequest) {
        Integer id = roleGroupMngFacade.save(roleGroupMapper.roleGroupSaveRequest2RoleGroupSaveDto(roleGroupSaveRequest));
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("delete")
    @Operation(summary = "删除角色组")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.system.RoleGroupMngController).MENU_CODE_DELETE)")
    public ResponseEntity<?> delete(@Parameter(description = "角色组ID") @RequestParam Set<Integer> id) {
        roleGroupMngFacade.delete(id);
        return ResponseEntity.ok().build();
    }


    @GetMapping("listusersbyid")
    @Operation(summary = "查询角色组下的用户")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.system.RoleGroupMngController).MENU_CODE_USERS)")
    public ResponseEntity<Page<UserVo>> listUsersById(@Parameter(description = "角色组ID") @RequestParam Integer id,
                                                      @Parameter(description = "搜索过滤") @RequestParam(required = false) String filter,
                                                      Pageable pageable) {
        Page<UserDto> result = roleGroupMngFacade.listUsersById(id, filter, pageable);
        return ResponseEntity.ok(result.map(userMapper::userDto2UserVo));
    }

    @GetMapping("queryuserrefer")
    @Operation(summary = "查询用户参照", description = "非系统租户只返回当前登录的法人下的用户")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<UserVo>> queryUserRefer(UserQueryCriteriaRequest queryCriteria,
                                                       Pageable pageable) {
        Page<UserDto> result = systemQueryFacade.queryUserRefer(queryCriteria,pageable);
        return ResponseEntity.ok(result.map(userMapper::userDto2UserVo));
    }

    @PostMapping("addusers")
    @Operation(summary = "角色组新增用户关联")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.system.RoleGroupMngController).MENU_CODE_USERS)")
    public ResponseEntity<?> addUsers(@Parameter(description = "角色组ID") @RequestParam Integer id,
                                      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "用户ID集合") @RequestBody Set<Integer> userId) {
        roleGroupMngFacade.addUsers(id,userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("removeusers")
    @Operation(summary = "角色组删除用户关联")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.system.RoleGroupMngController).MENU_CODE_USERS)")
    public ResponseEntity<?> removeUsers(@Parameter(description = "角色组ID") @RequestParam Integer id,
                                         @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "用户ID集合") @RequestBody Set<Integer> userId) {
        roleGroupMngFacade.removeUsers(id,userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("listrolesbyid")
    @Operation(summary = "查询角色组下的角色")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.system.RoleGroupMngController).MENU_CODE_ROLES)")
    public ResponseEntity<Page<RoleVo>> listRolesById(@Parameter(description = "角色组ID") @RequestParam Integer id,
                                                      @Parameter(description = "搜索过滤") @RequestParam(required = false) String filter,
                                                      Pageable pageable) {
        Page<RoleDto> result = roleGroupMngFacade.listRolesById(id, filter, pageable);
        return ResponseEntity.ok(result.map(roleMapper::roleDto2RoleVo));
    }

    @GetMapping("queryrolerefer")
    @Operation(summary = "查询角色参照", description = "非系统租户只返回当前登录的法人下的角色")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<RoleVo>> queryRoleRefer(RoleQueryCriteriaRequest queryCriteria,
                                                       Pageable pageable) {
        Page<RoleSmallDto> result = systemQueryFacade.queryRoleRefer(queryCriteria,pageable);
        return ResponseEntity.ok(result.map(roleMapper::roleSmallDto2RoleVo));
    }

    @PostMapping("addroles")
    @Operation(summary = "角色组新增角色关联")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.system.RoleGroupMngController).MENU_CODE_ROLES)")
    public ResponseEntity<?> addRoles(@Parameter(description = "角色组ID") @RequestParam Integer id,
                                      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "角色ID集合") @RequestBody Set<Integer> userId) {
        roleGroupMngFacade.addRoles(id,userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("removeroles")
    @Operation(summary = "角色组删除角色关联")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.system.RoleGroupMngController).MENU_CODE_ROLES)")
    public ResponseEntity<?> removeRoles(@Parameter(description = "角色组ID") @RequestParam Integer id,
                                         @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "角色ID集合") @RequestBody Set<Integer> roleId) {
        roleGroupMngFacade.removeRoles(id,roleId);
        return ResponseEntity.ok().build();
    }
}
