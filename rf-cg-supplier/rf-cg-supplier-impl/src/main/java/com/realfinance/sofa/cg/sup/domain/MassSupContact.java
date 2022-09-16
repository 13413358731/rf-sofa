package com.realfinance.sofa.cg.sup.domain;

import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

/**
 * 群发消息子表
 */
@Entity
@Table(name = "CG_SUP_SUPPLIER_MASS_SUP_CONTACT")
public class MassSupContact extends BaseEntity implements IEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 供应商
     */
    private Integer supplierId;

    /**
     * 供应商联系人
     */
    private Integer supContactId;



    private String supplierName;

    /**
     * 供应商联系人名称
     */
    private  String contactName;

    /**
     * 是否主联系人
     */
    private Boolean contactIsPrimary;

    /**
     * 联系电话
     */
    @Pattern(regexp = "(?:0|86|\\+86)?1[3456789]\\d{9}")
    @Column(nullable = false, length = 15)
    private String contactMobile;

    /**
     * 联系人邮箱
     */
    private String contactEmail;


    @ManyToOne
    @JoinColumn(name = "messaging_id", updatable = false)
    private MassMessaging messaging;


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

    public Integer getSupContactId() {
        return supContactId;
    }

    public void setSupContactId(Integer supContactId) {
        this.supContactId = supContactId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public Boolean getContactIsPrimary() {
        return contactIsPrimary;
    }

    public void setContactIsPrimary(Boolean contactIsPrimary) {
        this.contactIsPrimary = contactIsPrimary;
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

    public MassMessaging getMessaging() {
        return messaging;
    }

    public void setMessaging(MassMessaging messaging) {
        this.messaging = messaging;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
}
