package com.realfinance.sofa.flow.flowable.cmd;

import com.realfinance.sofa.flow.flowable.listener.ProcessEndListener;
import com.realfinance.sofa.flow.model.NextUserTaskInfo;
import com.realfinance.sofa.flow.util.CommandContextUtils;
import org.apache.commons.lang3.StringUtils;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.impl.identity.Authentication;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.engine.FormService;
import org.flowable.engine.TaskService;
import org.flowable.engine.form.FormProperty;
import org.flowable.engine.form.TaskFormData;
import org.flowable.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.flowable.engine.impl.cmd.NeedsActiveTaskCmd;
import org.flowable.engine.impl.persistence.entity.ExecutionEntity;
import org.flowable.engine.impl.util.CommandContextUtil;
import org.flowable.idm.api.User;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.realfinance.sofa.flow.model.ProcessVariableConstants.NEXT_NODE_KEY;

public class CompleteTaskCmd extends NeedsActiveTaskCmd<Void> {
    private static final Logger log = LoggerFactory.getLogger(CompleteTaskCmd.class);

    protected String businessCode;
    protected String comment;
    protected NextUserTaskInfo nextUserTaskInfo;
    protected Map<String, String> formData;

    public CompleteTaskCmd(String taskId, String comment, NextUserTaskInfo nextUserTaskInfo, Map<String, String> formData) {
        this(null,taskId,comment,nextUserTaskInfo,formData);
    }

    public CompleteTaskCmd(String businessCode, String taskId, String comment, NextUserTaskInfo nextUserTaskInfo, Map<String, String> formData) {
        super(taskId);
        this.businessCode = businessCode;
        this.comment = comment;
        this.nextUserTaskInfo = nextUserTaskInfo;
        this.formData = formData;
    }

    @Override
    protected Void execute(CommandContext commandContext, TaskEntity task) {
        ProcessEngineConfigurationImpl processEngineConfiguration = CommandContextUtil.getProcessEngineConfiguration(commandContext);

        ExecutionEntity processInstanceEntity = CommandContextUtil.getExecutionEntityManager(commandContext).findById(task.getProcessInstanceId());
        if (businessCode != null && !StringUtils.equals(processInstanceEntity.getReferenceId(),businessCode)) {
            throw new FlowableException("业务编码错误");
        }

        if (!StringUtils.equals(task.getAssignee(), Authentication.getAuthenticatedUserId())) {
            throw new FlowableException("非任务办理人不能执行完成任务");
        }

        User user = CommandContextUtils.findUser(commandContext, Authentication.getAuthenticatedUserId());

        String submitTaskComment = String.format("%s 提交了任务，意见：%s",
                user.getLastName(), StringUtils.defaultString(comment));
        TaskService taskService = processEngineConfiguration.getTaskService();
        FormService formService = processEngineConfiguration.getFormService();

        // 添加评语
        taskService.addComment(taskId,
                task.getProcessInstanceId(),
                submitTaskComment);

        TaskFormData taskFormData = formService.getTaskFormData(taskId);
        List<FormProperty> formProperties = taskFormData.getFormProperties();
        if (nextUserTaskInfo != null) {
            taskService.setVariable(taskId,NEXT_NODE_KEY,nextUserTaskInfo);
        }

        if (formProperties.isEmpty()) {
            taskService.complete(taskId);
        } else {
            formService.submitTaskFormData(taskId, fillFormData(formProperties,formData));
        }

        commandContext.addCloseListener(new ProcessEndListener(processInstanceEntity));
        return null;
    }

    private Map<String,String> fillFormData(List<FormProperty> formProperties, Map<String, String> formData) {
        assert !formProperties.isEmpty();
        if (formData == null) {
            formData = new HashMap<>();
        }
        for (FormProperty formProperty : formProperties) {
            if (!formData.containsKey(formProperty.getId())) {
                formData.put(formProperty.getId(),null);
            }
        }
        return formData;
    }
}
