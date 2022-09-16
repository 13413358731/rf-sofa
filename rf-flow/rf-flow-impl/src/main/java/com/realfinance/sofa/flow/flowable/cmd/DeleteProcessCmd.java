package com.realfinance.sofa.flow.flowable.cmd;

import com.realfinance.sofa.flow.flowable.listener.ProcessDeleteListener;
import com.realfinance.sofa.flow.util.CommandContextUtils;
import org.apache.commons.lang3.StringUtils;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.common.engine.impl.identity.Authentication;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.flowable.engine.impl.cmd.DeleteProcessInstanceCmd;
import org.flowable.engine.impl.persistence.entity.ExecutionEntity;
import org.flowable.engine.impl.util.CommandContextUtil;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.idm.api.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeleteProcessCmd extends DeleteProcessInstanceCmd {

    private static final Logger log = LoggerFactory.getLogger(DeleteProcessCmd.class);

    protected String businessCode;

    public DeleteProcessCmd(String processInstanceId, String deleteReason) {
        this(null,processInstanceId,deleteReason);
    }

    public DeleteProcessCmd(String businessCode, String processInstanceId, String deleteReason) {
        super(processInstanceId, deleteReason);
        this.businessCode = businessCode;
    }

    @Override
    public Void execute(CommandContext commandContext) {
        ProcessEngineConfigurationImpl processEngineConfiguration = CommandContextUtil.getProcessEngineConfiguration(commandContext);

        ExecutionEntity processInstanceEntity = CommandContextUtil.getExecutionEntityManager(commandContext).findById(processInstanceId);
        if (processInstanceEntity == null) {
            throw new FlowableObjectNotFoundException("No process instance found for id '" + processInstanceId + "'", ProcessInstance.class);
        }
        if (businessCode != null && !StringUtils.equals(processInstanceEntity.getReferenceId(), businessCode)) {
            throw new FlowableException("业务编码错误");
        }
        User user = CommandContextUtils.findUser(commandContext, Authentication.getAuthenticatedUserId());
        String deleteProcessComment = String.format("%s 终止了流程，原因：%s", user.getDisplayName(), StringUtils.defaultString(deleteReason));
        processEngineConfiguration.getTaskService().addComment(null, processInstanceId, deleteProcessComment);
        deleteReason = deleteProcessComment;
        super.execute(commandContext);
        commandContext.addCloseListener(new ProcessDeleteListener(processInstanceEntity, deleteProcessComment));
        return null;
    }
}
