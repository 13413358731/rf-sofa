package com.realfinance.sofa.cg.core.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class CgMultipleReleaseDetailsSaveDto {

    private Integer id;

    protected String name;

    /**
     * 应答截止时间
     */
    protected LocalDateTime replyEndTime;

    /**
     * 唱标开始时间
     */
    protected LocalDateTime openStartTime;

    /**
     * 唱标截止时间
     */
    protected LocalDateTime openEndTime;

    /**
     * 需要报价
     */
    protected Boolean needQuote;

    /**
     * 内容
     */
    protected String content;

    protected Integer projectExecution;

    private List<CgProjectExecutionAttDto> multipleReleaseAtts;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getReplyEndTime() {
        return replyEndTime;
    }

    public void setReplyEndTime(LocalDateTime replyEndTime) {
        this.replyEndTime = replyEndTime;
    }

    public LocalDateTime getOpenStartTime() {
        return openStartTime;
    }

    public void setOpenStartTime(LocalDateTime openStartTime) {
        this.openStartTime = openStartTime;
    }

    public LocalDateTime getOpenEndTime() {
        return openEndTime;
    }

    public void setOpenEndTime(LocalDateTime openEndTime) {
        this.openEndTime = openEndTime;
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

    public List<CgProjectExecutionAttDto> getMultipleReleaseAtts() {
        return multipleReleaseAtts;
    }

    public void setMultipleReleaseAtts(List<CgProjectExecutionAttDto> multipleReleaseAtts) {
        this.multipleReleaseAtts = multipleReleaseAtts;
    }

    public Set<Integer> getSupplierIds() {
        return supplierIds;
    }

    public void setSupplierIds(Set<Integer> supplierIds) {
        this.supplierIds = supplierIds;
    }
}
