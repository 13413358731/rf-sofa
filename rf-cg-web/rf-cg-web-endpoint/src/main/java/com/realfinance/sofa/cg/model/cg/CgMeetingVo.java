package com.realfinance.sofa.cg.model.cg;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.realfinance.sofa.cg.model.BaseVo;
import com.realfinance.sofa.common.model.IdentityObject;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "采购会议对象")
public class CgMeetingVo extends BaseVo implements IdentityObject<Integer> {
    public interface Save { }

    private Integer id;

    private String name;

    private String meetingRoom;

    /**
     * 主持人Id
     */
    private Integer meetingHostUserId;

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
    private CgProjectVo projId;

    /**
     * 采购方案执行
     */
    private CgProjectExecutionVo projectExecution;

    private List<CgMeetingConfereeVo> conferees;

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

    /**
     * 供应商报价信息， 动态表单
     */
    @Schema(description = "报价信息")
    private Object replyInfo;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeetingRoom() {
        return meetingRoom;
    }

    public void setMeetingRoom(String meetingRoom) {
        this.meetingRoom = meetingRoom;
    }

    public Integer getMeetingHostUserId() {
        return meetingHostUserId;
    }

    public void setMeetingHostUserId(Integer meetingHostUserId) {
        this.meetingHostUserId = meetingHostUserId;
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

    public CgProjectVo getProjId() {
        return projId;
    }

    public void setProjId(CgProjectVo projId) {
        this.projId = projId;
    }

    public CgProjectExecutionVo getProjectExecution() {
        return projectExecution;
    }

    public void setProjectExecution(CgProjectExecutionVo projectExecution) {
        this.projectExecution = projectExecution;
    }

    public List<CgMeetingConfereeVo> getConferees() {
        return conferees;
    }

    public void setConferees(List<CgMeetingConfereeVo> conferees) {
        this.conferees = conferees;
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

    public Object getReplyInfo() {
        return replyInfo;
    }

    public void setReplyInfo(Object replyInfo) {
        this.replyInfo = replyInfo;
    }
}
