package com.realfinance.sofa.flow.flowable.listener;

import com.realfinance.sofa.common.datascope.DataScopeUtils;
import com.realfinance.sofa.flow.domain.CallbackType;
import com.realfinance.sofa.flow.model.FlowCallbackRequest;
import com.realfinance.sofa.flow.util.CallbackUtils;
import com.realfinance.sofa.flow.util.CommandContextUtils;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.common.engine.impl.interceptor.CommandContextCloseListener;
import org.flowable.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class ProcessEndListener implements CommandContextCloseListener {

    private static final Logger log = LoggerFactory.getLogger(ProcessEndListener.class);

    protected ProcessInstance processInstance;

    public ProcessEndListener(ProcessInstance processInstance) {
        this.processInstance = Objects.requireNonNull(processInstance);
    }

    @Override
    public void closing(CommandContext commandContext) {

    }

    @Override
    public void afterSessionsFlush(CommandContext commandContext) {
        if (log.isTraceEnabled()) {
            log.trace("进入流程结束监听");
        }
        if (!CommandContextUtils.processIsEnd(commandContext,processInstance.getId())) {
            return;
        }

        try {
            CallbackType callbackType = CallbackUtils.getCallbackType(processInstance.getCallbackType());
            FlowCallbackRequest request = new FlowCallbackRequest();
            request.setTenantId(DataScopeUtils.loadTenantId());
            request.setBusinessKey(processInstance.getBusinessKey());
            request.setBusinessCode(processInstance.getReferenceId());
            request.setType(FlowCallbackRequest.TYPE_END);
            callbackType.sendCallbackRequest(processInstance.getCallbackId(), request);
        } catch (Exception e) {
            log.error("发送流程结束回调异常，processInstanceId：{}，msg：{}",processInstance.getId(),e.getMessage());
            log.error("发送流程结束回调异常",e);
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
        return 60;
    }

    @Override
    public boolean multipleAllowed() {
        return false;
    }
}
