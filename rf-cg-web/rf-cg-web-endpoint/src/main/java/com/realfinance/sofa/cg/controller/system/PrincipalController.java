package com.realfinance.sofa.cg.controller.system;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.cg.security.AuthInfo;
import com.realfinance.sofa.system.facade.SystemQueryFacade;
import com.realfinance.sofa.system.facade.UserMngFacade;
import com.realfinance.sofa.system.model.MenuDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.DigestUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@Tag(name = "当前用户接口")
@RequestMapping(value = {"/system/principal", "/principal"})
public class PrincipalController {

    @SofaReference(interfaceType = SystemQueryFacade.class, uniqueId = "${service.rf-system.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private SystemQueryFacade systemQueryFacade;
    @SofaReference(interfaceType = UserMngFacade.class, uniqueId = "${service.rf-system.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private UserMngFacade userMngFacade;

    @GetMapping("userinfo")
    @Operation(summary = "查询用户信息")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> userInfo(Authentication authentication) {
        return ResponseEntity.ok(authentication.getPrincipal());
    }

    @GetMapping("authorities")
    @Operation(summary = "查询用户权限信息", description = "返回菜单和按钮的编码集合")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<String>> authorities(Authentication authentication) {
        AuthInfo principal = (AuthInfo) authentication.getPrincipal();
        return ResponseEntity.ok(principal.getMenuCodes());
    }

    @GetMapping("roles")
    @Operation(summary = "查询用户拥有角色信息", description = "返回角色编码集合")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<String>> roles(Authentication authentication) {
        AuthInfo principal = (AuthInfo) authentication.getPrincipal();
        return ResponseEntity.ok(principal.getRoleCodes());
    }

    @GetMapping("menus")
    @Operation(summary = "查询用户拥有的菜单路由")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> menus(Authentication authentication) {
        Set<String> authoritySet = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
        List<MenuDto> allMenus = systemQueryFacade.queryMenus();
        JSONArray result = new JSONArray();
        getPermissionJsonArray(result,allMenus.stream().filter(e -> authoritySet.contains(e.getCode())).collect(Collectors.toList()), null);
        return ResponseEntity.ok(result);
    }

    @PostMapping("resetpassword")
    @Operation(summary = "修改密码")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> resetPassword(@Validated @RequestBody ResetPasswordRequest resetPasswordRequest,
                                           Authentication authentication) {
        userMngFacade.resetPassword(Integer.parseInt(authentication.getName()),resetPasswordRequest.getPassword(),
                resetPasswordRequest.getNewPassword(),resetPasswordRequest.getNewPassword2());
        return ResponseEntity.ok().build();
    }


    /**
     *  获取菜单JSON数组
     *  TODO 直接copy jeecg的逻辑 有时间要重写
     * @param jsonArray
     * @param metaList
     * @param parentJson
     */
    private void getPermissionJsonArray(JSONArray jsonArray, List<MenuDto> metaList, JSONObject parentJson) {
        for (MenuDto permission : metaList) {
            Integer tempPid = permission.getParent() == null ? null : permission.getParent().getId();
            if (permission.getType().equals("BUTTON")) {
                continue;
            }
            if (parentJson == null && tempPid == null) {
                JSONObject json = getPermissionJsonObject(permission,parentJson);
                jsonArray.add(json);
                if (!(permission.getLeafCount() == 0)) {
                    getPermissionJsonArray(jsonArray, metaList, json);
                }
            } else if (parentJson != null && tempPid != null && tempPid.equals(parentJson.getInteger("id"))) {
                JSONObject json = getPermissionJsonObject(permission,parentJson);
                // 类型( 0：一级菜单 1：子菜单 2：按钮 )
                if (permission.getType().equals("BUTTON")) {
                    JSONObject metaJson = parentJson.getJSONObject("meta");
                    if (metaJson.containsKey("permissionList")) {
                        metaJson.getJSONArray("permissionList").add(json);
                    } else {
                        JSONArray permissionList = new JSONArray();
                        permissionList.add(json);
                        metaJson.put("permissionList", permissionList);
                    }
                    // 类型( 0：一级菜单 1：子菜单 2：按钮 )
                } else if (permission.getType().equals("SUB") || permission.getType().equals("FIRST_LEVEL")) {
                    if (parentJson.containsKey("children")) {
                        parentJson.getJSONArray("children").add(json);
                    } else {
                        JSONArray children = new JSONArray();
                        children.add(json);
                        parentJson.put("children", children);
                    }

                    if (!(permission.getLeafCount() == 0)) {
                        getPermissionJsonArray(jsonArray, metaList, json);
                    }
                }
            }

        }
    }

    /**
     * 根据菜单配置生成路由json
     * @param permission
     * @return
     */
    private JSONObject getPermissionJsonObject(MenuDto permission, JSONObject parentJson) {
        JSONObject json = new JSONObject();
        // 类型(0：一级菜单 1：子菜单 2：按钮)
        if (permission.getType().equals("BUTTON")) {
            return null;
        } else if (permission.getType().equals("FIRST_LEVEL") || permission.getType().equals("SUB")) {
            // -----------前端说要在非一级和子级的数据中加视口-------------
            if (!permission.getType().equals("FIRST_LEVEL") && permission.getLeafCount() > 0) {
                json.put("needViewPort",true);
            }
            // -----------------------------------------------------
            json.put("id", permission.getId());
            if (isWWWHttpUrl(permission.getUrl())) {
                json.put("path", DigestUtils.md5DigestAsHex(permission.getUrl().getBytes(StandardCharsets.UTF_8)));
            } else {
                String url = permission.getUrl();
                // TODO: 2020/11/6 处理一下URL 剪切父路径
                // ---剪切url开始
                if (url != null) {
                    if (!permission.getType().equals("FIRST_LEVEL")) { // 非一级菜单
                        String path = parentJson.getString("path");
                        if (url.startsWith(path) && url.length() - path.length() > 1) {
                            url = url.substring(path.length() + 1);
                        }
                    }
                }
                // ---剪切url结束
                json.put("path", url);
            }
            // 重要规则：路由name (通过URL生成路由name,路由name供前端开发，页面跳转使用)
            if (StringUtils.isNotEmpty(permission.getComponentName())) {
                json.put("name", permission.getComponentName());
            } else {
                json.put("name", urlToRouteName(permission.getUrl()));
            }
            // 是否隐藏路由，默认都是显示的
            if (permission.getHidden()) {
                json.put("hidden", true);
            }
            json.put("component", permission.getComponent());
            JSONObject meta = new JSONObject();
            // 由用户设置是否缓存页面 用布尔值
            meta.put("keepAlive", permission.getKeepAlive());

            meta.put("title", permission.getName());

            if (StringUtils.isNotEmpty(permission.getIcon())) {
                meta.put("icon", permission.getIcon());
            }
            if (isWWWHttpUrl(permission.getUrl())) {
                meta.put("url", permission.getUrl());
            }
            json.put("meta", meta);
        }

        return json;
    }

    /**
     * 判断是否外网URL 例如： http://localhost:8080/jeecg-boot/swagger-ui.html#/ 支持特殊格式： {{
     * window._CONFIG['domianURL'] }}/druid/ {{ JS代码片段 }}，前台解析会自动执行JS代码片段
     *
     * @return
     */
    private boolean isWWWHttpUrl(String url) {
        if (url != null && (url.startsWith("http://") || url.startsWith("https://") || url.startsWith("{{"))) {
            return true;
        }
        return false;
    }


    /**
     * 通过URL生成路由name（去掉URL前缀斜杠，替换内容中的斜杠‘/’为-） 举例： URL = /isystem/role RouteName =
     * isystem-role
     *
     * @return
     */
    private String urlToRouteName(String url) {
        if (StringUtils.isNotEmpty(url)) {
            if (url.startsWith("/")) {
                url = url.substring(1);
            }
            url = url.replace("/", "-");

            // 特殊标记
            url = url.replace(":", "@");
            return url;
        } else {
            return null;
        }
    }


    public static class ResetPasswordRequest {

        @NotBlank
        @Size(min = 6, max = 20)
        @Schema(description = "原密码", type = "string")
        private String password;

        @NotBlank
        @Size(min = 6, max = 20)
        @Schema(description = "新密码", type = "string")
        private String newPassword;

        @NotBlank
        @Size(min = 6, max = 20)
        @Schema(description = "确认新密码", type = "string")
        private String newPassword2;

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getNewPassword() {
            return newPassword;
        }

        public void setNewPassword(String newPassword) {
            this.newPassword = newPassword;
        }

        public String getNewPassword2() {
            return newPassword2;
        }

        public void setNewPassword2(String newPassword2) {
            this.newPassword2 = newPassword2;
        }
    }
}
