package com.realfinance.sofa.flow.flowable.listener;

import com.realfinance.sofa.common.datascope.DataScopeUtils;
import com.realfinance.sofa.flow.domain.CallbackType;
import com.realfinance.sofa.flow.model.FlowCallbackRequest;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.common.engine.impl.interceptor.CommandContextCloseListener;
import org.flowable.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class ProcessDeleteListener implements CommandContextCloseListener {
    private static final Logger log = LoggerFactory.getLogger(ProcessDeleteListener.class);

    protected ProcessInstance processInstance;
    protected String comment;

    public ProcessDeleteListener(ProcessInstance processInstance, String comment) {
        this.processInstance = Objects.requireNonNull(processInstance);
        this.comment = comment;
    }

    @Override
    public void closing(CommandContext commandContext) {

    }

    @Override
    public void afterSessionsFlush(CommandContext commandContext) {
        try {
            CallbackType callbackType = getCallbackType();
            FlowCallbackRequest request = new FlowCallbackRequest();
            request.setTenantId(DataScopeUtils.loadTenantId());
            request.setBusinessKey(processInstance.getBusinessKey());
            request.setBusinessCode(processInstance.getReferenceId());
            request.setComment(comment);
            request.setType(FlowCallbackRequest.TYPE_DELETE);
            callbackType.sendCallbackRequest(processInstance.getCallbackId(), request);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("发送流程结束回调异常，processInstanceId：{}",processInstance.getId());
                log.error("发送流程结束回调异常",e);
            }
            throw new FlowableException("发送流程结束回调失败", e);
        }
    }

    @Override
    public void closed(CommandContext commandContext) {

    }

    @Override
    public void closeFailure(CommandContext commandContext) {

    }

    @Override
    public Integer order() {
        return 50;
    }

    @Override
    public boolean multipleAllowed() {
        return false;
    }

    protected CallbackType getCallbackType() {
        try {
            return CallbackType.valueOf(processInstance.getCallbackType());
        } catch (Exception e) {
            throw new FlowableIllegalArgumentException("找不到回调类型");
        }
    }
}
