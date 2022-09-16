package com.realfinance.sofa.cg.core.domain;

import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;

/**
 * 专家
 */
@Entity
@Table(name = "CG_CORE_DRAWEXPERTLIST")
public class DrawExpertList extends BaseEntity implements IEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     *抽取方式
     */
    @Column(nullable = false)
    private DrawExpertWay drawWay;

    /**
     *所属公司
     */
    @Column(nullable = false)
    private String tenantId;

    /**
     *所属部门
     */
    @Column(nullable = false)
    private Integer expertDepartment;

    /**
     *标签
     */
    @Column()
    private String expertLabel;

    /**
     *是否内部专家
     */
    @Column(nullable = false)
    private Boolean IsInternalExpert;

    /**
     *抽取条件
     */
    @Column()
    private String drawItem;

    /**
     *通知状态
     */
    @Column
    private Boolean noticeStatus;

    /**
     *能否参加 1参加2不参加0未确认
     */
    @Column()
    private Integer IsAttend;

    /**
     *缺席原因
     */
    @Column()
    private String absentReason;

    /**
     *参与类型
     */
    @Column(nullable = false)
    private ExpertType expertType;

    @ManyToOne
    @JoinColumn(name = "expert_id", nullable = false)
    private Expert expert;

    @ManyToOne
    @JoinColumn(name = "drawExpert_id", nullable = false)
    private DrawExpert drawExpert;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DrawExpertWay getDrawWay() {
        return drawWay;
    }

    public void setDrawWay(DrawExpertWay drawWay) {
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

    public ExpertType getExpertType() {
        return expertType;
    }

    public void setExpertType(ExpertType expertType) {
        this.expertType = expertType;
    }

    public Expert getExpert() {
        return expert;
    }

    public void setExpert(Expert expert) {
        this.expert = expert;
    }

    public DrawExpert getDrawExpert() {
        return drawExpert;
    }

    public void setDrawExpert(DrawExpert drawExpert) {
        this.drawExpert = drawExpert;
    }
}
