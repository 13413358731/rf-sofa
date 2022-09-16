package com.realfinance.sofa.cg.model.flow;

import com.realfinance.sofa.cg.model.system.UserVo;
import com.realfinance.sofa.common.model.IdentityObject;

import java.time.LocalDateTime;
import java.util.Date;

public class TaskVo implements IdentityObject<String> {

    protected String id;

    protected String name;

    protected String taskDefinitionKey;

    protected String executionId;

    protected UserVo assignee;

    protected LocalDateTime createTime;

    protected LocalDateTime endTime;

    protected LocalDateTime dueDate;

    protected LocalDateTime claimTime;

    protected Integer priority;

    protected UserVo owner;

    protected String delegateStatus;

    // 业务数据是否可编辑
    protected Boolean businessDataEditable;

    protected ProcessInstanceVo processInstance;

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

    public String getTaskDefinitionKey() {
        return taskDefinitionKey;
    }

    public void setTaskDefinitionKey(String taskDefinitionKey) {
        this.taskDefinitionKey = taskDefinitionKey;
    }

    public String getExecutionId() {
        return executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    public UserVo getAssignee() {
        return assignee;
    }

    public void setAssignee(UserVo assignee) {
        this.assignee = assignee;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDateTime getClaimTime() {
        return claimTime;
    }

    public void setClaimTime(LocalDateTime claimTime) {
        this.claimTime = claimTime;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public UserVo getOwner() {
        return owner;
    }

    public void setOwner(UserVo owner) {
        this.owner = owner;
    }

    public String getDelegateStatus() {
        return delegateStatus;
    }

    public void setDelegateStatus(String delegateStatus) {
        this.delegateStatus = delegateStatus;
    }

    public Boolean getBusinessDataEditable() {
        return businessDataEditable;
    }

    public void setBusinessDataEditable(Boolean businessDataEditable) {
        this.businessDataEditable = businessDataEditable;
    }

    public ProcessInstanceVo getProcessInstance() {
        return processInstance;
    }

    public void setProcessInstance(ProcessInstanceVo processInstance) {
        this.processInstance = processInstance;
    }
}
