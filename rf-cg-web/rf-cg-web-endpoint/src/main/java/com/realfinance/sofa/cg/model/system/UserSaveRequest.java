package com.realfinance.sofa.cg.model.system;

import com.realfinance.sofa.common.model.ReferenceObject;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Schema(description = "保存用户请求对象")
public class UserSaveRequest {

    @Schema(description = "ID")
    protected Integer id;
    @NotNull
    @Schema(description = "用户名")
    protected String username;
    @Schema(description = "姓名")
    protected String realname;
    @Email
    @Schema(description = "邮箱")
    protected String email;
    @Pattern(regexp = "(?:0|86|\\+86)?1[3456789]\\d{9}")
    @Schema(description = "手机")
    protected String mobile;
    @NotNull
    @Schema(description = "是否启用")
    protected Boolean enabled;
    @Schema(description = "分类")
    protected String classification;
    @NotNull
    @Schema(description = "法人")
    protected ReferenceObject<String> tenant;
    @Schema(description = "部门")
    protected ReferenceObject<Integer> department;

    @Schema(description = "关联的角色")
    protected Set<ReferenceObject<Integer>> roles;
    @Schema(description = "关联的角色组")
    protected Set<ReferenceObject<Integer>> roleGroups;

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

    public ReferenceObject<String> getTenant() {
        return tenant;
    }

    public void setTenant(ReferenceObject<String> tenant) {
        this.tenant = tenant;
    }

    public ReferenceObject<Integer> getDepartment() {
        return department;
    }

    public void setDepartment(ReferenceObject<Integer> department) {
        this.department = department;
    }

    public Set<ReferenceObject<Integer>> getRoles() {
        return roles;
    }

    public void setRoles(Set<ReferenceObject<Integer>> roles) {
        this.roles = roles;
    }

    public Set<ReferenceObject<Integer>> getRoleGroups() {
        return roleGroups;
    }

    public void setRoleGroups(Set<ReferenceObject<Integer>> roleGroups) {
        this.roleGroups = roleGroups;
    }
}
