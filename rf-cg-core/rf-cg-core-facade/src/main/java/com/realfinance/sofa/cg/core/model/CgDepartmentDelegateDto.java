package com.realfinance.sofa.cg.core.model;

import java.time.LocalDateTime;

public class CgDepartmentDelegateDto extends BaseDto {

    private Integer id;

    /**
     *单据编号
     */
    private String receipt;

    /**
     *专家抽取事由
     */
    private String event;

    /**
     *会议类别
     */
    private String sort;

    /**
     *参会人数
     */
    private Integer meetingNumber;

    /**
     *抽取人
     */
    private Integer drawNumber;

    /**
     * 制单人
     */
    private Integer createdUserId;

    /**
     *制单人公司
     */
    private String tenantId;

    /**
     * 制单人部门
     */
    private Integer departmentId;

    /**
     * 抽取日期
     */
    private LocalDateTime drawTime;

    /**
     *制单人联系电话
     */
    private String phone;

    /**
     *专家组成描述
     */
    private String description;

    /**
     *通知专家
     */
    private String noticeExpert;

    /**
     *通知部门
     */
    private String noticeDepartment;

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

    @Override
    public Integer getCreatedUserId() {
        return createdUserId;
    }

    @Override
    public void setCreatedUserId(Integer createdUserId) {
        this.createdUserId = createdUserId;
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

    public String getNoticeDepartment() {
        return noticeDepartment;
    }

    public void setNoticeDepartment(String noticeDepartment) {
        this.noticeDepartment = noticeDepartment;
    }
}
