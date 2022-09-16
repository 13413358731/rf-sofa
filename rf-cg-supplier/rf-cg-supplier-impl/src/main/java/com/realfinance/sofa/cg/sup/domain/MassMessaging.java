package com.realfinance.sofa.cg.sup.domain;

import com.realfinance.sofa.common.model.IEntity;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 群发消息
 */
@Entity
@Table(name = "CG_SUP_SUPPLIER_MASS_MESSAGING")
public class MassMessaging extends BaseEntity implements IEntity<Integer> {

    /**
     * 法人（租户）
     */
    @Column(nullable = false, updatable = false)
    private String tenantId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    /**
     * 短信通知
     */
    private Boolean smsNotice;


    /**
     * 邮件通知
     */
    private Boolean emailNotice;



    /**
     * 消息标题
     */
    @Column(nullable = false)
    private String  msgTitle;

    /**
     * 消息内容
     */
    @Column(length = 1024,nullable = false)
    private String  msgContent;


    /**
     * 发送状态 未发送：0 发送：1
     */
    private Integer sendStatus;


    /**
     * 发送时间
     */
    private LocalDateTime sendTime;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name="messaging_id")
    private List<MassSupContact>  massSupContacts;


    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getSmsNotice() {
        return smsNotice;
    }

    public void setSmsNotice(Boolean smsNotice) {
        this.smsNotice = smsNotice;
    }

    public Boolean getEmailNotice() {
        return emailNotice;
    }

    public void setEmailNotice(Boolean emailNotice) {
        this.emailNotice = emailNotice;
    }

    public String getMsgTitle() {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public Integer getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(Integer sendStatus) {
        this.sendStatus = sendStatus;
    }


    public LocalDateTime getSendTime() {
        return sendTime;
    }

    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }

    public List<MassSupContact> getMassSupContacts() {
        return massSupContacts;
    }

    public void setMassSupContacts(List<MassSupContact> massSupContacts) {
        this.massSupContacts = massSupContacts;
    }
}
