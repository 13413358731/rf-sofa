package com.realfinance.sofa.cg.core.model;

import java.time.LocalDateTime;

public class CgMultipleReleaseDto extends BaseDto {

    protected Integer departmentId;

    protected Integer id;

    /**
     * 标题
     */
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

    protected String status;

    protected LocalDateTime passTime;

    protected CgProjectExecutionDto projectExecution;

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

    public CgProjectExecutionDto getProjectExecution() {
        return projectExecution;
    }

    public void setProjectExecution(CgProjectExecutionDto projectExecution) {
        this.projectExecution = projectExecution;
    }
}
