package com.realfinance.sofa.system.model;

import java.util.Collection;

public class UserQueryCriteria {

    /**
     * 用户ID集合
     */
    private Collection<Integer> idIn;

    /**
     * 部门ID集合
     */
    private Collection<Integer> departmentIdIn;

    private Collection<String> departmentCodeIn;
    /**
     * 角色ID集合
     */
    private Collection<Integer> roleIdIn;

    private Collection<String> roleCodeIn;

    /**
     * 用户名模糊
     */
    private String usernameLike;
    /**
     * 真实姓名模糊
     */
    private String realnameLike;
    /**
     * 用户名或姓名模糊查询
     */
    private String usernameLikeOrRealnameLike;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 是否可用
     */
    private Boolean enabled;
    /**
     * 分类
     */
    private String classification;
    /**
     * 租户ID
     */
    private String tenantId;
    /**
     * 部门ID
     */
    private Integer departmentId;

    /**
     * 部门编码路径包含
     */
    private String departmentCodePathContains;

    public Collection<Integer> getIdIn() {
        return idIn;
    }

    public void setIdIn(Collection<Integer> idIn) {
        this.idIn = idIn;
    }

    public Collection<Integer> getDepartmentIdIn() {
        return departmentIdIn;
    }

    public void setDepartmentIdIn(Collection<Integer> departmentIdIn) {
        this.departmentIdIn = departmentIdIn;
    }

    public Collection<Integer> getRoleIdIn() {
        return roleIdIn;
    }

    public void setRoleIdIn(Collection<Integer> roleIdIn) {
        this.roleIdIn = roleIdIn;
    }

    public String getUsernameLike() {
        return usernameLike;
    }

    public void setUsernameLike(String usernameLike) {
        this.usernameLike = usernameLike;
    }

    public String getRealnameLike() {
        return realnameLike;
    }

    public void setRealnameLike(String realnameLike) {
        this.realnameLike = realnameLike;
    }

    public String getUsernameLikeOrRealnameLike() {
        return usernameLikeOrRealnameLike;
    }

    public void setUsernameLikeOrRealnameLike(String usernameLikeOrRealnameLike) {
        this.usernameLikeOrRealnameLike = usernameLikeOrRealnameLike;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentCodePathContains() {
        return departmentCodePathContains;
    }

    public void setDepartmentCodePathContains(String departmentCodePathContains) {
        this.departmentCodePathContains = departmentCodePathContains;
    }

    public Collection<String> getDepartmentCodeIn() {
        return departmentCodeIn;
    }

    public void setDepartmentCodeIn(Collection<String> departmentCodeIn) {
        this.departmentCodeIn = departmentCodeIn;
    }

    public Collection<String> getRoleCodeIn() {
        return roleCodeIn;
    }

    public void setRoleCodeIn(Collection<String> roleCodeIn) {
        this.roleCodeIn = roleCodeIn;
    }
}
