package com.realfinance.sofa.cg.sup.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CG_SUP_SUPPLIER_EXAMINATION_CREDIT")
public class SupplierExaminationCredit extends Credit{

    @ManyToOne
    @JoinColumn(name = "supplier_examination_id", updatable = false)
    private SupplierExamination supplierExamination;

    public SupplierExamination getSupplierExamination() {
        return supplierExamination;
    }

    public void setSupplierExamination(SupplierExamination supplierExamination) {
        this.supplierExamination = supplierExamination;
    }
}
