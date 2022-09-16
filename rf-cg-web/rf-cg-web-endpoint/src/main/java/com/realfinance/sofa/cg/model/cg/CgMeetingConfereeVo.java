package com.realfinance.sofa.cg.model.cg;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.realfinance.sofa.cg.model.system.UserVo;
import com.realfinance.sofa.common.model.IdentityObject;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "采购会议参会人员对象")
public class CgMeetingConfereeVo implements IdentityObject<Integer> {

    private Integer id;

    private UserVo userId;

    private Integer expertId;

    private String type;

    private Boolean online;

    private String signStatus;

    private Boolean signQual;

    private Boolean signResp;

    private String qualCommit;

    private String respCommit;

    private LocalDateTime qualCommitTime;

    private LocalDateTime respCommitTime;

    private Integer resolutionContent;

    private Boolean qualPass;

    private Boolean respPass;

    private LocalDateTime signInTime;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public UserVo getUserId() {
        return userId;
    }

    public void setUserId(UserVo userId) {
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

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
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
}
