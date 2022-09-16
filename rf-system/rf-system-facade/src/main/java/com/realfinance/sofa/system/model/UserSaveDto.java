package com.realfinance.sofa.system.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Set;

public class UserSaveDto implements Serializable {
    protected Integer id;
    @NotNull
    protected String username;
    protected String realname;
    @Email
    protected String email;
    @Pattern(regexp = "(?:0|86|\\+86)?1[3456789]\\d{9}")
    protected String mobile;
    @NotNull
    protected Boolean enabled;
    protected String classification;
    @NotNull
    protected String tenant;
    protected Integer department;
    protected Set<Integer> roles;
    protected Set<Integer> roleGroups;

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

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public Integer getDepartment() {
        return department;
    }

    public void setDepartment(Integer department) {
        this.department = department;
    }

    public Set<Integer> getRoles() {
        return roles;
    }

    public void setRoles(Set<Integer> roles) {
        this.roles = roles;
    }

    public Set<Integer> getRoleGroups() {
        return roleGroups;
    }

    public void setRoleGroups(Set<Integer> roleGroups) {
        this.roleGroups = roleGroups;
    }

    @Override
    public String toString() {
        return "UserSaveDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", realname='" + realname + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", enabled=" + enabled +
                ", classification='" + classification + '\'' +
                ", tenant='" + tenant + '\'' +
                ", department=" + department +
                ", roles=" + roles +
                ", roleGroups=" + roleGroups +
                '}';
    }
}
