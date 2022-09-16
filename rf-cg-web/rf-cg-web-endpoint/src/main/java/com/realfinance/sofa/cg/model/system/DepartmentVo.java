package com.realfinance.sofa.cg.model.system;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.realfinance.sofa.cg.model.BaseVo;
import com.realfinance.sofa.common.model.IdentityObject;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "部门对象")
public class DepartmentVo extends BaseVo implements IdentityObject<Integer> {
    @Schema(description = "ID")
    protected Integer id;
    @Schema(description = "法人")
    protected TenantVo tenant;
    @Schema(description = "上级部门")
    protected DepartmentVo parent;
    @Schema(description = "部门编码")
    protected String code;
    @Schema(description = "部门编码路径")
    protected String codePath;
    @Schema(description = "部门名称")
    protected String name;
    @Schema(description = "部门名称路径")
    protected String namePath;
    @Schema(description = "部门类别")
    protected String category;
    @Schema(description = "子叶数")
    protected Integer leafCount;
    @Schema(description = "排序")
    protected Integer sort;
    @Schema(description = "部门类型：FIRST_LEVEL（一级部门）、SUB（子部门）")
    protected String type;
    @Schema(description = "是否启用")
    protected Boolean enabled;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TenantVo getTenant() {
        return tenant;
    }

    public void setTenant(TenantVo tenant) {
        this.tenant = tenant;
    }

    public DepartmentVo getParent() {
        return parent;
    }

    public void setParent(DepartmentVo parent) {
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getLeafCount() {
        return leafCount;
    }

    public void setLeafCount(Integer leafCount) {
        this.leafCount = leafCount;
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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
