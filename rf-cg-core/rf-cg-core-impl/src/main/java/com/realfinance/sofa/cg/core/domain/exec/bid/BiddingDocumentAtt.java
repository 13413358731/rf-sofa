package com.realfinance.sofa.cg.core.domain.exec.bid;

import com.realfinance.sofa.cg.core.domain.BasePurAtt;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 标书附件
 */
@Entity
@Table(name = "CG_CORE_BID_DOC_ATT")
public class BiddingDocumentAtt extends BasePurAtt {

    @ManyToOne
    @JoinColumn(name = "bid_doc_id", updatable = false)
    private BiddingDocument biddingDocument;

    public BiddingDocument getBiddingDocument() {
        return biddingDocument;
    }

    public void setBiddingDocument(BiddingDocument biddingDocument) {
        this.biddingDocument = biddingDocument;
    }
}
