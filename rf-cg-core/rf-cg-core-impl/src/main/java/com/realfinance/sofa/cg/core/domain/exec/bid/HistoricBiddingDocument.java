package com.realfinance.sofa.cg.core.domain.exec.bid;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "CG_CORE_HI_BID_DOC")
public class HistoricBiddingDocument extends BaseBiddingDocument {

    @Column(nullable = false)
    private Integer srcId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "hi_bid_doc_id")
    private List<HistoricBiddingDocumentSection> biddingDocumentSections;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "hi_bid_doc_id")
    private List<HistoricBiddingDocumentQualExamination> biddingDocumentQualExaminations;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "hi_bid_doc_id")
    private List<HistoricBiddingDocumentRespExamination> biddingDocumentRespExaminations;

    /**
     * 发标的供应商ID
     */
    @Column
    @ElementCollection
    @CollectionTable(name="CG_CORE_HI_BID_DOC_SUP")
    private Set<Integer> supplierIds;

    public HistoricBiddingDocument() {
        biddingDocumentSections = new ArrayList<>();
        biddingDocumentQualExaminations = new ArrayList<>();
        biddingDocumentRespExaminations = new ArrayList<>();
        supplierIds = new HashSet<>();
    }

    public Integer getSrcId() {
        return srcId;
    }

    public void setSrcId(Integer srcId) {
        this.srcId = srcId;
    }

    public List<HistoricBiddingDocumentSection> getBiddingDocumentSections() {
        return biddingDocumentSections;
    }

    public void setBiddingDocumentSections(List<HistoricBiddingDocumentSection> biddingDocumentSections) {
        this.biddingDocumentSections = biddingDocumentSections;
    }

    public List<HistoricBiddingDocumentQualExamination> getBiddingDocumentQualExaminations() {
        return biddingDocumentQualExaminations;
    }

    public void setBiddingDocumentQualExaminations(List<HistoricBiddingDocumentQualExamination> supplierQualExaminations) {
        this.biddingDocumentQualExaminations = supplierQualExaminations;
    }

    public List<HistoricBiddingDocumentRespExamination> getBiddingDocumentRespExaminations() {
        return biddingDocumentRespExaminations;
    }

    public void setBiddingDocumentRespExaminations(List<HistoricBiddingDocumentRespExamination> supplierRespExaminations) {
        this.biddingDocumentRespExaminations = supplierRespExaminations;
    }

    public Set<Integer> getSupplierIds() {
        return supplierIds;
    }

    public void setSupplierIds(Set<Integer> supplierIds) {
        this.supplierIds = supplierIds;
    }
}
