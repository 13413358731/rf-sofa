package com.realfinance.sofa.cg.model.system;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.realfinance.sofa.cg.model.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "菜单对象")
public class MenuVo extends BaseVo {
    @Schema(description = "ID")
    private Integer id;
    @Schema(description = "上级菜单")
    private MenuVo parent;
    @Schema(description = "菜单编码")
    private String code;
    @Schema(description = "菜单编码路径")
    private String codePath;
    @Schema(description = "菜单名称")
    private String name;
    @Schema(description = "菜单名称路径")
    private String namePath;
    @Schema(description = "菜单类型：FIRST_LEVEL（一级菜单）、SUB（子菜单）、BUTTON（权限|按钮）")
    private String type;
    @Schema(description = "排序")
    private Integer sort;
    @Schema(description = "子叶数")
    private Integer leafCount;
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
    @Schema(description = "keepAlive")
    private Boolean keepAlive;
    @Schema(description = "是否含有菜单规则")
    private Boolean hasMenuDataRule;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MenuVo getParent() {
        return parent;
    }

    public void setParent(MenuVo parent) {
        this.parent = parent;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodePath() {
        return codePath;
    }

    public void setCodePath(String codePath) {
        this.codePath = codePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNamePath() {
        return namePath;
    }

    public void setNamePath(String namePath) {
        this.namePath = namePath;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getLeafCount() {
        return leafCount;
    }

    public void setLeafCount(Integer leafCount) {
        this.leafCount = leafCount;
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

    public Boolean getHasMenuDataRule() {
        return hasMenuDataRule;
    }

    public void setHasMenuDataRule(Boolean hasMenuDataRule) {
        this.hasMenuDataRule = hasMenuDataRule;
    }
}
