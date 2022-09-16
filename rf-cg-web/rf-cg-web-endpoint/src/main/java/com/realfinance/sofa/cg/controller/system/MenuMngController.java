package com.realfinance.sofa.cg.controller.system;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.cg.model.system.MenuDataRuleSaveRequest;
import com.realfinance.sofa.cg.model.system.MenuSaveRequest;
import com.realfinance.sofa.cg.model.system.MenuTreeVo;
import com.realfinance.sofa.cg.model.system.MenuVo;
import com.realfinance.sofa.cg.service.mapstruct.MenuMapper;
import com.realfinance.sofa.system.facade.MenuMngFacade;
import com.realfinance.sofa.system.facade.SystemQueryFacade;
import com.realfinance.sofa.system.model.MenuDataRuleDto;
import com.realfinance.sofa.system.model.MenuDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@Tag(name = "菜单管理")
@RequestMapping("/system/menumng")
public class MenuMngController {

    private static final Logger log = LoggerFactory.getLogger(MenuMngController.class);

    public static final String MENU_CODE_ROOT = "menumng";
    public static final String MENU_CODE_VIEW = MENU_CODE_ROOT + ":view";
    public static final String MENU_CODE_SAVE = MENU_CODE_ROOT + ":save";
    public static final String MENU_CODE_DELETE = MENU_CODE_ROOT + ":delete";
    public static final String MENU_CODE_MENUDATARULES = MENU_CODE_ROOT + ":menudatarules"; // 数据规则

    @SofaReference(interfaceType = MenuMngFacade.class, uniqueId = "${service.rf-system.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private MenuMngFacade menuMngFacade;
    @SofaReference(interfaceType = SystemQueryFacade.class, uniqueId = "${service.rf-system.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private SystemQueryFacade systemQueryFacade;

    @Resource
    private MenuMapper menuMapper;

    @GetMapping("listfirstlevel")
    @Operation(summary = "查询第一级菜单")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.system.MenuMngController).MENU_CODE_VIEW)")
    public ResponseEntity<List<MenuVo>> listFirstLevel() {
        List<MenuDto> result = menuMngFacade.listFirstLevel();
        return ResponseEntity.ok(result.stream().map(menuMapper::menuDto2MenuVo).collect(Collectors.toList()));
    }

    @GetMapping("listbyparentid")
    @Operation(summary = "根据父节点ID查询菜单列表")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.system.MenuMngController).MENU_CODE_VIEW)")
    public ResponseEntity<List<MenuVo>> listByParentId(@Parameter(description = "上级菜单ID") @RequestParam(required = false) Integer parentId) {
        List<MenuDto> result = menuMngFacade.listByParentId(parentId);
        return ResponseEntity.ok(result.stream().map(menuMapper::menuDto2MenuVo).collect(Collectors.toList()));
    }

    @GetMapping("querymenurefer")
    @Operation(summary = "查询菜单参照")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<MenuTreeVo>> queryTenantRefer() {
        List<MenuDto> allMenus = systemQueryFacade.queryMenus();
        List<MenuTreeVo> root = menuMapper.buildTree(allMenus);
        return ResponseEntity.ok(root);
    }


    @PostMapping("save")
    @Operation(summary = "保存菜单")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.system.MenuMngController).MENU_CODE_SAVE)")
    public ResponseEntity<Integer> save(@Validated @RequestBody MenuSaveRequest menuSaveRequest) {
        Integer id = menuMngFacade.save(menuMapper.menuSaveRequest2MenuSaveDto(menuSaveRequest));
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("delete")
    @Operation(summary = "删除菜单")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.system.MenuMngController).MENU_CODE_DELETE)")
    public ResponseEntity<?> delete(@Parameter(description = "菜单ID") @RequestParam Set<Integer> id) {
        menuMngFacade.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("listmenudatarule")
    @Operation(summary = "查询菜单数据规则列表")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.system.MenuMngController).MENU_CODE_MENUDATARULES)")
    public ResponseEntity<List<MenuDataRuleDto>> listMenuDataRule(@Parameter(description = "菜单ID") @RequestParam Integer id) {
        List<MenuDataRuleDto> result = menuMngFacade.listMenuDataRule(id);
        return ResponseEntity.ok(result);
    }

    @PostMapping("savemenudatarule")
    @Operation(summary = "保存菜单数据规则")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.system.MenuMngController).MENU_CODE_MENUDATARULES)")
    public ResponseEntity<?> saveMenuDataRule(@Validated @RequestBody MenuDataRuleSaveRequest menuDataRuleSaveRequest) {
        Integer id = menuMngFacade.saveMenuDataRule(menuMapper.menuDataRuleSaveRequest2MenuDataRuleSaveDto(menuDataRuleSaveRequest));
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("deletemenudatarule")
    @Operation(summary = "删除菜单数据规则")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.system.MenuMngController).MENU_CODE_MENUDATARULES)")
    public ResponseEntity<?> deleteMenuDataRule(@Parameter(description = "菜单数据规则ID") @RequestParam Set<Integer> menuDataRuleId) {
        menuMngFacade.deleteMenuDataRule(menuDataRuleId);
        return ResponseEntity.ok().build();
    }
}
