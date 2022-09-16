package com.realfinance.sofa.cg.core.model;

import java.time.LocalDateTime;
import java.util.List;

public class CgDrawExpertListDto extends BaseDto {

    private Integer id;

    /**
     *抽取方式
     */
    private String drawWay;

    /**
     *专家姓名
     */
    private CgExpertDto nameId;

    /**
     *所属公司
     */
    private String tenantId;

    /**
     *所属部门
     */
    private Integer expertDepartment;

    /**
     *标签
     */
    private String expertLabel;

    /**
     *是否内部专家
     */
    private Boolean IsInternalExpert;

    /**
     *抽取条件
     */
    private String drawItem;

    /**
     *通知状态
     */
    private Boolean noticeStatus;

    /**
     *能否参加
     */
    private Integer IsAttend;

    /**
     *缺席原因
     */
    private String absentReason;

    /**
     *参与类型
     */
    private String expertType;

    protected CgExpertDetailsDto expert;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDrawWay() {
        return drawWay;
    }

    public void setDrawWay(String drawWay) {
        this.drawWay = drawWay;
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
        return IsInternalExpert;
    }

    public void setInternalExpert(Boolean internalExpert) {
        IsInternalExpert = internalExpert;
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

   /* public CgExpertDto getExpert() {
        return expert;
    }

    public void setExpert(CgExpertDto expert) {
        this.expert = expert;
    }*/

    public CgExpertDetailsDto getExpert() {
        return expert;
    }

    public void setExpert(CgExpertDetailsDto expert) {
        this.expert = expert;
    }
}
