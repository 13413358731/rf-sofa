package com.realfinance.sofa.cg.core.domain.exec.bid;

import javax.persistence.*;

@Entity
@Table(name = "CG_CORE_HI_BID_DOC_SEC")
public class HistoricBiddingDocumentSection extends BaseBiddingDocumentSection {

    @Column(nullable = false)
    private Integer srcId;

    @ManyToOne
    @JoinColumn(name = "hi_bid_doc_id", updatable = false)
    protected HistoricBiddingDocument biddingDocument;

    public Integer getSrcId() {
        return srcId;
    }

    public void setSrcId(Integer srcId) {
        this.srcId = srcId;
    }

    public HistoricBiddingDocument getBiddingDocument() {
        return biddingDocument;
    }

    public void setBiddingDocument(HistoricBiddingDocument biddingDocument) {
        this.biddingDocument = biddingDocument;
    }
}
