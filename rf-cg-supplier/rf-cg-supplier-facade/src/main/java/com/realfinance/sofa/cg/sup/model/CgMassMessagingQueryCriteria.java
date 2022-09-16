package com.realfinance.sofa.cg.sup.model;

public class CgMassMessagingQueryCriteria {


    private String  msgTitleLike;

    private  Integer  sendStatus;

    public String getMsgTitleLike() {
        return msgTitleLike;
    }

    public void setMsgTitleLike(String msgTitleLike) {
        this.msgTitleLike = msgTitleLike;
    }

    public Integer getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(Integer sendStatus) {
        this.sendStatus = sendStatus;
    }
}
