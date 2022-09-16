package com.realfinance.sofa.cg.core.model;

public class CgConfereeQueryCriteria {

    /**
     * 专家类型
     */
    private String type;

    /**
     * 会议Id
     */
    private Integer meetingId;

    /**
     * 用户Id
     */
    private Integer userId;

    /**
     * 采购方案Id
     */
    private Integer projectId;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Integer meetingId) {
        this.meetingId = meetingId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}
