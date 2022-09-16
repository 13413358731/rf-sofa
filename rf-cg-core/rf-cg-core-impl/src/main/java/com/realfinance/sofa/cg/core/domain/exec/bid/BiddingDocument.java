package com.realfinance.sofa.cg.core.domain.exec.bid;

import com.realfinance.sofa.cg.core.domain.exec.release.MultipleReleaseAtt;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 标书
 */
@Entity
@Table(name = "CG_CORE_BID_DOC")
public class BiddingDocument extends BaseBiddingDocument {

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "bid_doc_id")
    private List<BiddingDocumentSection> biddingDocumentSections;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "bid_doc_id")
    private List<BiddingDocumentBiddingDocumentQualExamination> biddingDocumentQualExaminations;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "bid_doc_id")
    private List<BiddingDocumentBiddingDocumentRespExamination> biddingDocumentRespExaminations;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "bid_doc_id")
    private List<BiddingDocumentAtt> biddingDocumentAtts;

    /**
     * 发标的供应商ID
     */
    @Column
    @ElementCollection
    @CollectionTable(name="CG_CORE_BID_DOC_SUP")
    private Set<Integer> supplierIds;

    public BiddingDocument() {
        biddingDocumentSections = new ArrayList<>();
        biddingDocumentQualExaminations = new ArrayList<>();
        biddingDocumentRespExaminations = new ArrayList<>();
        biddingDocumentAtts=new ArrayList<>();
        supplierIds = new HashSet<>();
    }

    public List<BiddingDocumentAtt> getBiddingDocumentAtts() {
        return biddingDocumentAtts;
    }

    public void setBiddingDocumentAtts(List<BiddingDocumentAtt> biddingDocumentAtts) {
        this.biddingDocumentAtts = biddingDocumentAtts;
    }

    public List<BiddingDocumentSection> getBiddingDocumentSections() {
        return biddingDocumentSections;
    }

    public void setBiddingDocumentSections(List<BiddingDocumentSection> biddingDocumentSections) {
        this.biddingDocumentSections = biddingDocumentSections;
    }

    public List<BiddingDocumentBiddingDocumentQualExamination> getBiddingDocumentQualExaminations() {
        return biddingDocumentQualExaminations;
    }

    public void setBiddingDocumentQualExaminations(List<BiddingDocumentBiddingDocumentQualExamination> biddingDocumentQualExaminations) {
        this.biddingDocumentQualExaminations = biddingDocumentQualExaminations;
    }

    public List<BiddingDocumentBiddingDocumentRespExamination> getBiddingDocumentRespExaminations() {
        return biddingDocumentRespExaminations;
    }

    public void setBiddingDocumentRespExaminations(List<BiddingDocumentBiddingDocumentRespExamination> biddingDocumentRespExaminations) {
        this.biddingDocumentRespExaminations = biddingDocumentRespExaminations;
    }

    public Set<Integer> getSupplierIds() {
        return supplierIds;
    }

    public void setSupplierIds(Set<Integer> supplierIds) {
        this.supplierIds = supplierIds;
    }
}
