package com.realfinance.sofa.flow.flowable.cmd;

import com.realfinance.sofa.flow.util.CommandContextUtils;
import com.realfinance.sofa.flow.util.FlowUtils;
import org.flowable.common.engine.impl.identity.Authentication;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.flowable.engine.impl.util.CommandContextUtil;
import org.flowable.idm.api.User;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class ClaimTaskCmd extends org.flowable.engine.impl.cmd.ClaimTaskCmd {

    private static final Logger log = LoggerFactory.getLogger(ClaimTaskCmd.class);

    public ClaimTaskCmd(String taskId) {
        super(taskId, Authentication.getAuthenticatedUserId());
    }

    @Override
    protected Void execute(CommandContext commandContext, TaskEntity task) {
        ProcessEngineConfigurationImpl processEngineConfiguration = CommandContextUtil.getProcessEngineConfiguration(commandContext);
        User user = CommandContextUtils.findUser(commandContext, userId);
        String claimComment = String.format("%s 于 %s 签收了任务", user.getDisplayName(), FlowUtils.MINUTE_DATE_TIME_FORMATTER.format(LocalDateTime.now()));
        processEngineConfiguration.getTaskService()
                .addComment(taskId,task.getProcessInstanceId(),claimComment);
        super.execute(commandContext, task);
        return null;
    }
}
