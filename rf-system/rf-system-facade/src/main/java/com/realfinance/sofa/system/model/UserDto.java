package com.realfinance.sofa.system.model;

public class UserDto extends BaseDto {

    protected Integer id;
    protected String username;
    protected String realname;
    protected String email;
    protected String mobile;
    protected Boolean enabled;
    protected String classification;

    protected TenantSmallDto tenant;

    protected DepartmentSmallDto department;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
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

    public TenantSmallDto getTenant() {
        return tenant;
    }

    public void setTenant(TenantSmallDto tenant) {
        this.tenant = tenant;
    }

    public DepartmentSmallDto getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentSmallDto department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", realname='" + realname + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", enabled=" + enabled +
                ", classification='" + classification + '\'' +
                ", tenant=" + tenant +
                ", department=" + department +
                ", createdUserId=" + createdUserId +
                ", createdTime=" + createdTime +
                ", modifiedUserId=" + modifiedUserId +
                ", modifiedTime=" + modifiedTime +
                '}';
    }
}
