package com.realfinance.sofa.flow.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TaskDto implements Serializable {

    private String id;

    private String name;

    private String taskDefinitionKey;

    private String executionId;

    private Integer assignee;

    private Date createTime;

    private Date endTime;

    private Date dueDate;

    private Date claimTime;

    private Integer priority;

    private Integer owner;

    private String delegateStatus;

    private ProcessInstanceDto processInstance;

    private Boolean businessDataEditable;

    private List<CommentDto> comments;

    public String getId() {
        return id;
    }

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

    public Integer getAssignee() {
        return assignee;
    }

    public void setAssignee(Integer assignee) {
        this.assignee = assignee;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getClaimTime() {
        return claimTime;
    }

    public void setClaimTime(Date claimTime) {
        this.claimTime = claimTime;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public String getDelegateStatus() {
        return delegateStatus;
    }

    public void setDelegateStatus(String delegateStatus) {
        this.delegateStatus = delegateStatus;
    }

    public ProcessInstanceDto getProcessInstance() {
        return processInstance;
    }

    public void setProcessInstance(ProcessInstanceDto processInstance) {
        this.processInstance = processInstance;
    }

    public Boolean getBusinessDataEditable() {
        return businessDataEditable;
    }

    public void setBusinessDataEditable(Boolean businessDataEditable) {
        this.businessDataEditable = businessDataEditable;
    }

    public List<CommentDto> getComments() {
        return comments;
    }

    public void setComments(List<CommentDto> comments) {
        this.comments = comments;
    }
}
