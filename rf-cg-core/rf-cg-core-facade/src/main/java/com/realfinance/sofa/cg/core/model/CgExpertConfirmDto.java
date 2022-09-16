package com.realfinance.sofa.cg.core.model;

import java.time.LocalDateTime;

public class CgExpertConfirmDto extends BaseDto {

    private Integer id;

    /**
     *专家抽取事由
     */
    private String event;

    /**
     *会议类别
     */
    private String sort;

    /**
     *通知内容
     */
    private String noticeContent;

    /**
     *不参加原因
     */
    private String absentReason;

    /**
     *发送机构
     */
    private String tenantId;

    /**
     *发送部门
     */
    private Integer departmentId;

    /**
     *发送人
     */
    private Integer drawer;

    /**
     *确认状态
     */
    private Integer confirmStatus;

    /**
     *确认时间
     */
    private LocalDateTime confirmTime;

    /**
     *通知的专家(使用userId)
     */
    private Integer expertUserId;

    /**
     *专家抽取列表Id
     */
    private Integer expertListId;

    /**
     * 项目编号
     */
    private Integer projectId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public String getAbsentReason() {
        return absentReason;
    }

    public void setAbsentReason(String absentReason) {
        this.absentReason = absentReason;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getDrawer() {
        return drawer;
    }

    public void setDrawer(Integer drawer) {
        this.drawer = drawer;
    }

    public Integer getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(Integer confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    public LocalDateTime getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(LocalDateTime confirmTime) {
        this.confirmTime = confirmTime;
    }

    public Integer getExpertUserId() {
        return expertUserId;
    }

    public void setExpertUserId(Integer expertUserId) {
        this.expertUserId = expertUserId;
    }

    public Integer getExpertListId() {
        return expertListId;
    }

    public void setExpertListId(Integer expertListId) {
        this.expertListId = expertListId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}
