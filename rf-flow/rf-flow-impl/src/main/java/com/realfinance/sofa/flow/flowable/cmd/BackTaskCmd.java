package com.realfinance.sofa.flow.flowable.cmd;

import com.realfinance.sofa.flow.util.CommandContextUtils;
import org.apache.commons.lang3.StringUtils;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.impl.identity.Authentication;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.flowable.engine.impl.cmd.NeedsActiveTaskCmd;
import org.flowable.engine.impl.persistence.entity.ExecutionEntity;
import org.flowable.engine.impl.util.CommandContextUtil;
import org.flowable.idm.api.User;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskInfo;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class BackTaskCmd extends NeedsActiveTaskCmd<Void> {

    private static final Logger log = LoggerFactory.getLogger(BackTaskCmd.class);

    private String businessCode;
    private String comment;
    private String nextNode;

    public BackTaskCmd(String taskId, String comment, String nextNode) {
        this(null,taskId,comment,nextNode);
    }

    public BackTaskCmd(String businessCode, String taskId, String comment, String nextNode) {
        super(taskId);
        this.businessCode = businessCode;
        this.comment = comment;
        this.nextNode = nextNode;
    }

    @Override
    protected Void execute(CommandContext commandContext, TaskEntity task) {
        ProcessEngineConfigurationImpl processEngineConfiguration = CommandContextUtil.getProcessEngineConfiguration(commandContext);

        ExecutionEntity processInstanceEntity = CommandContextUtil.getExecutionEntityManager(commandContext).findById(task.getProcessInstanceId());
        if (businessCode != null && !StringUtils.equals(processInstanceEntity.getReferenceId(),businessCode)) {
            throw new FlowableException("业务编码错误");
        }

        User user = CommandContextUtils.findUser(commandContext, Authentication.getAuthenticatedUserId());

        String backTaskComment = String.format("%s 回退了任务，原因：%s", user.getDisplayName(), StringUtils.defaultString(comment));
        // 添加评语
        processEngineConfiguration.getTaskService().addComment(taskId,
                task.getProcessInstanceId(),
                backTaskComment);
        // TODO: 2020/12/21 实现复杂情况下的回退，目前是将所有任务回退， 如果有并行分支可能会出现问题

        List<Task> tasks = processEngineConfiguration.getTaskService()
                .createTaskQuery().processInstanceId(task.getProcessInstanceId()).list();

        processEngineConfiguration.getRuntimeService().createChangeActivityStateBuilder()
                .processInstanceId(task.getProcessInstanceId())
                .moveActivityIdsToSingleActivityId(tasks.stream().map(TaskInfo::getTaskDefinitionKey).collect(Collectors.toList()), nextNode)
                .changeState();

        return null;
    }
}
