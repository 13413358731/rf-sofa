package com.realfinance.sofa.flow.flowable.cmd;

import com.realfinance.sofa.flow.flowable.FlowConstants;
import com.realfinance.sofa.flow.model.ProcessInstanceDto;
import com.realfinance.sofa.flow.model.TaskDto;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.ExtensionElement;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.UserTask;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.common.engine.impl.identity.Authentication;
import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.flowable.engine.impl.persistence.entity.ExecutionEntity;
import org.flowable.engine.impl.util.CommandContextUtil;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GetTaskDtoByProcessInstanceIdCmd implements Command<List<TaskDto>>, Serializable {

    protected String processInstanceId;

    public GetTaskDtoByProcessInstanceIdCmd(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    @Override
    public List<TaskDto> execute(CommandContext commandContext) {
        String userId = Authentication.getAuthenticatedUserId();
        if (userId == null) {
            return new ArrayList<>();
        }
        ExecutionEntity processInstanceEntity = CommandContextUtil.getExecutionEntityManager(commandContext).findById(processInstanceId);
        if (processInstanceEntity == null) {
            throw new FlowableObjectNotFoundException("No process instance found for id '" + processInstanceId + "'", ProcessInstance.class);
        }

        ProcessEngineConfigurationImpl processEngineConfiguration = CommandContextUtil.getProcessEngineConfiguration(commandContext);
        List<Task> list = processEngineConfiguration.getTaskService().createTaskQuery()
                .processInstanceId(processInstanceId)
                .taskAssignee(userId)
                .orderByTaskCreateTime()
                .asc()
                .list();
        if (list.isEmpty()) {
            return new ArrayList<>();
        }
        RepositoryService repositoryService = processEngineConfiguration.getRepositoryService();
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstanceEntity.getProcessDefinitionId());
        return list.stream().map(e -> toTaskDto(bpmnModel,e,processInstanceEntity)).collect(Collectors.toList());
    }

    protected TaskDto toTaskDto(BpmnModel bpmnModel, Task task,ProcessInstance processInstance) {
        TaskDto taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setName(task.getName());
        taskDto.setCreateTime(task.getCreateTime());
        taskDto.setTaskDefinitionKey(task.getTaskDefinitionKey());
        taskDto.setDueDate(task.getDueDate());
        taskDto.setPriority(task.getPriority());
        taskDto.setExecutionId(task.getExecutionId());
        if (task.getDelegationState() != null) {
            taskDto.setDelegateStatus(task.getDelegationState().name());
        }
        taskDto.setClaimTime(task.getClaimTime());
        if (task.getOwner() != null) {
            taskDto.setOwner(Integer.parseInt(task.getOwner()));
        }
        if (task.getAssignee() != null) {
            taskDto.setAssignee(Integer.parseInt(task.getAssignee()));
        }

        // 设置自定扩展属性
        FlowElement flowElement = bpmnModel.getFlowElement(task.getTaskDefinitionKey());
        if (flowElement instanceof UserTask) {
            ExtensionElement businessDataEditable = getExtensionElement(flowElement, FlowConstants.BUSINESS_DATA_EDITABLE_CUSTOM_PROPERTY_ID);
            if (businessDataEditable != null) {
                taskDto.setBusinessDataEditable(Boolean.parseBoolean(businessDataEditable.getElementText()));
            }
        }

        if (task.getProcessInstanceId() != null) {
            ProcessInstanceDto processInstanceDto = new ProcessInstanceDto();
            processInstanceDto.setId(processInstance.getId());
            processInstanceDto.setName(processInstance.getName());
            processInstanceDto.setBusinessKey(processInstance.getBusinessKey());
            if (processInstance.getStartUserId() != null) {
                processInstanceDto.setStartUserId(Integer.parseInt(processInstance.getStartUserId()));
            }
            processInstanceDto.setStartTime(processInstance.getStartTime());
            processInstanceDto.setSuspended(processInstance.isSuspended());
            taskDto.setProcessInstance(processInstanceDto);
        }
        return taskDto;
    }


    protected ExtensionElement getExtensionElement(FlowElement flowElement, String key) {
        Map<String, List<ExtensionElement>> extensionElements = flowElement.getExtensionElements();
        if (extensionElements == null) {
            return null;
        }
        List<ExtensionElement> list = extensionElements.getOrDefault(key, Collections.emptyList());
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
}
