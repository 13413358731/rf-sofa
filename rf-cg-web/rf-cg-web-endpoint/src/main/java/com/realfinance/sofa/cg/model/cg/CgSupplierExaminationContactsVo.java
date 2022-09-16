package com.realfinance.sofa.cg.model.cg;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Schema(description = "供应商修改审核-联系人对象")
public class CgSupplierExaminationContactsVo {

    @NotBlank(groups = CgSupplierExaminationVo.SupplierEdit.class)
    @Schema(description = "姓名")
    protected String name;
    @NotNull(groups = CgSupplierExaminationVo.SupplierEdit.class)
    @Pattern(regexp = "(?:0|86|\\+86)?1[3456789]\\d{9}")
    @Schema(description = "手机号")
    protected String mobile;
    @NotNull(groups = CgSupplierExaminationVo.SupplierEdit.class)
    @Email
    @Schema(description = "邮箱")
    protected String email;
    @Schema(description = "传真")
    protected String fax;
    @NotNull(groups = CgSupplierExaminationVo.SupplierEdit.class)
    @Schema(description = "是否主联系人")
    protected Boolean primary;
    @Schema(description = "业务领域和区域")
    protected String businessArea;
    @Schema(description = "备注")
    protected String note;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public Boolean getPrimary() {
        return primary;
    }

    public void setPrimary(Boolean primary) {
        this.primary = primary;
    }

    public String getBusinessArea() {
        return businessArea;
    }

    public void setBusinessArea(String businessArea) {
        this.businessArea = businessArea;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
