package com.realfinance.sofa.flow.model;

import java.util.Date;
import java.util.List;

public class HistoricProcessInstanceQueryCriteria {
    private String processInstanceId;
    private List<String> processInstanceIds;
    private String processInstanceName;
    private String processInstanceNameLike;
    private String processInstanceNameLikeIgnoreCase;
    private String processBusinessKey;
    private String processBusinessKeyLike;
    private String processDefinitionId;
    private String processDefinitionKey;
    private List<String> processDefinitionKeyIn;
    private List<String> processDefinitionKeyNotIn;
    private String processDefinitionName;
    private Integer processDefinitionVersion;
    private String processDefinitionCategory;
    private String deploymentId;
    private List<String> deploymentIdIn;
    private String superProcessInstanceId;
    private Boolean excludeSubprocesses;
    private Boolean finished;
    private String involvedUser;
    private Date finishedAfter;
    private Date finishedBefore;
    private Date startedAfter;
    private Date startedBefore;
    private String startedBy;
    private Boolean includeProcessVariables;
//    private List<QueryVariable> variables;
    private String callbackId;
    private String callbackType;
    private String tenantId;
    private String tenantIdLike;
    private Boolean withoutTenantId;

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public List<String> getProcessInstanceIds() {
        return processInstanceIds;
    }

    public void setProcessInstanceIds(List<String> processInstanceIds) {
        this.processInstanceIds = processInstanceIds;
    }

    public String getProcessInstanceName() {
        return processInstanceName;
    }

    public void setProcessInstanceName(String processInstanceName) {
        this.processInstanceName = processInstanceName;
    }

    public String getProcessInstanceNameLike() {
        return processInstanceNameLike;
    }

    public void setProcessInstanceNameLike(String processInstanceNameLike) {
        this.processInstanceNameLike = processInstanceNameLike;
    }

    public String getProcessInstanceNameLikeIgnoreCase() {
        return processInstanceNameLikeIgnoreCase;
    }

    public void setProcessInstanceNameLikeIgnoreCase(String processInstanceNameLikeIgnoreCase) {
        this.processInstanceNameLikeIgnoreCase = processInstanceNameLikeIgnoreCase;
    }

    public String getProcessBusinessKey() {
        return processBusinessKey;
    }

    public void setProcessBusinessKey(String processBusinessKey) {
        this.processBusinessKey = processBusinessKey;
    }

    public String getProcessBusinessKeyLike() {
        return processBusinessKeyLike;
    }

    public void setProcessBusinessKeyLike(String processBusinessKeyLike) {
        this.processBusinessKeyLike = processBusinessKeyLike;
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

    public List<String> getProcessDefinitionKeyIn() {
        return processDefinitionKeyIn;
    }

    public void setProcessDefinitionKeyIn(List<String> processDefinitionKeyIn) {
        this.processDefinitionKeyIn = processDefinitionKeyIn;
    }

    public List<String> getProcessDefinitionKeyNotIn() {
        return processDefinitionKeyNotIn;
    }

    public void setProcessDefinitionKeyNotIn(List<String> processDefinitionKeyNotIn) {
        this.processDefinitionKeyNotIn = processDefinitionKeyNotIn;
    }

    public String getProcessDefinitionName() {
        return processDefinitionName;
    }

    public void setProcessDefinitionName(String processDefinitionName) {
        this.processDefinitionName = processDefinitionName;
    }

    public Integer getProcessDefinitionVersion() {
        return processDefinitionVersion;
    }

    public void setProcessDefinitionVersion(Integer processDefinitionVersion) {
        this.processDefinitionVersion = processDefinitionVersion;
    }

    public String getProcessDefinitionCategory() {
        return processDefinitionCategory;
    }

    public void setProcessDefinitionCategory(String processDefinitionCategory) {
        this.processDefinitionCategory = processDefinitionCategory;
    }

    public String getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public List<String> getDeploymentIdIn() {
        return deploymentIdIn;
    }

    public void setDeploymentIdIn(List<String> deploymentIdIn) {
        this.deploymentIdIn = deploymentIdIn;
    }

    public String getSuperProcessInstanceId() {
        return superProcessInstanceId;
    }

    public void setSuperProcessInstanceId(String superProcessInstanceId) {
        this.superProcessInstanceId = superProcessInstanceId;
    }

    public Boolean getExcludeSubprocesses() {
        return excludeSubprocesses;
    }

    public void setExcludeSubprocesses(Boolean excludeSubprocesses) {
        this.excludeSubprocesses = excludeSubprocesses;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public String getInvolvedUser() {
        return involvedUser;
    }

    public void setInvolvedUser(String involvedUser) {
        this.involvedUser = involvedUser;
    }

    public Date getFinishedAfter() {
        return finishedAfter;
    }

    public void setFinishedAfter(Date finishedAfter) {
        this.finishedAfter = finishedAfter;
    }

    public Date getFinishedBefore() {
        return finishedBefore;
    }

    public void setFinishedBefore(Date finishedBefore) {
        this.finishedBefore = finishedBefore;
    }

    public Date getStartedAfter() {
        return startedAfter;
    }

    public void setStartedAfter(Date startedAfter) {
        this.startedAfter = startedAfter;
    }

    public Date getStartedBefore() {
        return startedBefore;
    }

    public void setStartedBefore(Date startedBefore) {
        this.startedBefore = startedBefore;
    }

    public String getStartedBy() {
        return startedBy;
    }

    public void setStartedBy(String startedBy) {
        this.startedBy = startedBy;
    }

    public Boolean getIncludeProcessVariables() {
        return includeProcessVariables;
    }

    public void setIncludeProcessVariables(Boolean includeProcessVariables) {
        this.includeProcessVariables = includeProcessVariables;
    }

    public String getCallbackId() {
        return callbackId;
    }

    public void setCallbackId(String callbackId) {
        this.callbackId = callbackId;
    }

    public String getCallbackType() {
        return callbackType;
    }

    public void setCallbackType(String callbackType) {
        this.callbackType = callbackType;
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
}
