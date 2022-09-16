package com.realfinance.sofa.cg.core.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class CgBiddingDocumentDetailsSaveDto {

    private Integer id;

    protected String docName;

    protected String name;

    /**
     * 投标截止时间
     */
    protected LocalDateTime bidEndTime;

    /**
     * 唱标开始时间
     */
    protected LocalDateTime openBidStartTime;

    /**
     * 唱标截止时间
     */
    protected LocalDateTime openBidEndTime;

    /**
     * 需要报价
     */
    protected Boolean needQuote;

    /**
     * 内容
     */
    protected String content;

    protected Integer projectExecution;

    private List<CgBiddingDocumentSectionDto> biddingDocumentSections;

    private List<CgBiddingDocumentExaminationDto> biddingDocumentQualExaminations;

    private List<CgBiddingDocumentExaminationDto> biddingDocumentRespExaminations;

    private List<CgProjectExecutionAttDto> biddingDocumentAtts;

    /**
     * 发标的供应商ID
     */
    private Set<Integer> supplierIds;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getBidEndTime() {
        return bidEndTime;
    }

    public void setBidEndTime(LocalDateTime bidEndTime) {
        this.bidEndTime = bidEndTime;
    }

    public LocalDateTime getOpenBidStartTime() {
        return openBidStartTime;
    }

    public void setOpenBidStartTime(LocalDateTime openBidStartTime) {
        this.openBidStartTime = openBidStartTime;
    }

    public LocalDateTime getOpenBidEndTime() {
        return openBidEndTime;
    }

    public void setOpenBidEndTime(LocalDateTime openBidEndTime) {
        this.openBidEndTime = openBidEndTime;
    }

    public Boolean getNeedQuote() {
        return needQuote;
    }

    public void setNeedQuote(Boolean needQuote) {
        this.needQuote = needQuote;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getProjectExecution() {
        return projectExecution;
    }

    public void setProjectExecution(Integer projectExecution) {
        this.projectExecution = projectExecution;
    }

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

    public Set<Integer> getSupplierIds() {
        return supplierIds;
    }

    public void setSupplierIds(Set<Integer> supplierIds) {
        this.supplierIds = supplierIds;
    }

    public List<CgProjectExecutionAttDto> getBiddingDocumentAtts() {
        return biddingDocumentAtts;
    }

    public void setBiddingDocumentAtts(List<CgProjectExecutionAttDto> biddingDocumentAtts) {
        this.biddingDocumentAtts = biddingDocumentAtts;
    }
}
