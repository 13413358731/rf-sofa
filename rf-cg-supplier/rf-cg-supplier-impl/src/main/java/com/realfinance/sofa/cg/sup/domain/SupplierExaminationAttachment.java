package com.realfinance.sofa.cg.sup.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 供应商审核附件
 */
@Entity
@Table(name = "CG_SUP_SUPPLIER_EXAMINATION_ATTACHMENT")
public class SupplierExaminationAttachment extends Attachment {
    @ManyToOne
    @JoinColumn(name = "supplier_examination_id", updatable = false)
    private SupplierExamination supplierExamination;

}
