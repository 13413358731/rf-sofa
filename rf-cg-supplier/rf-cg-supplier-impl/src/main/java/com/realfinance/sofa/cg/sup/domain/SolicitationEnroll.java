package com.realfinance.sofa.cg.sup.domain;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;


/**
 * 供应商意向征集报名表
 */
@Entity
@Table(name = "CG_SUP_SUPPLIER_SOLICITATIONENROLL")
public class SolicitationEnroll {


    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 供应商ID
     */
    @Column
    private Integer supplierId;

    /**
     * 意向征集ID
     */
    @Column
    private Integer solicitationId;

    /**
     * 供应商名称
     */
    @Column
    private String supplierName;


    /**
     * 联系人名字
     */
    @Column
    private String contactName;

    /**
     * 联系人电话
     */
    @Column
    @Pattern(regexp = "(?:0|86|\\+86)?1[3456789]\\d{9}")
    private String mobile;


    /**
     * 联系人邮箱
     */
    @Column
    @Email
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
