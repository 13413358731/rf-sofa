package com.realfinance.sofa.cg.core.domain.meeting;

import com.realfinance.sofa.cg.core.domain.DrawExpertWay;
import com.realfinance.sofa.cg.core.domain.ExpertType;
import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 参会人员
 */
@Entity
@Table(name = "CG_CORE_MEETING_CONFEREE")
public class MeetingConferee implements IEntity<Integer> {

    /**
     * 参会人员类型
     */
    public enum Type {
        CGJBR("采购经办人"),
        HGBJDR("合规部监督人"),
        JWJDR("纪委监督人"),
        PBZJ("评标专家");

        String zh;

        Type(String zh) {
            this.zh = zh;
        }

        public String getZh() {
            return zh;
        }
    }

    /**
     * 签字状态
     */
    public enum SignStatus {
        WBJ("未表决"),
        TY("同意"),
        BTY("不同意");

        String zh;

        SignStatus(String zh) {
            this.zh = zh;
        }

        public String getZh() {
            return zh;
        }
    }

    /**
     * 签字状态
     */
    public enum CommitStatus {
        WTJ("未提交"),
        YTJ("已提交"),
        YHZ("已汇总");

        String zh;

        CommitStatus(String zh) {
            this.zh = zh;
        }

        public String getZh() {
            return zh;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 专家ID
     */
    private Integer expertId;

    /**
     * 采购方案ID
     */
    private Integer projectId;

    /**
     * 关联会议
     */
    @ManyToOne
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    @Column
    @Enumerated
    private ExpertType type;

    /**
     * 是否在线
     */
    @Column
    private Boolean online;

    /**
     * 签字状态
     */
    @Column
    @Enumerated
    private SignStatus signStatus;

    /**
     * 资格性审查签名否
     */
    @Column
    private Boolean signQual;

    /**
     * 响应性审查签名否
     */
    @Column
    private Boolean signResp;

    /**
     * 资格性审查提交状态
     */
    @Column
    @Enumerated
    private CommitStatus qualCommit;

    /**
     * 资格性审查提交时间
     */
    @Column
    private LocalDateTime qualCommitTime;

    /**
     * 响应性审查提交状态
     */
    @Column
    @Enumerated
    private CommitStatus respCommit;

    /**
     * 响应性审查提交时间
     */
    @Column
    private LocalDateTime respCommitTime;

    /**
     * 决议意见是否同意
     */
    @Column
    private Integer resolutionContent;

    @Column
    private Boolean qualPass;

    @Column
    private Boolean respPass;

    /**
     * 是否签到
     */
    @Column
    private LocalDateTime signInTime;

    /**
     * 抽取方式
     */
    @Column(nullable = false)
    private DrawExpertWay drawWay;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getExpertId() {
        return expertId;
    }

    public void setExpertId(Integer expertId) {
        this.expertId = expertId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Meeting getMeeting() {
        return meeting;
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }

    public ExpertType getType() {
        return type;
    }

    public void setType(ExpertType type) {
        this.type = type;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public SignStatus getSignStatus() {
        return signStatus;
    }

    public void setSignStatus(SignStatus signStatus) {
        this.signStatus = signStatus;
    }

    public Boolean getSignQual() {
        return signQual;
    }

    public void setSignQual(Boolean signQual) {
        this.signQual = signQual;
    }

    public Boolean getSignResp() {
        return signResp;
    }

    public void setSignResp(Boolean signResp) {
        this.signResp = signResp;
    }

    public CommitStatus getQualCommit() {
        return qualCommit;
    }

    public void setQualCommit(CommitStatus qualCommit) {
        this.qualCommit = qualCommit;
    }

    public CommitStatus getRespCommit() {
        return respCommit;
    }

    public void setRespCommit(CommitStatus respCommit) {
        this.respCommit = respCommit;
    }

    public LocalDateTime getQualCommitTime() {
        return qualCommitTime;
    }

    public void setQualCommitTime(LocalDateTime qualCommitTime) {
        this.qualCommitTime = qualCommitTime;
    }

    public LocalDateTime getRespCommitTime() {
        return respCommitTime;
    }

    public void setRespCommitTime(LocalDateTime respCommitTime) {
        this.respCommitTime = respCommitTime;
    }

    public Integer getResolutionContent() {
        return resolutionContent;
    }

    public void setResolutionContent(Integer resolutionContent) {
        this.resolutionContent = resolutionContent;
    }

    public Boolean getQualPass() {
        return qualPass;
    }

    public void setQualPass(Boolean qualPass) {
        this.qualPass = qualPass;
    }

    public Boolean getRespPass() {
        return respPass;
    }

    public void setRespPass(Boolean respPass) {
        this.respPass = respPass;
    }

    public LocalDateTime getSignInTime() {
        return signInTime;
    }

    public void setSignInTime(LocalDateTime signInTime) {
        this.signInTime = signInTime;
    }

    public DrawExpertWay getDrawWay() {
        return drawWay;
    }

    public void setDrawWay(DrawExpertWay drawWay) {
        this.drawWay = drawWay;
    }
}
