package com.realfinance.sofa.cg.sup.domain;

import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 公告附件
 */
@Entity
@Table(name = "CG_SUP_SUPPLIER_ANNOUNCEMENT_ATTACHMENT")
public class SupplierAnnouncementAttachment extends Attachment implements IEntity<Integer> {

    /**
     * 所属公告
     */
    @ManyToOne
    @JoinColumn(name = "announcement_id", updatable = false)
    private SupplierAnnouncement supplierAnnouncement;

    public SupplierAnnouncement getSupplierAnnouncement() {
        return supplierAnnouncement;
    }

    public void setSupplierAnnouncement(SupplierAnnouncement supplierAnnouncement) {
        this.supplierAnnouncement = supplierAnnouncement;
    }
}
