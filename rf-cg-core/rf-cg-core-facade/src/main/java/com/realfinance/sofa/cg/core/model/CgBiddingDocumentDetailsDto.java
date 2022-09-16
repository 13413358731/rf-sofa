package com.realfinance.sofa.cg.core.model;

import java.util.List;
import java.util.Set;

public class CgBiddingDocumentDetailsDto extends CgBiddingDocumentDto {

    private List<CgBiddingDocumentSectionDto> biddingDocumentSections;

    private List<CgBiddingDocumentExaminationDto> biddingDocumentQualExaminations;

    private List<CgBiddingDocumentExaminationDto> biddingDocumentRespExaminations;

    private List<CgProjectExecutionAttDto> biddingDocumentAtts;

    private Set<Integer> supplierIds;

    public List<CgBiddingDocumentSectionDto> getBiddingDocumentSections() {
        return biddingDocumentSections;
    }

    public void setBiddingDocumentSections(List<CgBiddingDocumentSectionDto> biddingDocumentSections) {
        this.biddingDocumentSections = biddingDocumentSections;
    }

    public List<CgBiddingDocumentExaminationDto> getBiddingDocumentQualExaminations() {
        return biddingDocumentQualExaminations;
    }

    public void setBiddingDocumentQualExaminations(List<CgBiddingDocumentExaminationDto> biddingDocumentQualExaminations) {
        this.biddingDocumentQualExaminations = biddingDocumentQualExaminations;
    }

    public List<CgBiddingDocumentExaminationDto> getBiddingDocumentRespExaminations() {
        return biddingDocumentRespExaminations;
    }

    public void setBiddingDocumentRespExaminations(List<CgBiddingDocumentExaminationDto> biddingDocumentRespExaminations) {
        this.biddingDocumentRespExaminations = biddingDocumentRespExaminations;
    }

    public List<CgProjectExecutionAttDto> getBiddingDocumentAtts() {
        return biddingDocumentAtts;
    }

    public void setBiddingDocumentAtts(List<CgProjectExecutionAttDto> biddingDocumentAtts) {
        this.biddingDocumentAtts = biddingDocumentAtts;
    }

    public Set<Integer> getSupplierIds() {
        return supplierIds;
    }

    public void setSupplierIds(Set<Integer> supplierIds) {
        this.supplierIds = supplierIds;
    }
}
