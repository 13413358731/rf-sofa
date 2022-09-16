package com.realfinance.sofa.cg.core.domain;


import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;



/**
 * 采购结果通知子表(供应商)
 */
@Entity
@Table(name = "CG_CORE_PURCHASE_RESULT_NOTICE_SUP")
public class PurchaseResultNoticeSup implements IEntity<Integer> {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer supplierId;

    private Integer supContactId;

    private Boolean isBid;

    //@Column(nullable = false)
    @Column
    private String name;

    private String supplierName;

    //@Column(nullable = false)
    @Column
    private Boolean isPrimary;

    //@Pattern(regexp = "(?:0|86|\\+86)?1[3456789]\\d{9}")
    //@Column(nullable = false, length = 15)
    @Column
    private String mobile;

    //@Column(nullable = false)
    @Column
    @Email
    private String email;

    @ManyToOne
    @JoinColumn(name= "resultnotice_id", referencedColumnName = "id")
    private PurchaseResultNotice purchaseResultNotice;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public PurchaseResultNotice getPurchaseResultNotice() {
        return purchaseResultNotice;
    }

    public void setPurchaseResultNotice(PurchaseResultNotice purchaseResultNotice) {
        this.purchaseResultNotice = purchaseResultNotice;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
}
