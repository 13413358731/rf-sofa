package com.realfinance.sofa.system.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

public class DepartmentSaveDto implements Serializable {
    private Integer id;

    private Integer parent;

    @NotNull
    private String tenant;

    @NotBlank
    @Pattern(regexp = "^((?!.*(\\/)).)*$")
    private String code;

    @NotBlank
    @Pattern(regexp = "^((?!.*(\\/)).)*$")
    private String name;

    @Min(1)
    @NotNull
    private Integer sort;

    @NotNull
    private String type;

    private String category;

    @NotNull
    private Boolean enabled;

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

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "DepartmentSaveDto{" +
                "id=" + id +
                ", parent=" + parent +
                ", tenant='" + tenant + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", sort=" + sort +
                ", type='" + type + '\'' +
                ", category='" + category + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
