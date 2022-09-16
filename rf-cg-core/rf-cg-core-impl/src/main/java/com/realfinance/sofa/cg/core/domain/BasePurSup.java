package com.realfinance.sofa.cg.core.domain;

import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 推荐供应商
 */
@MappedSuperclass
public abstract class BasePurSup implements IEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    /**
     * 供应商ID
     */
    @Column(nullable = false)
    protected Integer supplierId;

    /**
     * 联系人姓名
     */
    @Column
    protected String contactName;

    /**
     * 联系人手机
     */
    @Column
    protected String contactMobile;

    /**
     * 联系人邮箱
     */
    @Column
    protected String contactEmail;

    /**
     * 推荐理由
     */
    @Column
    protected String reason;

    /**
     * 备注
     */
    @Column
    protected String note;

    /**
     * 来源
     */
    @Column
    protected String source;

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

    @Override
    public Integer getId() {
        return id;
    }

    @Override
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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

    public LocalDateTime getSupplierCreditTime() {
        return supplierCreditTime;
    }

    public void setSupplierCreditTime(LocalDateTime supplierCreditTime) {
        this.supplierCreditTime = supplierCreditTime;
    }
}
