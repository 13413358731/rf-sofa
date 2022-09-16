package com.realfinance.sofa.cg.core.domain.meeting;

import com.realfinance.sofa.cg.core.domain.BaseEntity;
import com.realfinance.sofa.cg.core.domain.DrawExpertList;
import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * 会议
 */
@Entity
@Table(name = "CG_CORE_MEETING")
public class Meeting extends BaseEntity implements IEntity<Integer> {

//    public enum FileStatus {
//        // 技术商务文件
//        SW,
//        // 价格文件
//        JG,
//        // 未开启
//        WKQ,
//        //全开启
//        ALL
//    }

    @Version
    private Long v;

    /**
     * 法人ID
     */
    @Column(nullable = false)
    private String tenantId;

    /**
     * 部门ID
     */
    @Column(nullable = false)
    private Integer departmentId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 会议名称
     */
    @Column(nullable = false)
    private String name;

    /**
     * 会议主持人
     */
    @Column(nullable = false)
    private Integer meetingHostUserId;

    /**
     * 会议决定
     */
    @Column
    private String meetingDecision;

    /**
     * 会议室号
     */
    @Column()
    private String meetingRoom;

    /**
     * 会议时间
     */
    private LocalDateTime meetingTime;

    /**
     * 开始时间
     */
    @Column
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @Column
    private LocalDateTime endTime;

    /**
     * 采购方案ID
     */
    @Column
    private Integer projId;

    /**
     * 采购方案执行
     */
    @Column
    private Integer projectExecution;

    /**
     * 价格标状态
     */
    @Column
    private Boolean filePrice;

    /**
     * 技术商务标状态
     */
    @Column
    private Boolean fileTecBusiness;

    /**
     * 审查生成状态
     */
    @Column
    private Boolean isAudited;

    /**
     * 会议决议
     */
    @Column
    private String resolutionContent;

    /**
     * 开启会议评分
     */
    @Column
    private Boolean isGraded;

    /**
     * 完成评分(表决)汇总
     */
    @Column
    private Boolean finishGrade;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "meeting_id")
    private List<MeetingConferee> conferees;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "meeting_id")
    private List<MeetingChatRecord> meetingChatRecords;

    /**
     * 会议已开始
     * @return
     */
    public boolean getMeetingStarted() {
        return !Objects.isNull(startTime);
    }

    /**
     * 会议已结束
     * @return
     */
    public boolean getMeetingEnded() {
        return !Objects.isNull(endTime);
    }

    public Long getV() {
        return v;
    }

    public void setV(Long v) {
        this.v = v;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

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

    public Boolean getFilePrice() {
        return filePrice;
    }

    public void setFilePrice(Boolean filePrice) {
        this.filePrice = filePrice;
    }

    public Boolean getAudited() {
        return isAudited;
    }

    public void setAudited(Boolean audited) {
        isAudited = audited;
    }

    public Boolean getFileTecBusiness() {
        return fileTecBusiness;
    }

    public void setFileTecBusiness(Boolean fileTecBusiness) {
        this.fileTecBusiness = fileTecBusiness;
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

    public List<MeetingConferee> getConferees() {
        return conferees;
    }

    public void setConferees(List<MeetingConferee> conferees) {
        this.conferees = conferees;
    }

    public String getResolutionContent() {
        return resolutionContent;
    }

    public void setResolutionContent(String resolutionContent) {
        this.resolutionContent = resolutionContent;
    }
}
