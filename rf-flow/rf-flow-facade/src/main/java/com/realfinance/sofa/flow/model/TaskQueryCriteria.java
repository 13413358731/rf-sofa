package com.realfinance.sofa.flow.model;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public class TaskQueryCriteria {

    private String name;
    private String nameLike;
    private String description;
    private String descriptionLike;
    private Integer priority;
    private Integer minimumPriority;
    private Integer maximumPriority;
    private String assignee;
    private String assigneeLike;
    private String owner;
    private String ownerLike;
    private Boolean unassigned;
    private String delegationState;
    private String candidateUser;
    private String candidateGroup;
    private List<String> candidateGroupIn;
    private boolean ignoreAssignee;
    private String involvedUser;
    private String processInstanceId;
    private String processInstanceIdWithChildren;
    private String processInstanceBusinessKey;
    private String processInstanceBusinessKeyLike;
    private String processDefinitionId;
    private String processDefinitionKey;
    private String processDefinitionName;
    private String processDefinitionKeyLike;
    private String processDefinitionNameLike;
    private String executionId;
    private Date createdOn;
    private Date createdBefore;
    private Date createdAfter;
    private Boolean excludeSubTasks;
    private String taskDefinitionKey;
    private String taskDefinitionKeyLike;
    private Collection<String> taskDefinitionKeys;
    private Date dueDate;
    private Date dueBefore;
    private Date dueAfter;
    private Boolean withoutDueDate;
    private Boolean active;
    private Boolean includeTaskLocalVariables;
    private Boolean includeProcessVariables;
    private String scopeDefinitionId;
    private String scopeId;
    private String scopeType;
    private String tenantId;
    private String tenantIdLike;
    private Boolean withoutTenantId;
    private String candidateOrAssigned;
    private String category;
    //业务编码(移动审批用到)
    private String referenceIds;

    private List<String> roles;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameLike() {
        return nameLike;
    }

    public void setNameLike(String nameLike) {
        this.nameLike = nameLike;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionLike() {
        return descriptionLike;
    }

    public void setDescriptionLike(String descriptionLike) {
        this.descriptionLike = descriptionLike;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getMinimumPriority() {
        return minimumPriority;
    }

    public void setMinimumPriority(Integer minimumPriority) {
        this.minimumPriority = minimumPriority;
    }

    public Integer getMaximumPriority() {
        return maximumPriority;
    }

    public void setMaximumPriority(Integer maximumPriority) {
        this.maximumPriority = maximumPriority;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getAssigneeLike() {
        return assigneeLike;
    }

    public void setAssigneeLike(String assigneeLike) {
        this.assigneeLike = assigneeLike;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwnerLike() {
        return ownerLike;
    }

    public void setOwnerLike(String ownerLike) {
        this.ownerLike = ownerLike;
    }

    public Boolean getUnassigned() {
        return unassigned;
    }

    public void setUnassigned(Boolean unassigned) {
        this.unassigned = unassigned;
    }

    public String getDelegationState() {
        return delegationState;
    }

    public void setDelegationState(String delegationState) {
        this.delegationState = delegationState;
    }

    public String getCandidateUser() {
        return candidateUser;
    }

    public void setCandidateUser(String candidateUser) {
        this.candidateUser = candidateUser;
    }

    public String getCandidateGroup() {
        return candidateGroup;
    }

    public void setCandidateGroup(String candidateGroup) {
        this.candidateGroup = candidateGroup;
    }

    public List<String> getCandidateGroupIn() {
        return candidateGroupIn;
    }

    public void setCandidateGroupIn(List<String> candidateGroupIn) {
        this.candidateGroupIn = candidateGroupIn;
    }

    public boolean isIgnoreAssignee() {
        return ignoreAssignee;
    }

    public void setIgnoreAssignee(boolean ignoreAssignee) {
        this.ignoreAssignee = ignoreAssignee;
    }

    public String getInvolvedUser() {
        return involvedUser;
    }

    public void setInvolvedUser(String involvedUser) {
        this.involvedUser = involvedUser;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getProcessInstanceIdWithChildren() {
        return processInstanceIdWithChildren;
    }

    public void setProcessInstanceIdWithChildren(String processInstanceIdWithChildren) {
        this.processInstanceIdWithChildren = processInstanceIdWithChildren;
    }

    public String getProcessInstanceBusinessKey() {
        return processInstanceBusinessKey;
    }

    public void setProcessInstanceBusinessKey(String processInstanceBusinessKey) {
        this.processInstanceBusinessKey = processInstanceBusinessKey;
    }

    public String getProcessInstanceBusinessKeyLike() {
        return processInstanceBusinessKeyLike;
    }

    public void setProcessInstanceBusinessKeyLike(String processInstanceBusinessKeyLike) {
        this.processInstanceBusinessKeyLike = processInstanceBusinessKeyLike;
    }

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    public String getProcessDefinitionKey() {
        return processDefinitionKey;
    }

    public void setProcessDefinitionKey(String processDefinitionKey) {
        this.processDefinitionKey = processDefinitionKey;
    }

    public String getProcessDefinitionName() {
        return processDefinitionName;
    }

    public void setProcessDefinitionName(String processDefinitionName) {
        this.processDefinitionName = processDefinitionName;
    }

    public String getProcessDefinitionKeyLike() {
        return processDefinitionKeyLike;
    }

    public void setProcessDefinitionKeyLike(String processDefinitionKeyLike) {
        this.processDefinitionKeyLike = processDefinitionKeyLike;
    }

    public String getProcessDefinitionNameLike() {
        return processDefinitionNameLike;
    }

    public void setProcessDefinitionNameLike(String processDefinitionNameLike) {
        this.processDefinitionNameLike = processDefinitionNameLike;
    }

    public String getExecutionId() {
        return executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getCreatedBefore() {
        return createdBefore;
    }

    public void setCreatedBefore(Date createdBefore) {
        this.createdBefore = createdBefore;
    }

    public Date getCreatedAfter() {
        return createdAfter;
    }

    public void setCreatedAfter(Date createdAfter) {
        this.createdAfter = createdAfter;
    }

    public Boolean getExcludeSubTasks() {
        return excludeSubTasks;
    }

    public void setExcludeSubTasks(Boolean excludeSubTasks) {
        this.excludeSubTasks = excludeSubTasks;
    }

    public String getTaskDefinitionKey() {
        return taskDefinitionKey;
    }

    public void setTaskDefinitionKey(String taskDefinitionKey) {
        this.taskDefinitionKey = taskDefinitionKey;
    }

    public String getTaskDefinitionKeyLike() {
        return taskDefinitionKeyLike;
    }

    public void setTaskDefinitionKeyLike(String taskDefinitionKeyLike) {
        this.taskDefinitionKeyLike = taskDefinitionKeyLike;
    }

    public Collection<String> getTaskDefinitionKeys() {
        return taskDefinitionKeys;
    }

    public void setTaskDefinitionKeys(Collection<String> taskDefinitionKeys) {
        this.taskDefinitionKeys = taskDefinitionKeys;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getDueBefore() {
        return dueBefore;
    }

    public void setDueBefore(Date dueBefore) {
        this.dueBefore = dueBefore;
    }

    public Date getDueAfter() {
        return dueAfter;
    }

    public void setDueAfter(Date dueAfter) {
        this.dueAfter = dueAfter;
    }

    public Boolean getWithoutDueDate() {
        return withoutDueDate;
    }

    public void setWithoutDueDate(Boolean withoutDueDate) {
        this.withoutDueDate = withoutDueDate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getIncludeTaskLocalVariables() {
        return includeTaskLocalVariables;
    }

    public void setIncludeTaskLocalVariables(Boolean includeTaskLocalVariables) {
        this.includeTaskLocalVariables = includeTaskLocalVariables;
    }

    public Boolean getIncludeProcessVariables() {
        return includeProcessVariables;
    }

    public void setIncludeProcessVariables(Boolean includeProcessVariables) {
        this.includeProcessVariables = includeProcessVariables;
    }

    public String getScopeDefinitionId() {
        return scopeDefinitionId;
    }

    public void setScopeDefinitionId(String scopeDefinitionId) {
        this.scopeDefinitionId = scopeDefinitionId;
    }

    public String getScopeId() {
        return scopeId;
    }

    public void setScopeId(String scopeId) {
        this.scopeId = scopeId;
    }

    public String getScopeType() {
        return scopeType;
    }

    public void setScopeType(String scopeType) {
        this.scopeType = scopeType;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getTenantIdLike() {
        return tenantIdLike;
    }

    public void setTenantIdLike(String tenantIdLike) {
        this.tenantIdLike = tenantIdLike;
    }

    public Boolean getWithoutTenantId() {
        return withoutTenantId;
    }

    public void setWithoutTenantId(Boolean withoutTenantId) {
        this.withoutTenantId = withoutTenantId;
    }

    public String getCandidateOrAssigned() {
        return candidateOrAssigned;
    }

    public void setCandidateOrAssigned(String candidateOrAssigned) {
        this.candidateOrAssigned = candidateOrAssigned;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getReferenceIds() {
        return referenceIds;
    }

    public void setReferenceIds(String referenceIds) {
        this.referenceIds = referenceIds;
    }

    @Override
    public String toString() {
        return "TaskQueryCriteria{" +
                "name='" + name + '\'' +
                ", nameLike='" + nameLike + '\'' +
                ", description='" + description + '\'' +
                ", descriptionLike='" + descriptionLike + '\'' +
                ", priority=" + priority +
                ", minimumPriority=" + minimumPriority +
                ", maximumPriority=" + maximumPriority +
                ", assignee='" + assignee + '\'' +
                ", assigneeLike='" + assigneeLike + '\'' +
                ", owner='" + owner + '\'' +
                ", ownerLike='" + ownerLike + '\'' +
                ", unassigned=" + unassigned +
                ", delegationState='" + delegationState + '\'' +
                ", candidateUser='" + candidateUser + '\'' +
                ", candidateGroup='" + candidateGroup + '\'' +
                ", candidateGroupIn=" + candidateGroupIn +
                ", ignoreAssignee=" + ignoreAssignee +
                ", involvedUser='" + involvedUser + '\'' +
                ", processInstanceId='" + processInstanceId + '\'' +
                ", processInstanceIdWithChildren='" + processInstanceIdWithChildren + '\'' +
                ", processInstanceBusinessKey='" + processInstanceBusinessKey + '\'' +
                ", processInstanceBusinessKeyLike='" + processInstanceBusinessKeyLike + '\'' +
                ", processDefinitionId='" + processDefinitionId + '\'' +
                ", processDefinitionKey='" + processDefinitionKey + '\'' +
                ", processDefinitionName='" + processDefinitionName + '\'' +
                ", processDefinitionKeyLike='" + processDefinitionKeyLike + '\'' +
                ", processDefinitionNameLike='" + processDefinitionNameLike + '\'' +
                ", executionId='" + executionId + '\'' +
                ", createdOn=" + createdOn +
                ", createdBefore=" + createdBefore +
                ", createdAfter=" + createdAfter +
                ", excludeSubTasks=" + excludeSubTasks +
                ", taskDefinitionKey='" + taskDefinitionKey + '\'' +
                ", taskDefinitionKeyLike='" + taskDefinitionKeyLike + '\'' +
                ", taskDefinitionKeys=" + taskDefinitionKeys +
                ", dueDate=" + dueDate +
                ", dueBefore=" + dueBefore +
                ", dueAfter=" + dueAfter +
                ", withoutDueDate=" + withoutDueDate +
                ", active=" + active +
                ", includeTaskLocalVariables=" + includeTaskLocalVariables +
                ", includeProcessVariables=" + includeProcessVariables +
                ", scopeDefinitionId='" + scopeDefinitionId + '\'' +
                ", scopeId='" + scopeId + '\'' +
                ", scopeType='" + scopeType + '\'' +
                ", tenantId='" + tenantId + '\'' +
                ", tenantIdLike='" + tenantIdLike + '\'' +
                ", withoutTenantId=" + withoutTenantId +
                ", candidateOrAssigned='" + candidateOrAssigned + '\'' +
                ", category='" + category + '\'' +
                ", referenceIds='" + referenceIds + '\'' +
                '}';
    }
}
