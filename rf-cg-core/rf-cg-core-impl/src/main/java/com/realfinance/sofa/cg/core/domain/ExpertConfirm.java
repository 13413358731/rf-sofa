package com.realfinance.sofa.cg.core.domain;

import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 专家确认
 */
@Entity
@Table(name = "CG_CORE_EXPERTCOMFIRM")
public class ExpertConfirm extends BaseEntity implements IEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     *专家抽取事由
     */
    @Column(nullable = false)
    private String event;

    /**
     *会议类别
     */
    @Column(nullable = false)
    private String sort;

    /**
     *通知内容
     */
    @Column(nullable = false)
    private String noticeContent;

    /**
     *不参加原因
     */
    @Column()
    private String absentReason;

    /**
     * 制单人(抽取人)
     */
    @Column(nullable = false)
    private Integer drawer;

    /**
     *制单人公司
     */
    @Column(nullable = false)
    private String tenantId;

    /**
     * 制单人部门
     */
    @Column(nullable = false)
    private Integer departmentId;

    /**
     *确认状态
     */
    @Column()
    private Integer confirmStatus;

    /**
     *确认时间
     */
    @Column()
    private LocalDateTime confirmTime;

    /**
     * 通知的专家（使用userId）
     */
    @Column(nullable = false)
    private Integer expertUserId;

    /**
     * 专家抽取列表Id
     */
    @Column(nullable = false)
    private Integer expertListId;

    /**
     * 项目编号
     */
    @Column(nullable = false)
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

    public Integer getDrawer() {
        return drawer;
    }

    public void setDrawer(Integer drawer) {
        this.drawer = drawer;
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
