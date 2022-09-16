package com.realfinance.sofa.cg.sup.domain;

import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 征集附件
 */
@Entity
@Table(name = "CG_SUP_SUPPLIER_SOLICITATION_ATTACHMENT")
public class SupplierSolicitationAttachment extends Attachment implements IEntity<Integer> {
    /**
     * 所属意向征集
     */
    @ManyToOne
    @JoinColumn(name = "solicitation_id", updatable = false)
    private SupplierSolicitation solicitation;

    public SupplierSolicitation getSolicitation() {
        return solicitation;
    }

    public void setSolicitation(SupplierSolicitation solicitation) {
        this.solicitation = solicitation;
    }
}
