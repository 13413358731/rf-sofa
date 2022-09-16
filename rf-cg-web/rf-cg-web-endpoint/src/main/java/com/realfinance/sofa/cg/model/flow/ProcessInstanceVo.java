package com.realfinance.sofa.cg.model.flow;

import com.realfinance.sofa.cg.model.system.UserVo;
import com.realfinance.sofa.common.model.IdentityObject;

import java.time.LocalDateTime;

// TODO: 2021/3/10 添加流程定义对象
public class ProcessInstanceVo implements IdentityObject<String> {
    private String id;

    private String name;

    private String businessKey;

    private UserVo startUser;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String endActivityId;

    private Boolean suspended;

    private String referenceType;

    private String referenceId;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public UserVo getStartUser() {
        return startUser;
    }

    public void setStartUser(UserVo startUser) {
        this.startUser = startUser;
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

    public String getEndActivityId() {
        return endActivityId;
    }

    public void setEndActivityId(String endActivityId) {
        this.endActivityId = endActivityId;
    }

    public Boolean getSuspended() {
        return suspended;
    }

    public void setSuspended(Boolean suspended) {
        this.suspended = suspended;
    }

    public String getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }
}
