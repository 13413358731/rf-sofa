package com.realfinance.sofa.cg.sup.domain;

import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.util.List;

/**
 * 供应商信用信息表
 */
@Entity
@Table(name = "CG_SUP_SUPPLIER_CREDIT")
public class SupplierCredit extends Credit {
    @ManyToOne
    @JoinColumn(name = "supplier_id", updatable = false)
    private Supplier supplier;

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}
