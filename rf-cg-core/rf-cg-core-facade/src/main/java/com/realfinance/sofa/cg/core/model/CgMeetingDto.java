package com.realfinance.sofa.cg.core.model;

import java.time.LocalDateTime;

public class CgMeetingDto extends BaseDto {
    private Integer id;

    private String name;

    private Integer meetingHostUserId;

    /**
     * 会议决定
     */
    private String meetingDecision;

    /**
     * 会议室号
     */
    private String meetingRoom;

    /**
     * 会议时间
     */
    private LocalDateTime meetingTime;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 采购方案
     */
    private Integer projId;

    /**
     * 采购方案执行
     */
    private Integer projectExecution;

    /**
     * 文件状态
     */
    private String fileStatus;

    /**
     * 价格标状态
     */
    private Boolean filePrice;

    /**
     * 技术商务标状态
     */
    private Boolean fileTecBusiness;

    /**
     * 审查生成状态
     */
    private Boolean isAudited;

    /**
     * 会议决议
     */
    private String resolutionContent;

    /**
     * 开启会议评分
     */
    private Boolean isGraded;

    /**
     * 完成评分(表决)汇总
     */
    private Boolean finishGrade;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMeetingHostUserId() {
        return meetingHostUserId;
    }

    public void setMeetingHostUserId(Integer meetingHostUserId) {
        this.meetingHostUserId = meetingHostUserId;
    }

    public String getMeetingDecision() {
        return meetingDecision;
    }

    public void setMeetingDecision(String meetingDecision) {
        this.meetingDecision = meetingDecision;
    }

    public String getMeetingRoom() {
        return meetingRoom;
    }

    public void setMeetingRoom(String meetingRoom) {
        this.meetingRoom = meetingRoom;
    }

    public LocalDateTime getMeetingTime() {
        return meetingTime;
    }

    public void setMeetingTime(LocalDateTime meetingTime) {
        this.meetingTime = meetingTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Integer getProjId() {
        return projId;
    }

    public void setProjId(Integer projId) {
        this.projId = projId;
    }

    public Integer getProjectExecution() {
        return projectExecution;
    }

    public void setProjectExecution(Integer projectExecution) {
        this.projectExecution = projectExecution;
    }

    public String getFileStatus() {
        return fileStatus;
    }

    public void setFileStatus(String fileStatus) {
        this.fileStatus = fileStatus;
    }

    public Boolean getFilePrice() {
        return filePrice;
    }

    public void setFilePrice(Boolean filePrice) {
        this.filePrice = filePrice;
    }

    public Boolean getFileTecBusiness() {
        return fileTecBusiness;
    }

    public void setFileTecBusiness(Boolean fileTecBusiness) {
        this.fileTecBusiness = fileTecBusiness;
    }

    public Boolean getAudited() {
        return isAudited;
    }

    public void setAudited(Boolean audited) {
        isAudited = audited;
    }

    public String getResolutionContent() {
        return resolutionContent;
    }

    public void setResolutionContent(String resolutionContent) {
        this.resolutionContent = resolutionContent;
    }

    public Boolean getGraded() {
        return isGraded;
    }

    public void setGraded(Boolean graded) {
        isGraded = graded;
    }

    public Boolean getFinishGrade() {
        return finishGrade;
    }

    public void setFinishGrade(Boolean finishGrade) {
        this.finishGrade = finishGrade;
    }
}
