package com.realfinance.sofa.cg.sup.model;

import java.time.LocalDateTime;

public class CgRequirementSupDto {

    private Integer id;

    /**
     * 供应商ID
     */
    private Integer supplierId;

    /**
     * 联系人姓名
     */
    private String contactName;

    /**
     * 联系人手机
     */
    private String contactMobile;

    /**
     * 联系人邮箱
     */
    private String contactEmail;

    /**
     * 推荐理由
     */
    private String reason;

    /**
     * 备注
     */
    private String note;

    /**
     * 供应商关联交易情况
     */
    protected String supplierRelatedStatus;

    /**
     * 供应商信用情况
     */
    protected String supplierCreditStatus;

    /**
     * 供应商信用查询时间
     */
    protected LocalDateTime supplierCreditTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDateTime getSupplierCreditTime() {
        return supplierCreditTime;
    }

    public void setSupplierCreditTime(LocalDateTime supplierCreditTime) {
        this.supplierCreditTime = supplierCreditTime;
    }


    public String getSupplierRelatedStatus() {
        return supplierRelatedStatus;
    }

    public void setSupplierRelatedStatus(String supplierRelatedStatus) {
        this.supplierRelatedStatus = supplierRelatedStatus;
    }

    public String getSupplierCreditStatus() {
        return supplierCreditStatus;
    }

    public void setSupplierCreditStatus(String supplierCreditStatus) {
        this.supplierCreditStatus = supplierCreditStatus;
    }
}
