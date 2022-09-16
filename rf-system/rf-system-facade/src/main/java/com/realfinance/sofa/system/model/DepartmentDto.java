package com.realfinance.sofa.system.model;

import java.io.Serializable;
import java.util.Objects;

public class DepartmentDto extends BaseDto implements Serializable {

    private Integer id;

    protected TenantSmallDto tenant;

    private DepartmentSmallDto parent;

    private String code;

    private String codePath;

    private String name;

    private String namePath;

    private String category;

    private Integer leafCount;

    private Integer sort;

    private String type;

    private Boolean enabled;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepartmentDto that = (DepartmentDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TenantSmallDto getTenant() {
        return tenant;
    }

    public void setTenant(TenantSmallDto tenant) {
        this.tenant = tenant;
    }

    public DepartmentSmallDto getParent() {
        return parent;
    }

    public void setParent(DepartmentSmallDto parent) {
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
