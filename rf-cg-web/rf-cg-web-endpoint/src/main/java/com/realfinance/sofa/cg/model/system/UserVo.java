package com.realfinance.sofa.cg.model.system;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.realfinance.sofa.cg.model.BaseVo;
import com.realfinance.sofa.common.model.IdentityObject;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "用户对象")
public class UserVo extends BaseVo implements IdentityObject<Integer> {

    @Schema(description = "ID")
    protected Integer id;
    @Schema(description = "用户名")
    protected String username;
    @Schema(description = "姓名")
    protected String realname;
    @Schema(description = "邮箱")
    protected String email;
    @Schema(description = "手机")
    protected String mobile;
    @Schema(description = "是否启用")
    protected Boolean enabled;
    @Schema(description = "分类")
    protected String classification;
    @Schema(description = "法人")
    protected TenantVo tenant;
    @Schema(description = "部门")
    protected DepartmentVo department;

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

    public TenantVo getTenant() {
        return tenant;
    }

    public void setTenant(TenantVo tenant) {
        this.tenant = tenant;
    }

    public DepartmentVo getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentVo department) {
        this.department = department;
    }
}
