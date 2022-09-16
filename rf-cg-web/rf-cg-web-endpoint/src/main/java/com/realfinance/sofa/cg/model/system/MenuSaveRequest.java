package com.realfinance.sofa.cg.model.system;

import com.realfinance.sofa.common.model.ReferenceObject;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.lang.NonNull;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Schema(description = "保存菜单请求对象")
public class MenuSaveRequest {
    @Schema(description = "ID")
    private Integer id;
    @Schema(description = "上级菜单")
    private ReferenceObject<Integer> parent;
    @NotBlank
    @Pattern(regexp = "^((?!.*(\\/)).)*$")
    @Schema(description = "菜单编码")
    private String code;
    @NotBlank
    @Pattern(regexp = "^((?!.*(\\/)).)*$")
    @Schema(description = "菜单名称")
    private String name;
    @NotNull
    @Schema(description = "菜单类型：FIRST_LEVEL（一级菜单）、SUB（子菜单）、BUTTON（权限|按钮）")
    private String type;
    @NotNull
    @Min(1)
    @Schema(description = "排序")
    private Integer sort;
    @NotNull
    @Schema(description = "hidden")
    private Boolean hidden;
    @Schema(description = "icon")
    private String icon;
    @Schema(description = "component")
    private String component;
    @Schema(description = "componentName")
    private String componentName;
    @Schema(description = "url")
    private String url;
    @NotNull
    @Schema(description = "keepAlive")
    private Boolean keepAlive;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ReferenceObject<Integer> getParent() {
        return parent;
    }

    public void setParent(ReferenceObject<Integer> parent) {
        this.parent = parent;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(@NonNull String type) {
        this.type = type;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(Boolean keepAlive) {
        this.keepAlive = keepAlive;
    }
}
