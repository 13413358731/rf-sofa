package com.realfinance.sofa.cg.core.model;

public class CgMeetingQueryCriteria {

    /**
     * 会议主持人用户ID
     */
    private Integer meetingHostUserId;

    /**
     * 参会人员用户ID
     */
    private Integer meetingConfereeUserId;

    /**
     * 结束时间为NULL
     */
    private Boolean endTimeIsNull;

    private String nameLike;

    public String getNameLike() {
        return nameLike;
    }

    public void setNameLike(String nameLike) {
        this.nameLike = nameLike;
    }

    public Integer getMeetingHostUserId() {
        return meetingHostUserId;
    }

    public void setMeetingHostUserId(Integer meetingHostUserId) {
        this.meetingHostUserId = meetingHostUserId;
    }

    public Integer getMeetingConfereeUserId() {
        return meetingConfereeUserId;
    }

    public void setMeetingConfereeUserId(Integer meetingConfereeUserId) {
        this.meetingConfereeUserId = meetingConfereeUserId;
    }

    public Boolean getEndTimeIsNull() {
        return endTimeIsNull;
    }

    public void setEndTimeIsNull(Boolean endTimeIsNull) {
        this.endTimeIsNull = endTimeIsNull;
    }

}
