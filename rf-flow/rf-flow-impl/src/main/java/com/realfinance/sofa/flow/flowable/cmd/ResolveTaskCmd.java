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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.realfinance.sofa.flow.util.FlowUtils.MINUTE_DATE_TIME_FORMATTER;

public class ResolveTaskCmd extends org.flowable.engine.impl.cmd.ResolveTaskCmd {

    private static final Logger log = LoggerFactory.getLogger(ResolveTaskCmd.class);

    protected String businessCode;
    protected String comment;

    public ResolveTaskCmd(String businessCode, String taskId, String comment, Map<String, String> formData) {
        super(taskId, formData == null ? null : new HashMap<>(formData));
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

        String authenticatedUserId = Authentication.getAuthenticatedUserId();
        if (!StringUtils.equals(authenticatedUserId, task.getAssignee())) {
            throw new FlowableException("非被委派的任务办理人");
        }

        User user = CommandContextUtils.findUser(commandContext, task.getAssignee());

        User owner =  CommandContextUtils.findUser(commandContext, task.getOwner());

        String resolveComment = String.format("%s 于 %s 完成了 %s 委派的任务，意见：%s",
                user.getDisplayName(),
                MINUTE_DATE_TIME_FORMATTER.format(LocalDateTime.now()),
                owner.getDisplayName(),
                StringUtils.defaultString(comment));
        processEngineConfiguration.getTaskService()
                .addComment(taskId,task.getProcessInstanceId(),resolveComment);

        super.execute(commandContext, task);
        return null;
    }
}
