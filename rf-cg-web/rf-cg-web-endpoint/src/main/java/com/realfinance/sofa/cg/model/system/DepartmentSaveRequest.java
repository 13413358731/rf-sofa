package com.realfinance.sofa.cg.model.system;

import com.realfinance.sofa.common.model.ReferenceObject;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Schema(description = "保存部门请求对象")
public class DepartmentSaveRequest {
    @Schema(description = "ID")
    private Integer id;
    @Schema(description = "上级部门")
    private ReferenceObject<Integer> parent;
    @NotNull
    @Schema(description = "法人")
    private ReferenceObject<String> tenant;
    @NotBlank
    @Pattern(regexp = "^((?!.*(\\/)).)*$")
    @Schema(description = "部门编码")
    private String code;
    @NotBlank
    @Pattern(regexp = "^((?!.*(\\/)).)*$")
    @Schema(description = "部门名称")
    private String name;
    @Min(1)
    @NotNull
    @Schema(description = "排序")
    private Integer sort;
    @NotNull
    @Schema(description = "部门类型：FIRST_LEVEL（一级部门）、SUB（子部门）")
    private String type;
    @Schema(description = "部门类别")
    private String category;
    @NotNull
    @Schema(description = "是否启用")
    private Boolean enabled;

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

    public ReferenceObject<String> getTenant() {
        return tenant;
    }

    public void setTenant(ReferenceObject<String> tenant) {
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
}
