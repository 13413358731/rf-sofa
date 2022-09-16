package com.realfinance.sofa.cg.model.cg;

import com.realfinance.sofa.cg.model.system.DepartmentVo;
import com.realfinance.sofa.cg.model.system.UserVo;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "供应商考核指标")
public class CgSupplierEvaluatorVo {

    public interface Save {
    }

    @Schema(description = "姓名")
    private String realname;

    @Schema(description = "人员编码")
    private String username;

    @Schema(description = "制单人部门")
    protected DepartmentVo department;

    @Schema(description = "联系电话")
    private String mobile;

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public DepartmentVo getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentVo department) {
        this.department = department;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
