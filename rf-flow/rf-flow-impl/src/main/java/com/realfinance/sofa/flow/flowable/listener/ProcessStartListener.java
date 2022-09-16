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

public class ProcessStartListener implements CommandContextCloseListener {

    private static final Logger log = LoggerFactory.getLogger(ProcessStartListener.class);

    protected ProcessInstance processInstance;

    private boolean isDefault;

    public ProcessStartListener(ProcessInstance processInstance,boolean isDefault) {
        this.processInstance = Objects.requireNonNull(processInstance);
        this.isDefault = isDefault;
    }

    @Override
    public void closing(CommandContext commandContext) {

    }

    @Override
    public void afterSessionsFlush(CommandContext commandContext) {

    }

    @Override
    public void closed(CommandContext commandContext) {
        if (log.isTraceEnabled()) {
            log.trace("进入流程启动监听");
        }
        try {
            CallbackType callbackType = CallbackUtils
                    .getCallbackType(processInstance.getCallbackType());
            FlowCallbackRequest request = new FlowCallbackRequest();
            if(isDefault){
                request.setTenantId("01");
            }else{
                request.setTenantId(DataScopeUtils.loadTenantId());
            }
            request.setBusinessKey(processInstance.getBusinessKey());
            request.setBusinessCode(processInstance.getReferenceId());
            // 如果流程给开始就结束，就会忽略START回调，直接发送END回调
            if (CommandContextUtils.processIsEnd(commandContext,processInstance.getId())) {
                if (log.isDebugEnabled()) {
                    log.debug("发送流程结束回调，processInstanceId：{}",processInstance.getId());
                }
                request.setType(FlowCallbackRequest.TYPE_END);
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("发送流程开始回调，processInstanceId：{}",processInstance.getId());
                }
                request.setType(FlowCallbackRequest.TYPE_START);
            }
            callbackType.sendCallbackRequest(processInstance.getCallbackId(), request);
        } catch (Exception e) {
            log.error("发送流程回调异常，msg：{}", e.getMessage());
            throw new FlowableException("发送流程回调失败", e);
        }
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
}
