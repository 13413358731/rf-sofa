package com.realfinance.sofa.cg.sup.model;

import java.io.Serializable;

public class SolicitationEnrollDto implements Serializable {

    private Integer id;
    private  Integer supplierId;
    private  Integer  solicitationId;
    private String supplierName;
    private String contactName;
    private String mobile;
    private String email;

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

    public Integer getSolicitationId() {
        return solicitationId;
    }

    public void setSolicitationId(Integer solicitationId) {
        this.solicitationId = solicitationId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
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
}
