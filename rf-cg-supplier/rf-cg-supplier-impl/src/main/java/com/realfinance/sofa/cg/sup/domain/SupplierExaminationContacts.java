package com.realfinance.sofa.cg.sup.domain;

import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import javax.validation.constraints.Email;

/**
 * 供应商审核联系人
 */
@Entity
@Table(name = "CG_SUP_SUPPLIER_EXAMINATION_CONTACTS")
public class SupplierExaminationContacts implements IEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 姓名
     */
    @Column(nullable = false)
    private String name;
    /**
     * 手机
     */
    @Column(nullable = false)
    private String mobile;
    /**
     * 邮箱
     */
    @Column(nullable = false)
    @Email
    private String email;
    /**
     * 传真
     */
    @Column
    private String fax;
    /**
     * 是否主联系人
     */
    @Column(nullable = false)
    private Boolean isPrimary;
    /**
     * 业务领域和区域
     */
    @Column
    private String businessArea;
    /**
     * 备注
     */
    @Column
    private String note;

    @ManyToOne
    @JoinColumn(name = "supplier_examination_id", updatable = false)
    private SupplierExamination supplierExamination;

    public Integer getId() {
        return id;
    }

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
        return isPrimary;
    }

    public void setPrimary(Boolean primary) {
        isPrimary = primary;
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

    public SupplierExamination getSupplierExamination() {
        return supplierExamination;
    }

    public void setSupplierExamination(SupplierExamination supplierExamination) {
        this.supplierExamination = supplierExamination;
    }
}
