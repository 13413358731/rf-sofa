package com.realfinance.sofa.cg.core.domain.meeting;

import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "CG_CORE_MEETING_CHAT_RECORD")
public class MeetingChatRecord implements IEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    /**
     * 发送人用户ID
     */
    @Column(nullable = false)
    private Integer senderUserId;

    /**
     * 发送人显示名称
     */
    @Column(nullable = false)
    private String senderName;

    /**
     * 发送内容
     */
    @Column(nullable = false, length = 1000)
    private String content;

    /**
     * 发送时间
     */
    @Column(nullable = false, updatable = false)
    private LocalDateTime sendTime;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Meeting getMeeting() {
        return meeting;
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
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
}
