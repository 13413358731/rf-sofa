package com.realfinance.sofa.cg.sup.domain;

import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 供应商黑名单附件
 */
@Entity
@Table(name = "CG_SUP_SUPPLIER_BLACKLIST_ATTACHMENT")
public class SupplierBlacklistAttachment extends Attachment implements IEntity<Integer> {

    @ManyToOne
    @JoinColumn(name = "supplier_blacklist_id", updatable = false)
    private SupplierBlacklist supplierBlacklist;


    public SupplierBlacklist getSupplierBlacklist() {
        return supplierBlacklist;
    }

    public void setSupplierBlacklist(SupplierBlacklist supplierBlacklist) {
        this.supplierBlacklist = supplierBlacklist;
    }
}
