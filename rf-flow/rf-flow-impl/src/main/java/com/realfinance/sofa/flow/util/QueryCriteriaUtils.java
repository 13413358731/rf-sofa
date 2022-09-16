package com.realfinance.sofa.flow.util;

import com.realfinance.sofa.flow.model.HistoricProcessInstanceQueryCriteria;
import com.realfinance.sofa.flow.model.HistoricTaskInstanceQueryCriteria;
import com.realfinance.sofa.flow.model.TaskQueryCriteria;
import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.flowable.engine.history.HistoricProcessInstanceQuery;
import org.flowable.engine.impl.HistoricProcessInstanceQueryProperty;
import org.flowable.task.api.DelegationState;
import org.flowable.task.api.NativeTaskQuery;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.flowable.task.api.history.HistoricTaskInstanceQuery;
import org.flowable.task.service.impl.HistoricTaskInstanceQueryProperty;
import org.flowable.task.service.impl.TaskQueryProperty;
import org.springframework.data.domain.Sort;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class QueryCriteriaUtils {

    public static TaskQuery setQueryCriteria(TaskQuery query, TaskQueryCriteria queryCriteria) {
        Assert.notNull(query,"query can not be null");
        if (queryCriteria == null) {
            return query;
        }
        if (queryCriteria.getName() != null) {
            query.taskName(queryCriteria.getName());
        }
        if (queryCriteria.getNameLike() != null) {
            query.taskNameLike(queryCriteria.getNameLike());
        }
        if (queryCriteria.getDescription() != null) {
            query.taskDescription(queryCriteria.getDescription());
        }
        if (queryCriteria.getDescriptionLike() != null) {
            query.taskDescriptionLike(queryCriteria.getDescriptionLike());
        }
        if (queryCriteria.getPriority() != null) {
            query.taskPriority(queryCriteria.getPriority());
        }
        if (queryCriteria.getMinimumPriority() != null) {
            query.taskMinPriority(queryCriteria.getMinimumPriority());
        }
        if (queryCriteria.getMaximumPriority() != null) {
            query.taskMaxPriority(queryCriteria.getMaximumPriority());
        }
        if (queryCriteria.getAssignee() != null) {
            query.taskAssignee(queryCriteria.getAssignee());
        }
        if (queryCriteria.getAssigneeLike() != null) {
            query.taskAssigneeLike(queryCriteria.getAssigneeLike());
        }
        if (queryCriteria.getOwner() != null) {
            query.taskOwner(queryCriteria.getOwner());
        }
        if (queryCriteria.getOwnerLike() != null) {
            query.taskOwnerLike(queryCriteria.getOwnerLike());
        }
        if (queryCriteria.getUnassigned() != null) {
            query.taskUnassigned();
        }
        if (queryCriteria.getDelegationState() != null) {
            DelegationState state = getDelegationState(queryCriteria.getDelegationState());
            if (state != null) {
                query.taskDelegationState(state);
            }
        }
        if (queryCriteria.getCandidateUser() != null) {
            query.taskCandidateUser(queryCriteria.getCandidateUser());
        }
        if (queryCriteria.getInvolvedUser() != null) {
            query.taskInvolvedUser(queryCriteria.getInvolvedUser());
        }
        if (queryCriteria.getCandidateGroup() != null) {
            query.taskCandidateGroup(queryCriteria.getCandidateGroup());
        }
        /*if (queryCriteria.getCandidateGroupIn() != null) {
            query.taskCandidateGroupIn(queryCriteria.getCandidateGroupIn());
        }*/
        if (queryCriteria.isIgnoreAssignee()) {
            query.ignoreAssigneeValue();
        }
        if (queryCriteria.getProcessInstanceId() != null) {
            query.processInstanceId(queryCriteria.getProcessInstanceId());
        }
        if (queryCriteria.getProcessInstanceIdWithChildren() != null) {
            query.processInstanceIdWithChildren(queryCriteria.getProcessInstanceIdWithChildren());
        }
        if (queryCriteria.getProcessInstanceBusinessKey() != null) {
            query.processInstanceBusinessKey(queryCriteria.getProcessInstanceBusinessKey());
        }
        if (queryCriteria.getExecutionId() != null) {
            query.executionId(queryCriteria.getExecutionId());
        }
        if (queryCriteria.getCreatedOn() != null) {
            query.taskCreatedOn(queryCriteria.getCreatedOn());
        }
        if (queryCriteria.getCreatedBefore() != null) {
            query.taskCreatedBefore(queryCriteria.getCreatedBefore());
        }
        if (queryCriteria.getCreatedAfter() != null) {
            query.taskCreatedAfter(queryCriteria.getCreatedAfter());
        }
        if (queryCriteria.getExcludeSubTasks() != null) {
            if (queryCriteria.getExcludeSubTasks().booleanValue()) {
                query.excludeSubtasks();
            }
        }

        if (queryCriteria.getTaskDefinitionKey() != null) {
            query.taskDefinitionKey(queryCriteria.getTaskDefinitionKey());
        }

        if (queryCriteria.getTaskDefinitionKeyLike() != null) {
            query.taskDefinitionKeyLike(queryCriteria.getTaskDefinitionKeyLike());
        }

        if (queryCriteria.getTaskDefinitionKeys() != null) {
            query.taskDefinitionKeys(queryCriteria.getTaskDefinitionKeys());
        }

        if (queryCriteria.getDueDate() != null) {
            query.taskDueDate(queryCriteria.getDueDate());
        }
        if (queryCriteria.getDueBefore() != null) {
            query.taskDueBefore(queryCriteria.getDueBefore());
        }
        if (queryCriteria.getDueAfter() != null) {
            query.taskDueAfter(queryCriteria.getDueAfter());
        }
        if (queryCriteria.getWithoutDueDate() != null && queryCriteria.getWithoutDueDate()) {
            query.withoutTaskDueDate();
        }

        if (queryCriteria.getActive() != null) {
            if (queryCriteria.getActive().booleanValue()) {
                query.active();
            } else {
                query.suspended();
            }
        }

        if (queryCriteria.getIncludeTaskLocalVariables() != null) {
            if (queryCriteria.getIncludeTaskLocalVariables()) {
                query.includeTaskLocalVariables();
            }
        }
        if (queryCriteria.getIncludeProcessVariables() != null) {
            if (queryCriteria.getIncludeProcessVariables()) {
                query.includeProcessVariables();
            }
        }

        if (queryCriteria.getProcessInstanceBusinessKeyLike() != null) {
            query.processInstanceBusinessKeyLike(queryCriteria.getProcessInstanceBusinessKeyLike());
        }

        if (queryCriteria.getProcessDefinitionId() != null) {
            query.processDefinitionId(queryCriteria.getProcessDefinitionId());
        }

        if (queryCriteria.getProcessDefinitionKey() != null) {
            query.processDefinitionKey(queryCriteria.getProcessDefinitionKey());
        }

        if (queryCriteria.getProcessDefinitionKeyLike() != null) {
            query.processDefinitionKeyLike(queryCriteria.getProcessDefinitionKeyLike());
        }

        if (queryCriteria.getProcessDefinitionName() != null) {
            query.processDefinitionName(queryCriteria.getProcessDefinitionName());
        }

        if (queryCriteria.getProcessDefinitionNameLike() != null) {
            query.processDefinitionNameLike(queryCriteria.getProcessDefinitionNameLike());
        }

        if (queryCriteria.getScopeDefinitionId() != null) {
            query.scopeDefinitionId(queryCriteria.getScopeDefinitionId());
        }

        if (queryCriteria.getScopeId() != null) {
            query.scopeId(queryCriteria.getScopeId());
        }

        if (queryCriteria.getScopeType() != null) {
            query.scopeType(queryCriteria.getScopeType());
        }

        if (queryCriteria.getTenantId() != null) {
            query.taskTenantId(queryCriteria.getTenantId());
        }

        if (queryCriteria.getTenantIdLike() != null) {
            query.taskTenantIdLike(queryCriteria.getTenantIdLike());
        }

        if (Boolean.TRUE.equals(queryCriteria.getWithoutTenantId())) {
            query.taskWithoutTenantId();
        }

        /*if (queryCriteria.getCandidateOrAssigned() != null) {
            query.taskCandidateOrAssigned(queryCriteria.getCandidateOrAssigned());
        }*/

        if (queryCriteria.getCategory() != null) {
            query.taskCategory(queryCriteria.getCategory());
        }
        return query;
    }

    public static DelegationState getDelegationState(String delegationState) {
        DelegationState state = null;
        if (delegationState != null) {
            if (DelegationState.RESOLVED.name().toLowerCase().equals(delegationState)) {
                return DelegationState.RESOLVED;
            } else if (DelegationState.PENDING.name().toLowerCase().equals(delegationState)) {
                return DelegationState.PENDING;
            } else {
                throw new FlowableIllegalArgumentException("Illegal value for delegationState: " + delegationState);
            }
        }
        return state;
    }

    public static void setSort(TaskQuery taskQuery, Sort sort) {
        Assert.notNull(taskQuery,"query can not be null");
        Assert.notNull(sort,"sort can not be null");
        // 设置排序 只能拿出第一个order进行排序
        sort.get().limit(1).findFirst().ifPresent(e -> {
            TaskQueryProperty taskQueryProperty = TaskQueryProperty.findByName(e.getProperty());
            if (taskQueryProperty != null) {
                taskQuery.orderBy(taskQueryProperty);
                if (e.getDirection().isDescending()) {
                    taskQuery.desc();
                } else {
                    taskQuery.asc();
                }
            }
        });
    }


    public static HistoricTaskInstanceQuery setQueryCriteria(HistoricTaskInstanceQuery query, HistoricTaskInstanceQueryCriteria queryCriteria) {
        Assert.notNull(query,"query can not be null");
        if (queryCriteria == null) {
            return query;
        }
        if (queryCriteria.getTaskId() != null) {
            query.taskId(queryCriteria.getTaskId());
        }
        if (queryCriteria.getProcessInstanceId() != null) {
            query.processInstanceId(queryCriteria.getProcessInstanceId());
        }
        if (queryCriteria.getProcessInstanceIdWithChildren() != null) {
            query.processInstanceIdWithChildren(queryCriteria.getProcessInstanceIdWithChildren());
        }
        if (queryCriteria.getProcessBusinessKey() != null) {
            query.processInstanceBusinessKey(queryCriteria.getProcessBusinessKey());
        }
        if (queryCriteria.getProcessBusinessKeyLike() != null) {
            query.processInstanceBusinessKeyLike(queryCriteria.getProcessBusinessKeyLike());
        }
        if (queryCriteria.getProcessDefinitionKey() != null) {
            query.processDefinitionKey(queryCriteria.getProcessDefinitionKey());
        }
        if (queryCriteria.getProcessDefinitionKeyLike() != null) {
            query.processDefinitionKeyLike(queryCriteria.getProcessDefinitionKeyLike());
        }
        if (queryCriteria.getProcessDefinitionId() != null) {
            query.processDefinitionId(queryCriteria.getProcessDefinitionId());
        }
        if (queryCriteria.getProcessDefinitionName() != null) {
            query.processDefinitionName(queryCriteria.getProcessDefinitionName());
        }
        if (queryCriteria.getProcessDefinitionNameLike() != null) {
            query.processDefinitionNameLike(queryCriteria.getProcessDefinitionNameLike());
        }
        if (queryCriteria.getExecutionId() != null) {
            query.executionId(queryCriteria.getExecutionId());
        }
        if (queryCriteria.getTaskName() != null) {
            query.taskName(queryCriteria.getTaskName());
        }
        if (queryCriteria.getTaskNameLike() != null) {
            query.taskNameLike(queryCriteria.getTaskNameLike());
        }
        if (queryCriteria.getTaskDescription() != null) {
            query.taskDescription(queryCriteria.getTaskDescription());
        }
        if (queryCriteria.getTaskDescriptionLike() != null) {
            query.taskDescriptionLike(queryCriteria.getTaskDescriptionLike());
        }
        if (queryCriteria.getTaskDefinitionKey() != null) {
            query.taskDefinitionKey(queryCriteria.getTaskDefinitionKey());
        }
        if (queryCriteria.getTaskDefinitionKeyLike() != null) {
            query.taskDefinitionKeyLike(queryCriteria.getTaskDefinitionKeyLike());
        }
        if (queryCriteria.getTaskDefinitionKeys() != null) {
            query.taskDefinitionKeys(queryCriteria.getTaskDefinitionKeys());
        }
        if (queryCriteria.getTaskCategory() != null) {
            query.taskCategory(queryCriteria.getTaskCategory());
        }
        if (queryCriteria.getTaskDeleteReason() != null) {
            query.taskDeleteReason(queryCriteria.getTaskDeleteReason());
        }
        if (queryCriteria.getTaskDeleteReasonLike() != null) {
            query.taskDeleteReasonLike(queryCriteria.getTaskDeleteReasonLike());
        }
        if (queryCriteria.getTaskAssignee() != null) {
            query.taskAssignee(queryCriteria.getTaskAssignee());
        }
        if (queryCriteria.getTaskAssigneeLike() != null) {
            query.taskAssigneeLike(queryCriteria.getTaskAssigneeLike());
        }
        if (queryCriteria.getTaskOwner() != null) {
            query.taskOwner(queryCriteria.getTaskOwner());
        }
        if (queryCriteria.getTaskOwnerLike() != null) {
            query.taskOwnerLike(queryCriteria.getTaskOwnerLike());
        }
        if (queryCriteria.getTaskInvolvedUser() != null) {
            query.taskInvolvedUser(queryCriteria.getTaskInvolvedUser());
        }
        if (queryCriteria.getTaskPriority() != null) {
            query.taskPriority(queryCriteria.getTaskPriority());
        }
        if (queryCriteria.getTaskMinPriority() != null) {
            query.taskMinPriority(queryCriteria.getTaskMinPriority());
        }
        if (queryCriteria.getTaskMaxPriority() != null) {
            query.taskMaxPriority(queryCriteria.getTaskMaxPriority());
        }
        if (queryCriteria.getTaskPriority() != null) {
            query.taskPriority(queryCriteria.getTaskPriority());
        }
        if (queryCriteria.getFinished() != null) {
            if (queryCriteria.getFinished()) {
                query.finished();
            } else {
                query.unfinished();
            }
        }
        if (queryCriteria.getProcessFinished() != null) {
            if (queryCriteria.getProcessFinished()) {
                query.processFinished();
            } else {
                query.processUnfinished();
            }
        }
        if (queryCriteria.getParentTaskId() != null) {
            query.taskParentTaskId(queryCriteria.getParentTaskId());
        }
        if (queryCriteria.getDueDate() != null) {
            query.taskDueDate(queryCriteria.getDueDate());
        }
        if (queryCriteria.getDueDateAfter() != null) {
            query.taskDueAfter(queryCriteria.getDueDateAfter());
        }
        if (queryCriteria.getDueDateBefore() != null) {
            query.taskDueBefore(queryCriteria.getDueDateBefore());
        }
        if (queryCriteria.getWithoutDueDate() != null && queryCriteria.getWithoutDueDate()) {
            query.withoutTaskDueDate();
        }
        if (queryCriteria.getTaskCreatedOn() != null) {
            query.taskCreatedOn(queryCriteria.getTaskCreatedOn());
        }
        if (queryCriteria.getTaskCreatedBefore() != null) {
            query.taskCreatedBefore(queryCriteria.getTaskCreatedBefore());
        }
        if (queryCriteria.getTaskCreatedAfter() != null) {
            query.taskCreatedAfter(queryCriteria.getTaskCreatedAfter());
        }
        if (queryCriteria.getTaskCreatedOn() != null) {
            query.taskCreatedOn(queryCriteria.getTaskCreatedOn());
        }
        if (queryCriteria.getTaskCreatedBefore() != null) {
            query.taskCreatedBefore(queryCriteria.getTaskCreatedBefore());
        }
        if (queryCriteria.getTaskCreatedAfter() != null) {
            query.taskCreatedAfter(queryCriteria.getTaskCreatedAfter());
        }
        if (queryCriteria.getTaskCompletedOn() != null) {
            query.taskCompletedOn(queryCriteria.getTaskCompletedOn());
        }
        if (queryCriteria.getTaskCompletedBefore() != null) {
            query.taskCompletedBefore(queryCriteria.getTaskCompletedBefore());
        }
        if (queryCriteria.getTaskCompletedAfter() != null) {
            query.taskCompletedAfter(queryCriteria.getTaskCompletedAfter());
        }
        if (queryCriteria.getIncludeTaskLocalVariables() != null) {
            if (queryCriteria.getIncludeTaskLocalVariables()) {
                query.includeTaskLocalVariables();
            }
        }
        if (queryCriteria.getIncludeProcessVariables() != null) {
            if (queryCriteria.getIncludeProcessVariables()) {
                query.includeProcessVariables();
            }
        }
        if (queryCriteria.getScopeDefinitionId() != null) {
            query.scopeDefinitionId(queryCriteria.getScopeDefinitionId());
        }
        if (queryCriteria.getScopeId() != null) {
            query.scopeId(queryCriteria.getScopeId());
        }
        if (queryCriteria.getScopeType() != null) {
            query.scopeType(queryCriteria.getScopeType());
        }

        if (queryCriteria.getTenantId() != null) {
            query.taskTenantId(queryCriteria.getTenantId());
        }
        if (queryCriteria.getTenantIdLike() != null) {
            query.taskTenantIdLike(queryCriteria.getTenantIdLike());
        }
        if (Boolean.TRUE.equals(queryCriteria.getWithoutTenantId())) {
            query.taskWithoutTenantId();
        }

        if (Boolean.TRUE.equals(queryCriteria.getWithoutDeleteReason())) {
            query.taskWithoutDeleteReason();
        }

        if (queryCriteria.getTaskCandidateGroup() != null) {
            query.taskCandidateGroup(queryCriteria.getTaskCandidateGroup());
        }

        if (queryCriteria.isIgnoreTaskAssignee()) {
            query.ignoreAssigneeValue();
        }
        return query;
    }

    public static void setSort(HistoricTaskInstanceQuery historicTaskInstanceQuery, Sort sort) {
        Assert.notNull(historicTaskInstanceQuery,"query can not be null");
        Assert.notNull(sort,"sort can not be null");
        // 设置排序 只能拿出第一个order进行排序
        sort.get().limit(1).findFirst().ifPresent(e -> {
            HistoricTaskInstanceQueryProperty historicTaskInstanceQueryProperty = HistoricTaskInstanceQueryProperty.findByName(e.getProperty());
            if (historicTaskInstanceQueryProperty != null) {
                historicTaskInstanceQuery.orderBy(historicTaskInstanceQueryProperty);
                if (e.getDirection().isDescending()) {
                    historicTaskInstanceQuery.desc();
                } else {
                    historicTaskInstanceQuery.asc();
                }
            }
        });
    }

    public static HistoricProcessInstanceQuery setQueryCriteria(HistoricProcessInstanceQuery query, HistoricProcessInstanceQueryCriteria queryCriteria) {
        if (queryCriteria.getProcessInstanceId() != null) {
            query.processInstanceId(queryCriteria.getProcessInstanceId());
        }
        if (queryCriteria.getProcessInstanceIds() != null && !queryCriteria.getProcessInstanceIds().isEmpty()) {
            query.processInstanceIds(new HashSet<>(queryCriteria.getProcessInstanceIds()));
        }
        if (queryCriteria.getProcessInstanceName() != null) {
            query.processInstanceName(queryCriteria.getProcessInstanceName());
        }
        if (queryCriteria.getProcessInstanceNameLike() != null) {
            query.processInstanceNameLike(queryCriteria.getProcessInstanceNameLike());
        }
        if (queryCriteria.getProcessInstanceNameLikeIgnoreCase() != null) {
            query.processInstanceNameLikeIgnoreCase(queryCriteria.getProcessInstanceNameLikeIgnoreCase());
        }
        if (queryCriteria.getProcessDefinitionKey() != null) {
            query.processDefinitionKey(queryCriteria.getProcessDefinitionKey());
        }
        if (queryCriteria.getProcessDefinitionKeyIn() != null) {
            query.processDefinitionKeyIn(queryCriteria.getProcessDefinitionKeyIn());
        }
        if (queryCriteria.getProcessDefinitionKeyNotIn() != null) {
            query.processDefinitionKeyNotIn(queryCriteria.getProcessDefinitionKeyNotIn());
        }
        if (queryCriteria.getProcessDefinitionId() != null) {
            query.processDefinitionId(queryCriteria.getProcessDefinitionId());
        }
        if (queryCriteria.getProcessDefinitionName() != null) {
            query.processDefinitionName(queryCriteria.getProcessDefinitionName());
        }
        if (queryCriteria.getProcessDefinitionVersion() != null) {
            query.processDefinitionVersion(Integer.valueOf(queryCriteria.getProcessDefinitionVersion()));
        }
        if (queryCriteria.getProcessDefinitionCategory() != null) {
            query.processDefinitionCategory(queryCriteria.getProcessDefinitionCategory());
        }
        if (queryCriteria.getDeploymentId() != null) {
            query.deploymentId(queryCriteria.getDeploymentId());
        }
        if (queryCriteria.getDeploymentIdIn() != null) {
            query.deploymentIdIn(queryCriteria.getDeploymentIdIn());
        }
        if (queryCriteria.getProcessBusinessKey() != null) {
            query.processInstanceBusinessKey(queryCriteria.getProcessBusinessKey());
        }
        if (queryCriteria.getProcessBusinessKeyLike() != null) {
            query.processInstanceBusinessKeyLike(queryCriteria.getProcessBusinessKeyLike());
        }
        if (queryCriteria.getInvolvedUser() != null) {
            query.involvedUser(queryCriteria.getInvolvedUser());
        }
        if (queryCriteria.getSuperProcessInstanceId() != null) {
            query.superProcessInstanceId(queryCriteria.getSuperProcessInstanceId());
        }
        if (queryCriteria.getExcludeSubprocesses() != null) {
            query.excludeSubprocesses(queryCriteria.getExcludeSubprocesses());
        }
        if (queryCriteria.getFinishedAfter() != null) {
            query.finishedAfter(queryCriteria.getFinishedAfter());
        }
        if (queryCriteria.getFinishedBefore() != null) {
            query.finishedBefore(queryCriteria.getFinishedBefore());
        }
        if (queryCriteria.getStartedAfter() != null) {
            query.startedAfter(queryCriteria.getStartedAfter());
        }
        if (queryCriteria.getStartedBefore() != null) {
            query.startedBefore(queryCriteria.getStartedBefore());
        }
        if (queryCriteria.getStartedBy() != null) {
            query.startedBy(queryCriteria.getStartedBy());
        }
        if (queryCriteria.getFinished() != null) {
            if (queryCriteria.getFinished()) {
                query.finished();
            } else {
                query.unfinished();
            }
        }
        if (queryCriteria.getIncludeProcessVariables() != null) {
            if (queryCriteria.getIncludeProcessVariables()) {
                query.includeProcessVariables();
            }
        }

        if (queryCriteria.getCallbackId() != null) {
            query.processInstanceCallbackId(queryCriteria.getCallbackId());
        }
        if (queryCriteria.getCallbackType() != null) {
            query.processInstanceCallbackType(queryCriteria.getCallbackType());
        }

        if (queryCriteria.getTenantId() != null) {
            query.processInstanceTenantId(queryCriteria.getTenantId());
        }
        if (queryCriteria.getTenantIdLike() != null) {
            query.processInstanceTenantIdLike(queryCriteria.getTenantIdLike());
        }

        if (Boolean.TRUE.equals(queryCriteria.getWithoutTenantId())) {
            query.processInstanceWithoutTenantId();
        }
        return query;
    }

    public static void setSort(HistoricProcessInstanceQuery historicProcessInstanceQuery, Sort sort) {
        Assert.notNull(historicProcessInstanceQuery,"query can not be null");
        Assert.notNull(sort,"sort can not be null");
        // 设置排序 只能拿出第一个order进行排序
        sort.get().limit(1).findFirst().ifPresent(e -> {
            HistoricProcessInstanceQueryProperty historicProcessInstanceQueryProperty = HistoricProcessInstanceQueryProperty.findByName(e.getProperty());
            if (historicProcessInstanceQueryProperty != null) {
                historicProcessInstanceQuery.orderBy(historicProcessInstanceQueryProperty);
                if (e.getDirection().isDescending()) {
                    historicProcessInstanceQuery.desc();
                } else {
                    historicProcessInstanceQuery.asc();
                }
            }
        });
    }

    public static TaskQuery setText(TaskQuery query, TaskQueryCriteria queryCriteria) {
        Assert.notNull(query,"query can not be null");
        if (queryCriteria == null) {
            return query;
        }
        if (queryCriteria.getName() != null) {
            query.taskName(queryCriteria.getName());
        }
        if (queryCriteria.getNameLike() != null) {
            query.taskNameLike(queryCriteria.getNameLike());
        }
        if (queryCriteria.getDescription() != null) {
            query.taskDescription(queryCriteria.getDescription());
        }
        if (queryCriteria.getDescriptionLike() != null) {
            query.taskDescriptionLike(queryCriteria.getDescriptionLike());
        }
        if (queryCriteria.getPriority() != null) {
            query.taskPriority(queryCriteria.getPriority());
        }
        if (queryCriteria.getMinimumPriority() != null) {
            query.taskMinPriority(queryCriteria.getMinimumPriority());
        }
        if (queryCriteria.getMaximumPriority() != null) {
            query.taskMaxPriority(queryCriteria.getMaximumPriority());
        }
        if (queryCriteria.getAssignee() != null) {
            query.taskAssignee(queryCriteria.getAssignee());
        }
        if (queryCriteria.getAssigneeLike() != null) {
            query.taskAssigneeLike(queryCriteria.getAssigneeLike());
        }
        if (queryCriteria.getOwner() != null) {
            query.taskOwner(queryCriteria.getOwner());
        }
        if (queryCriteria.getOwnerLike() != null) {
            query.taskOwnerLike(queryCriteria.getOwnerLike());
        }
        if (queryCriteria.getUnassigned() != null) {
            query.taskUnassigned();
        }
        if (queryCriteria.getDelegationState() != null) {
            DelegationState state = getDelegationState(queryCriteria.getDelegationState());
            if (state != null) {
                query.taskDelegationState(state);
            }
        }
        if (queryCriteria.getCandidateUser() != null) {
            query.taskCandidateUser(queryCriteria.getCandidateUser());
        }
        if (queryCriteria.getInvolvedUser() != null) {
            query.taskInvolvedUser(queryCriteria.getInvolvedUser());
        }
        if (queryCriteria.getCandidateGroup() != null) {
            query.taskCandidateGroup(queryCriteria.getCandidateGroup());
        }
        /*if (queryCriteria.getCandidateGroupIn() != null) {
            query.taskCandidateGroupIn(queryCriteria.getCandidateGroupIn());
        }*/
        if (queryCriteria.isIgnoreAssignee()) {
            query.ignoreAssigneeValue();
        }
        if (queryCriteria.getProcessInstanceId() != null) {
            query.processInstanceId(queryCriteria.getProcessInstanceId());
        }
        if (queryCriteria.getProcessInstanceIdWithChildren() != null) {
            query.processInstanceIdWithChildren(queryCriteria.getProcessInstanceIdWithChildren());
        }
        if (queryCriteria.getProcessInstanceBusinessKey() != null) {
            query.processInstanceBusinessKey(queryCriteria.getProcessInstanceBusinessKey());
        }
        if (queryCriteria.getExecutionId() != null) {
            query.executionId(queryCriteria.getExecutionId());
        }
        if (queryCriteria.getCreatedOn() != null) {
            query.taskCreatedOn(queryCriteria.getCreatedOn());
        }
        if (queryCriteria.getCreatedBefore() != null) {
            query.taskCreatedBefore(queryCriteria.getCreatedBefore());
        }
        if (queryCriteria.getCreatedAfter() != null) {
            query.taskCreatedAfter(queryCriteria.getCreatedAfter());
        }
        if (queryCriteria.getExcludeSubTasks() != null) {
            if (queryCriteria.getExcludeSubTasks().booleanValue()) {
                query.excludeSubtasks();
            }
        }

        if (queryCriteria.getTaskDefinitionKey() != null) {
            query.taskDefinitionKey(queryCriteria.getTaskDefinitionKey());
        }

        if (queryCriteria.getTaskDefinitionKeyLike() != null) {
            query.taskDefinitionKeyLike(queryCriteria.getTaskDefinitionKeyLike());
        }

        if (queryCriteria.getTaskDefinitionKeys() != null) {
            query.taskDefinitionKeys(queryCriteria.getTaskDefinitionKeys());
        }

        if (queryCriteria.getDueDate() != null) {
            query.taskDueDate(queryCriteria.getDueDate());
        }
        if (queryCriteria.getDueBefore() != null) {
            query.taskDueBefore(queryCriteria.getDueBefore());
        }
        if (queryCriteria.getDueAfter() != null) {
            query.taskDueAfter(queryCriteria.getDueAfter());
        }
        if (queryCriteria.getWithoutDueDate() != null && queryCriteria.getWithoutDueDate()) {
            query.withoutTaskDueDate();
        }

        if (queryCriteria.getActive() != null) {
            if (queryCriteria.getActive().booleanValue()) {
                query.active();
            } else {
                query.suspended();
            }
        }

        if (queryCriteria.getIncludeTaskLocalVariables() != null) {
            if (queryCriteria.getIncludeTaskLocalVariables()) {
                query.includeTaskLocalVariables();
            }
        }
        if (queryCriteria.getIncludeProcessVariables() != null) {
            if (queryCriteria.getIncludeProcessVariables()) {
                query.includeProcessVariables();
            }
        }

        if (queryCriteria.getProcessInstanceBusinessKeyLike() != null) {
            query.processInstanceBusinessKeyLike(queryCriteria.getProcessInstanceBusinessKeyLike());
        }

        if (queryCriteria.getProcessDefinitionId() != null) {
            query.processDefinitionId(queryCriteria.getProcessDefinitionId());
        }

        if (queryCriteria.getProcessDefinitionKey() != null) {
            query.processDefinitionKey(queryCriteria.getProcessDefinitionKey());
        }

        if (queryCriteria.getProcessDefinitionKeyLike() != null) {
            query.processDefinitionKeyLike(queryCriteria.getProcessDefinitionKeyLike());
        }

        if (queryCriteria.getProcessDefinitionName() != null) {
            query.processDefinitionName(queryCriteria.getProcessDefinitionName());
        }

        if (queryCriteria.getProcessDefinitionNameLike() != null) {
            query.processDefinitionNameLike(queryCriteria.getProcessDefinitionNameLike());
        }

        if (queryCriteria.getScopeDefinitionId() != null) {
            query.scopeDefinitionId(queryCriteria.getScopeDefinitionId());
        }

        if (queryCriteria.getScopeId() != null) {
            query.scopeId(queryCriteria.getScopeId());
        }

        if (queryCriteria.getScopeType() != null) {
            query.scopeType(queryCriteria.getScopeType());
        }

        if (queryCriteria.getTenantId() != null) {
            query.taskTenantId(queryCriteria.getTenantId());
        }

        if (queryCriteria.getTenantIdLike() != null) {
            query.taskTenantIdLike(queryCriteria.getTenantIdLike());
        }

        if (Boolean.TRUE.equals(queryCriteria.getWithoutTenantId())) {
            query.taskWithoutTenantId();
        }

        if (queryCriteria.getCandidateOrAssigned() != null) {
            query.taskCandidateOrAssigned(queryCriteria.getCandidateOrAssigned());
        }

        if (queryCriteria.getCategory() != null) {
            query.taskCategory(queryCriteria.getCategory());
        }
        return query;
    }


   /* public static void setSort1(List<Task> tasks, Sort sort) {
        Assert.notNull(tasks,"query can not be null");
        Assert.notNull(sort,"sort can not be null");
        // 设置排序 只能拿出第一个order进行排序
        sort.get().limit(1).findFirst().ifPresent(e -> {
            TaskQueryProperty taskQueryProperty = TaskQueryProperty.findByName(e.getProperty());
            if (taskQueryProperty != null) {
                taskQuery.orderBy(taskQueryProperty);
                if (e.getDirection().isDescending()) {
                    taskQuery.desc();
                } else {
                    taskQuery.asc();
                }
            }
        });
    }*/

}
