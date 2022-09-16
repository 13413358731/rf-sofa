package com.realfinance.sofa.cg.core.domain.exec.bid;

import com.realfinance.sofa.cg.core.domain.BasePurAtt;

import javax.persistence.*;

/**
 * 标书附件
 */
@Entity
@Table(name = "CG_CORE_HI_BID_DOC_ATT")
public class HistoricBiddingDocumentAtt extends BasePurAtt {

    @Column(nullable = false)
    private Integer srcId;

    /**
     * 供应商ID
     */
    @Column()
    private Integer supplierId;

    @ManyToOne
    @JoinColumn(name = "bid_doc_id", updatable = false)
    private HistoricBiddingDocument biddingDocument;

    public Integer getSrcId() {
        return srcId;
    }

    public void setSrcId(Integer srcId) {
        this.srcId = srcId;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public HistoricBiddingDocument getBiddingDocument() {
        return biddingDocument;
    }

    public void setBiddingDocument(HistoricBiddingDocument biddingDocument) {
        this.biddingDocument = biddingDocument;
    }
}
