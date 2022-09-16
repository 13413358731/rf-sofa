package com.realfinance.sofa.system.model;

import java.util.Set;

public class TextTemplateQueryCriteria {

    private Set<Integer> roleScopeIn;
    private Set<Integer> departmentScopeIn;
    private Set<Integer> userScopeIn;
    private Set<Integer> orRoleScopeIn;
    private Set<Integer> orDepartmentScopeIn;
    private Set<Integer> orUserScopeIn;
    private String type;
    private String nameLike;
    private String tenantId;
    private Integer createdUserId;
    private Integer orCreatedUserId;

    public Set<Integer> getRoleScopeIn() {
        return roleScopeIn;
    }

    public void setRoleScopeIn(Set<Integer> roleScopeIn) {
        this.roleScopeIn = roleScopeIn;
    }

    public Set<Integer> getDepartmentScopeIn() {
        return departmentScopeIn;
    }

    public void setDepartmentScopeIn(Set<Integer> departmentScopeIn) {
        this.departmentScopeIn = departmentScopeIn;
    }

    public Set<Integer> getUserScopeIn() {
        return userScopeIn;
    }

    public void setUserScopeIn(Set<Integer> userScopeIn) {
        this.userScopeIn = userScopeIn;
    }

    public Set<Integer> getOrRoleScopeIn() {
        return orRoleScopeIn;
    }

    public void setOrRoleScopeIn(Set<Integer> orRoleScopeIn) {
        this.orRoleScopeIn = orRoleScopeIn;
    }

    public Set<Integer> getOrDepartmentScopeIn() {
        return orDepartmentScopeIn;
    }

    public void setOrDepartmentScopeIn(Set<Integer> orDepartmentScopeIn) {
        this.orDepartmentScopeIn = orDepartmentScopeIn;
    }

    public Set<Integer> getOrUserScopeIn() {
        return orUserScopeIn;
    }

    public void setOrUserScopeIn(Set<Integer> orUserScopeIn) {
        this.orUserScopeIn = orUserScopeIn;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNameLike() {
        return nameLike;
    }

    public void setNameLike(String nameLike) {
        this.nameLike = nameLike;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Integer getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(Integer createdUserId) {
        this.createdUserId = createdUserId;
    }

    public Integer getOrCreatedUserId() {
        return orCreatedUserId;
    }

    public void setOrCreatedUserId(Integer orCreatedUserId) {
        this.orCreatedUserId = orCreatedUserId;
    }
}
