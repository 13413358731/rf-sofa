package com.realfinance.sofa.cg.model.cg;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "采购结果通知-供应商对象")
public class CgPurResultNoticeSupVo {

    protected Integer id;

    @Schema(description = "供应商Id")
    protected CgSupplierVo supplierId;

    @Schema(description = "供应商联系人Id")
    protected Integer supContactId;

    @Schema(description = "是否中标")
    protected Boolean isBid;

    @Schema(description = "供应商名称")
    protected String supplierName;

    @Schema(description = "是否主联系人")
    protected Boolean isPrimary;

    @Schema(description = "手机号")
    protected String mobile;

    @Schema(description = "电子邮件")
    protected String email;

    protected String name;

    /*protected List<CgSupplierContactsVo> contactsVos;

    public List<CgSupplierContactsVo> getContactsVos() {
        return contactsVos;
    }

    public void setContactsVos(List<CgSupplierContactsVo> contactsVos) {
        this.contactsVos = contactsVos;
    }*/

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CgSupplierVo getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(CgSupplierVo supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getSupContactId() {
        return supContactId;
    }

    public void setSupContactId(Integer supContactId) {
        this.supContactId = supContactId;
    }

    public Boolean getBid() {
        return isBid;
    }

    public void setBid(Boolean bid) {
        isBid = bid;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Boolean getPrimary() {
        return isPrimary;
    }

    public void setPrimary(Boolean primary) {
        isPrimary = primary;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
