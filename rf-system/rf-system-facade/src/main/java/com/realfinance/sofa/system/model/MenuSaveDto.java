package com.realfinance.sofa.system.model;

import org.springframework.lang.NonNull;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

public class MenuSaveDto implements Serializable {
    private Integer id;
    private Integer parent;
    @NotBlank
    @Pattern(regexp = "^((?!.*(\\/)).)*$")
    private String code;
    @NotBlank
    @Pattern(regexp = "^((?!.*(\\/)).)*$")
    private String name;
    @NonNull
    private String type;
    @NotNull
    @Min(1)
    private Integer sort;
    @NotNull
    private Boolean hidden;
    private String icon;
    private String component;
    private String componentName;
    private String url;
    @NotNull
    private Boolean keepAlive;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
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

    @NonNull
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

    @Override
    public String toString() {
        return "MenuSaveDto{" +
                "id=" + id +
                ", parent=" + parent +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", sort=" + sort +
                ", hidden=" + hidden +
                ", icon='" + icon + '\'' +
                ", component='" + component + '\'' +
                ", componentName='" + componentName + '\'' +
                ", url='" + url + '\'' +
                ", keepAlive=" + keepAlive +
                '}';
    }
}
