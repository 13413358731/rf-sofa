package com.realfinance.sofa.cg.model.cg;

import com.realfinance.sofa.cg.model.BaseVo;
import com.realfinance.sofa.common.model.IdentityObject;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "供应商-联系人对象")
public class CgSupplierContactsVo extends BaseVo implements IdentityObject<Integer> {
    @Schema(description = "ID")
    protected Integer id;
    @Schema(description = "姓名")
    protected String name;
    @Schema(description = "手机号")
    protected String mobile;
    @Schema(description = "邮箱")
    protected String email;
    @Schema(description = "传真")
    protected String fax;
    @Schema(description = "是否主联系人")
    protected Boolean primary;
    @Schema(description = "业务领域和区域")
    protected String businessArea;
    @Schema(description = "备注")
    protected String note;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

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
