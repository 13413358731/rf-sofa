package com.realfinance.sofa.flow.flowable.cmd;

import com.realfinance.sofa.flow.util.CommandContextUtils;
import org.apache.commons.lang3.StringUtils;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.impl.identity.Authentication;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.flowable.engine.impl.persistence.entity.ExecutionEntity;
import org.flowable.engine.impl.util.CommandContextUtil;
import org.flowable.idm.api.User;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;

public class DelegateTaskCmd extends org.flowable.engine.impl.cmd.DelegateTaskCmd {

    protected String businessCode;
    protected String comment;

    public DelegateTaskCmd(String taskId, String targetUserId, String comment) {
        this(null,taskId,targetUserId,comment);
    }

    public DelegateTaskCmd(String businessCode, String taskId, String targetUserId, String comment) {
        super(taskId, targetUserId);
        this.businessCode = businessCode;
        this.comment = comment;
    }

    @Override
    protected Void execute(CommandContext commandContext, TaskEntity task) {
        ProcessEngineConfigurationImpl processEngineConfiguration = CommandContextUtil.getProcessEngineConfiguration(commandContext);

        ExecutionEntity processInstanceEntity = CommandContextUtil.getExecutionEntityManager(commandContext).findById(task.getProcessInstanceId());
        if (businessCode != null && !StringUtils.equals(processInstanceEntity.getReferenceId(),businessCode)) {
            throw new FlowableException("业务编码错误");
        }

        if (!StringUtils.equals(Authentication.getAuthenticatedUserId(), task.getAssignee())) {
            throw new FlowableException("非任务办理人不能委派");
        }

        User user = CommandContextUtils.findUser(commandContext, Authentication.getAuthenticatedUserId());

        User targetUser = CommandContextUtils.findUser(commandContext, userId);

        String delegateTaskComment = String.format("%s 委派任务给 %s，原因：%s", user.getDisplayName(), targetUser.getDisplayName(), StringUtils.defaultString(comment));
        // 添加评语
        processEngineConfiguration.getTaskService().addComment(taskId,
                task.getProcessInstanceId(),
                delegateTaskComment);

        super.execute(commandContext, task);
        return null;
    }
}
