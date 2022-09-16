package com.realfinance.sofa.cg.core.model;

import java.time.LocalDateTime;

public class CgMeetingConfereeDto {

    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 专家ID
     */
    private Integer expertId;

    private String type;

    private String online;

    /**
     * 签字状态
     */
    private String signStatus;

    private Boolean signQual;

    private Boolean signResp;

    private String qualCommit;

    private String respCommit;

    private LocalDateTime qualCommitTime;

    private LocalDateTime respCommitTime;

    private Boolean qualPass;

    private Boolean respPass;

    private LocalDateTime signInTime;

    private Integer resolutionContent;

    /**
     *抽取方式
     */
    private String drawWay;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getExpertId() {
        return expertId;
    }

    public void setExpertId(Integer expertId) {
        this.expertId = expertId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public String getSignStatus() {
        return signStatus;
    }

    public void setSignStatus(String signStatus) {
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

    public String getQualCommit() {
        return qualCommit;
    }

    public void setQualCommit(String qualCommit) {
        this.qualCommit = qualCommit;
    }

    public String getRespCommit() {
        return respCommit;
    }

    public void setRespCommit(String respCommit) {
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

    public String getDrawWay() {
        return drawWay;
    }

    public void setDrawWay(String drawWay) {
        this.drawWay = drawWay;
    }
}
