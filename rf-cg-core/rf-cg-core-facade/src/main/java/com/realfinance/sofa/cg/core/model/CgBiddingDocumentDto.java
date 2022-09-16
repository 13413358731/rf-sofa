package com.realfinance.sofa.cg.core.model;

import java.time.LocalDateTime;

public class CgBiddingDocumentDto extends BaseDto {

    protected Integer departmentId;

    protected Integer id;

    protected String name;

    /**
     * 生成文档名称
     */
    protected String docName;

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


    /**
     * 监督人员开启价格的时间
     */
    protected LocalDateTime preOpenQuoteTime;
    /**
     * 开启价格的监督人员ID
     */
    protected Integer preOpenQuoteUserId;
    /**
     * 开启价格时间
     */
    protected LocalDateTime openQuoteTime;

    /**
     * 监督人员开启商务的时间
     */
    protected LocalDateTime preOpenBizTime;
    /**
     * 开启商务的监督人员ID
     */
    protected Integer preOpenBizUserId;
    /**
     * 开启商务时间
     */
    protected LocalDateTime openBizTime;

    private CgProjectExecutionDto projectExecution;

    protected String status;

    protected LocalDateTime passTime;

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
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

    public LocalDateTime getPreOpenQuoteTime() {
        return preOpenQuoteTime;
    }

    public void setPreOpenQuoteTime(LocalDateTime preOpenQuoteTime) {
        this.preOpenQuoteTime = preOpenQuoteTime;
    }

    public Integer getPreOpenQuoteUserId() {
        return preOpenQuoteUserId;
    }

    public void setPreOpenQuoteUserId(Integer preOpenQuoteUserId) {
        this.preOpenQuoteUserId = preOpenQuoteUserId;
    }

    public LocalDateTime getOpenQuoteTime() {
        return openQuoteTime;
    }

    public void setOpenQuoteTime(LocalDateTime openQuoteTime) {
        this.openQuoteTime = openQuoteTime;
    }

    public LocalDateTime getPreOpenBizTime() {
        return preOpenBizTime;
    }

    public void setPreOpenBizTime(LocalDateTime preOpenBizTime) {
        this.preOpenBizTime = preOpenBizTime;
    }

    public Integer getPreOpenBizUserId() {
        return preOpenBizUserId;
    }

    public void setPreOpenBizUserId(Integer preOpenBizUserId) {
        this.preOpenBizUserId = preOpenBizUserId;
    }

    public LocalDateTime getOpenBizTime() {
        return openBizTime;
    }

    public void setOpenBizTime(LocalDateTime openBizTime) {
        this.openBizTime = openBizTime;
    }

    public CgProjectExecutionDto getProjectExecution() {
        return projectExecution;
    }

    public void setProjectExecution(CgProjectExecutionDto projectExecution) {
        this.projectExecution = projectExecution;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getPassTime() {
        return passTime;
    }

    public void setPassTime(LocalDateTime passTime) {
        this.passTime = passTime;
    }
}
