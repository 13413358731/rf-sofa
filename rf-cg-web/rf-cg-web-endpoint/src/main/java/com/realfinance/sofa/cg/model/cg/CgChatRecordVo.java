package com.realfinance.sofa.cg.model.cg;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.realfinance.sofa.cg.model.BaseVo;
import com.realfinance.sofa.common.model.IdentityObject;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "采购会议对象")
public class CgChatRecordVo extends BaseVo implements IdentityObject<Integer> {

    private Integer id;

    private Integer senderUserId;

    private String senderName;

    private String content;

    private LocalDateTime sendTime;

    private String messageType;

    private Integer projectExecutionId;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSenderUserId() {
        return senderUserId;
    }

    public void setSenderUserId(Integer senderUserId) {
        this.senderUserId = senderUserId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getSendTime() {
        return sendTime;
    }

    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public Integer getProjectExecutionId() {
        return projectExecutionId;
    }

    public void setProjectExecutionId(Integer projectExecutionId) {
        this.projectExecutionId = projectExecutionId;
    }

    @Override
    public String toString() {
        return "CgChatRecordVo{" +
                "id=" + id +
                ", senderUserId=" + senderUserId +
                ", senderName='" + senderName + '\'' +
                ", content='" + content + '\'' +
                ", sendTime=" + sendTime +
                ", messageType='" + messageType + '\'' +
                ", projectExecutionId=" + projectExecutionId +
                '}';
    }
}
