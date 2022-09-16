package com.realfinance.sofa.cg.core.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class CgDrawExpertSaveDto implements Serializable {

    protected Integer id;

    protected String receipt;

    protected String event;

    protected String sort;

    protected Integer meetingNumber;

    protected Integer drawNumber;

    protected String tenantId;

    protected LocalDateTime drawTime;

    protected String phone;

    protected String description;

    protected String noticeExpert;

    protected Boolean validStatus;

    protected Integer projectId;

    protected Integer confirmNumber;

    protected LocalDateTime biddingTime;

    protected LocalDateTime endTime;

    protected List<CgDrawExpertListDto> drawExpertLists;

    protected List<CgDrawExpertRuleDto> drawExpertRules;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public Integer getMeetingNumber() {
        return meetingNumber;
    }

    public void setMeetingNumber(Integer meetingNumber) {
        this.meetingNumber = meetingNumber;
    }

    public Integer getDrawNumber() {
        return drawNumber;
    }

    public void setDrawNumber(Integer drawNumber) {
        this.drawNumber = drawNumber;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public LocalDateTime getDrawTime() {
        return drawTime;
    }

    public void setDrawTime(LocalDateTime drawTime) {
        this.drawTime = drawTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNoticeExpert() {
        return noticeExpert;
    }

    public void setNoticeExpert(String noticeExpert) {
        this.noticeExpert = noticeExpert;
    }

    public Boolean getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(Boolean validStatus) {
        this.validStatus = validStatus;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getConfirmNumber() {
        return confirmNumber;
    }

    public void setConfirmNumber(Integer confirmNumber) {
        this.confirmNumber = confirmNumber;
    }

    public LocalDateTime getBiddingTime() {
        return biddingTime;
    }

    public void setBiddingTime(LocalDateTime biddingTime) {
        this.biddingTime = biddingTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public List<CgDrawExpertListDto> getDrawExpertLists() {
        return drawExpertLists;
    }

    public void setDrawExpertLists(List<CgDrawExpertListDto> drawExpertLists) {
        this.drawExpertLists = drawExpertLists;
    }
}
