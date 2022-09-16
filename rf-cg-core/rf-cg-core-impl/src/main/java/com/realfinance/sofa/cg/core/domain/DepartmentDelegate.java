package com.realfinance.sofa.cg.core.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 部门委派
 */
@Entity
@Table(name = "CG_CORE_DEPARTMENTDELEGATE")
public class DepartmentDelegate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     *专家申请事由
     */
    @Column(nullable = false)
    private String applyEvent;

    /**
     *通知人
     */
    @Column(nullable = false)
    private String notifier;

    /**
     *通知人部门
     */
    @Column(nullable = false)
    private String notifierDepartment;

    /**
     *通知时间
     */
    @Column(nullable = false)
    private LocalDateTime noticeTime;

    /**
     *接收人
     */
    @Column(nullable = false)
    private String receiver;

    /**
     *接收人部门
     */
    @Column(nullable = false)
    private String receiverDepartment;

    /**
     *委派人数
     */
    @Column(nullable = false)
    private String delegateNumber;

    /**
     *委派人员类型
     */
    @Column(nullable = false)
    private String delegateType;

    /**
     *通知内容
     */
    @Column(nullable = false)
    private String noticeContent;

    /**
     *委派状态
     */
    @Column(nullable = false)
    private String delegateStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApplyEvent() {
        return applyEvent;
    }

    public void setApplyEvent(String applyEvent) {
        this.applyEvent = applyEvent;
    }

    public String getNotifier() {
        return notifier;
    }

    public void setNotifier(String notifier) {
        this.notifier = notifier;
    }

    public String getNotifierDepartment() {
        return notifierDepartment;
    }

    public void setNotifierDepartment(String notifierDepartment) {
        this.notifierDepartment = notifierDepartment;
    }

    public LocalDateTime getNoticeTime() {
        return noticeTime;
    }

    public void setNoticeTime(LocalDateTime noticeTime) {
        this.noticeTime = noticeTime;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReceiverDepartment() {
        return receiverDepartment;
    }

    public void setReceiverDepartment(String receiverDepartment) {
        this.receiverDepartment = receiverDepartment;
    }

    public String getDelegateNumber() {
        return delegateNumber;
    }

    public void setDelegateNumber(String delegateNumber) {
        this.delegateNumber = delegateNumber;
    }

    public String getDelegateType() {
        return delegateType;
    }

    public void setDelegateType(String delegateType) {
        this.delegateType = delegateType;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public String getDelegateStatus() {
        return delegateStatus;
    }

    public void setDelegateStatus(String delegateStatus) {
        this.delegateStatus = delegateStatus;
    }
}
