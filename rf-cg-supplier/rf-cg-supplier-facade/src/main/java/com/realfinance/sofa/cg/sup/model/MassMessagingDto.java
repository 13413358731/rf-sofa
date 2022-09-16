package com.realfinance.sofa.cg.sup.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class MassMessagingDto {


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
    private String  msgTitle;

    /**
     * 消息内容
     */
    private String  msgContent;


    /**
     * 发送状态 未发送：0 发送：1
     */
    private Integer sendStatus;


    /**
     * 发送时间
     */
    private LocalDateTime sendTime;


    private List<massSupContactsDto> massSupContacts;

    public Integer getId() {
        return id;
    }

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

    public List<massSupContactsDto> getMassSupContacts() {
        return massSupContacts;
    }

    public void setMassSupContacts(List<massSupContactsDto> massSupContacts) {
        this.massSupContacts = massSupContacts;
    }
}
