package com.realfinance.sofa.cg.controller.system;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.cg.model.system.*;
import com.realfinance.sofa.cg.service.mapstruct.MenuMapper;
import com.realfinance.sofa.cg.service.mapstruct.RoleMapper;
import com.realfinance.sofa.cg.service.mapstruct.TenantMapper;
import com.realfinance.sofa.cg.service.mapstruct.UserMapper;
import com.realfinance.sofa.system.facade.RoleMngFacade;
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
import java.util.List;
import java.util.Set;

@RestController
@Tag(name = "角色管理")
@RequestMapping("/system/rolemng")
public class RoleMngController {

    private static final Logger log = LoggerFactory.getLogger(RoleMngController.class);

    public static final String MENU_CODE_ROOT = "rolemng";
    public static final String MENU_CODE_VIEW = MENU_CODE_ROOT + ":view";
    public static final String MENU_CODE_SAVE = MENU_CODE_ROOT + ":save";
    public static final String MENU_CODE_DELETE = MENU_CODE_ROOT + ":delete";
    public static final String MENU_CODE_USERS = MENU_CODE_ROOT + ":users"; //关联用户
    public static final String MENU_CODE_MENUS = MENU_CODE_ROOT + ":menus";// 授权菜单

    @SofaReference(interfaceType = RoleMngFacade.class, uniqueId = "${service.rf-system.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private RoleMngFacade roleMngFacade;

    @SofaReference(interfaceType = SystemQueryFacade.class, uniqueId = "${service.rf-system.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private SystemQueryFacade systemQueryFacade;

    @Resource
    private UserMapper userMapper;
    @Resource
    private TenantMapper tenantMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private MenuMapper menuMapper;

    @GetMapping("list")
    @Operation(summary = "查询角色列表")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.system.RoleMngController).MENU_CODE_VIEW)")
    public ResponseEntity<Page<RoleVo>> list(RoleQueryCriteriaRequest queryCriteria,
                                             Pageable pageable) {
        Page<RoleDto> result = roleMngFacade.list(queryCriteria, pageable);
        return ResponseEntity.ok(result.map(roleMapper::roleDto2RoleVo));
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
    @Operation(summary = "保存角色")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.system.RoleMngController).MENU_CODE_SAVE)")
    public ResponseEntity<Integer> save(@RequestBody RoleSaveRequest roleSaveRequest) {  //多了@Validated
        Integer id = roleMngFacade.save(roleMapper.roleSaveRequest2RoleSaveDto(roleSaveRequest));
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("delete")
    @Operation(summary = "删除角色")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.system.RoleMngController).MENU_CODE_DELETE)")
    public ResponseEntity<?> delete(@Parameter(description = "角色ID") @RequestParam Set<Integer> id) {
        roleMngFacade.delete(id);
        return ResponseEntity.ok().build();
    }


    @GetMapping("listusersbyid")
    @Operation(summary = "查询角色下的用户")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.system.RoleMngController).MENU_CODE_SAVE)")
    public ResponseEntity<Page<UserVo>> listUsersById(@Parameter(description = "角色ID") @RequestParam Integer id,
                                                      @Parameter(description = "搜索过滤") @RequestParam(required = false) String filter,
                                                      Pageable pageable) {
        Page<UserDto> result = roleMngFacade.listUsersById(id, filter, pageable);
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
    @Operation(summary = "角色新增用户关联")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.system.RoleMngController).MENU_CODE_USERS)")
    public ResponseEntity<?> addUsers(@Parameter(description = "角色ID") @RequestParam Integer id,
                                      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "用户ID集合") @RequestBody Set<Integer> userId) {
        roleMngFacade.addUsers(id,userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("removeusers")
    @Operation(summary = "角色删除用户关联")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.system.RoleMngController).MENU_CODE_USERS)")
    public ResponseEntity<?> removeUsers(@Parameter(description = "角色ID") @RequestParam Integer id,
                                         @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "用户ID集合") @RequestBody Set<Integer> userId) {
        roleMngFacade.removeUsers(id,userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("listmenus")
    @Operation(summary = "查询所有菜单")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.system.RoleMngController).MENU_CODE_MENUS)")
    public ResponseEntity<List<MenuTreeVo>> listMenus() {
        List<MenuDto> allMenus = systemQueryFacade.queryMenus();
        List<MenuTreeVo> root = menuMapper.buildTree(allMenus);
        return ResponseEntity.ok(root);
    }

    @GetMapping("listmenuidsbyid")
    @Operation(summary = "查询角色关联的菜单ID集合")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.system.RoleMngController).MENU_CODE_MENUS)")
    public ResponseEntity<Set<Integer>> listMenuIdsById(@Parameter(description = "角色ID") @RequestParam Integer id) {
        Set<Integer> result = roleMngFacade.listMenuIdsById(id);
        return ResponseEntity.ok(result);
    }

    @PostMapping("updatemenus")
    @Operation(summary = "更新角色菜单关联")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.system.RoleMngController).MENU_CODE_MENUS)")
    public ResponseEntity<?> updateMenus(@Parameter(description = "角色ID") @RequestParam Integer id,
                                         @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "菜单IDj集合") @RequestBody Set<Integer> menuId) {
        // TODO: 2020/11/3 如果需要控制菜单分配权限 先查出当前用户无权操作的菜单ID 然后再把这些ID放回menuId里，但是暂定能分配菜单功能则能分配所有菜单 即超级管理员
        roleMngFacade.updateMenus(id,menuId);
        return ResponseEntity.ok().build();
    }


    @GetMapping("listmenudatarule")
    @Operation(summary = "查询菜单数据规则列表")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.system.RoleMngController).MENU_CODE_MENUS)")
    public ResponseEntity<List<MenuDataRuleDto>> listMenuDataRule(@Parameter(description = "菜单ID") @RequestParam Integer menuId) {
        List<MenuDataRuleDto> result = roleMngFacade.listMenuDataRule(menuId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("listmenudataruleidsbyidandmenuid")
    @Operation(summary = "查询角色在的菜单下关联的数据规则ID集合")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.system.RoleMngController).MENU_CODE_MENUS)")
    public ResponseEntity<Set<Integer>> listMenuDataRuleIdsByIdAndMenuId(@Parameter(description = "角色ID") @RequestParam Integer id,
                                                                         @Parameter(description = "菜单ID") @RequestParam Integer menuId) {
        Set<Integer> result = roleMngFacade.listMenuDataRuleIdsByIdAndMenuId(id, menuId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("updatemenudatarules")
    @Operation(summary = "更新角色在菜单下数据规则关联")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.system.RoleMngController).MENU_CODE_MENUS)")
    public ResponseEntity<?> updateMenuDataRules(@Parameter(description = "角色ID") @RequestParam Integer id,
                                                 @Parameter(description = "菜单ID") @RequestParam Integer menuId,
                                                 @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "菜单数据规则ID集合") @RequestBody Set<Integer> menuDataRuleId) {
        roleMngFacade.updateMenuDataRules(id,menuId,menuDataRuleId);
        return ResponseEntity.ok().build();
    }
}
