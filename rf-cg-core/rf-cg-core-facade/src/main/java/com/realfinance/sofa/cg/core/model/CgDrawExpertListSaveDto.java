package com.realfinance.sofa.cg.core.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class CgDrawExpertListSaveDto implements Serializable {

    protected Integer id;

    protected String drawWay;

    protected CgExpertDto nameId;

    protected String tenantId;

    protected Integer expertDepartment;

    protected String expertLabel;

    protected Boolean isInternalExpert;

    protected String drawItem;

    protected Boolean noticeStatus;

    protected Integer IsAttend;

    protected String absentReason;

    protected String expertType;

    protected Integer expert;

    protected LocalDateTime biddingTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDrawWay() {
        return drawWay;
    }

    public void setDrawWay(String drawway) {
        this.drawWay = drawway;
    }


    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Integer getExpertDepartment() {
        return expertDepartment;
    }

    public void setExpertDepartment(Integer expertDepartment) {
        this.expertDepartment = expertDepartment;
    }

    public String getExpertLabel() {
        return expertLabel;
    }

    public void setExpertLabel(String expertLabel) {
        this.expertLabel = expertLabel;
    }

    public Boolean getInternalExpert() {
        return isInternalExpert;
    }

    public void setInternalExpert(Boolean internalExpert) {
        isInternalExpert = internalExpert;
    }

    public String getDrawItem() {
        return drawItem;
    }

    public void setDrawItem(String drawItem) {
        this.drawItem = drawItem;
    }

    public Boolean getNoticeStatus() {
        return noticeStatus;
    }

    public void setNoticeStatus(Boolean noticeStatus) {
        this.noticeStatus = noticeStatus;
    }

    public Integer getIsAttend() {
        return IsAttend;
    }

    public void setIsAttend(Integer isAttend) {
        IsAttend = isAttend;
    }

    public String getAbsentReason() {
        return absentReason;
    }

    public void setAbsentReason(String absentReason) {
        this.absentReason = absentReason;
    }

    public CgExpertDto getNameId() {
        return nameId;
    }

    public void setNameId(CgExpertDto nameId) {
        this.nameId = nameId;
    }

    public String getExpertType() {
        return expertType;
    }

    public void setExpertType(String expertType) {
        this.expertType = expertType;
    }

   /* public Integer getExpert() {
        return expert;
    }

    public void setExpert(Integer expert) {
        this.expert = expert;
    }*/

    public Integer getExpert() {
        return expert;
    }

    public void setExpert(Integer expert) {
        this.expert = expert;
    }

    public LocalDateTime getBiddingTime() {
        return biddingTime;
    }

    public void setBiddingTime(LocalDateTime biddingTime) {
        this.biddingTime = biddingTime;
    }
}
