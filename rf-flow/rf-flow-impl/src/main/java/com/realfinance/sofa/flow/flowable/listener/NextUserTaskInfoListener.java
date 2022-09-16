package com.realfinance.sofa.flow.flowable.listener;

import com.realfinance.sofa.flow.model.NextUserTaskInfo;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.flowable.common.engine.api.delegate.event.AbstractFlowableEventListener;
import org.flowable.common.engine.api.delegate.event.FlowableEvent;
import org.flowable.common.engine.api.delegate.event.FlowableEventType;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.event.FlowableActivityEvent;
import org.flowable.engine.delegate.event.FlowableProcessEngineEvent;
import org.flowable.engine.impl.bpmn.behavior.MultiInstanceActivityBehavior;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Objects;

import static com.realfinance.sofa.flow.flowable.FlowConstants.*;
import static com.realfinance.sofa.flow.model.ProcessVariableConstants.NEXT_USER_TASK_INFO;
import static org.flowable.common.engine.api.delegate.event.FlowableEngineEventType.ACTIVITY_STARTED;
import static org.flowable.common.engine.api.delegate.event.FlowableEngineEventType.MULTI_INSTANCE_ACTIVITY_STARTED;

/**
 * 这个监听器用户消费 {@link NextUserTaskInfo}，并且清空掉
 */
public class NextUserTaskInfoListener extends AbstractFlowableEventListener {

    public static final Logger log = LoggerFactory.getLogger(NextUserTaskInfoListener.class);

    @Override
    public void onEvent(FlowableEvent event) {
        if (!isSupport(event.getType())) {
            return;
        }
        // 非用户任务节点跳过
        if (!isUserTask(event)) {
            return;
        }
        DelegateExecution execution = getExecution(event);
        if (execution == null) {
            return;
        }

        Object nextUserTaskInfo = execution.getVariable(NEXT_USER_TASK_INFO);
        if (log.isTraceEnabled()) {
            log.trace("eventType：{}，execution：{}，nextUserTaskInfo：{}",
                    event.getType(), execution, nextUserTaskInfo);
        }
        if (nextUserTaskInfo == null) {
            return;
        }
        if (!(nextUserTaskInfo instanceof NextUserTaskInfo)) {
            if (log.isWarnEnabled()) {
                log.warn("Variable[" + NEXT_USER_TASK_INFO + "]类型错误");
            }
            return;
        }
        NextUserTaskInfo info = (NextUserTaskInfo) nextUserTaskInfo;

        boolean isMultiInstanceBehavior = isMultiInstanceBehavior(event);

        try {
            installNextAssignees(execution,info.getAssignees(),isMultiInstanceBehavior);
            installNextDueDate(execution,info.getDueDate());
            installNextPriority(execution,info.getPriority());
        } finally {
            execution.removeVariable(NEXT_USER_TASK_INFO);
        }
    }



    @Override
    public boolean isFailOnException() {
        return true;
    }

    protected void installNextPriority(DelegateExecution execution, Integer nextPriority) {
        if (nextPriority == null) {
            return;
        }
        setNextUserTaskInfoVariable(execution,PRIORITY_VARIABLE_KEY,nextPriority);
    }

    protected void installNextDueDate(DelegateExecution execution, LocalDateTime nextDueDate) {
        if (nextDueDate == null) {
            return;
        }
        long toEpochMilli = nextDueDate.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        setNextUserTaskInfoVariable(execution,DUE_DATE_VARIABLE_KEY,toEpochMilli);
    }

    protected void installNextAssignees(DelegateExecution execution, Collection<String> nextAssignees, boolean isMultiInstanceBehavior) {
        if (nextAssignees == null) {
            return;
        }

        Object first = ((Collection<?>) nextAssignees).stream().findFirst()
                .orElseThrow(() -> new FlowableIllegalArgumentException("nextAssignees集合必须至少有一个元素"));

        if (isMultiInstanceBehavior) {
            setNextUserTaskInfoVariable(execution,ASSIGNEES_VARIABLE_KEY,nextAssignees);
        } else {
            setNextUserTaskInfoVariable(execution,ASSIGNEE_VARIABLE_KEY,first);
        }
    }

    private boolean isSupport(FlowableEventType eventType) {
        return eventType == ACTIVITY_STARTED || eventType == MULTI_INSTANCE_ACTIVITY_STARTED;
    }

    private boolean isUserTask(FlowableEvent event) {
        return event instanceof FlowableActivityEvent
                && Objects.equals("userTask",((FlowableActivityEvent) event).getActivityType());
    }

    private DelegateExecution getExecution(FlowableEvent event) {
        if (event instanceof FlowableProcessEngineEvent) {
            return ((FlowableProcessEngineEvent) event).getExecution();
        }
        return null;
    }

    private boolean isMultiInstanceBehavior(FlowableEvent event) {
        if (event instanceof FlowableActivityEvent) {
            String behaviorClass = ((FlowableActivityEvent) event).getBehaviorClass();
            try {
                return MultiInstanceActivityBehavior.class.isAssignableFrom(Class.forName(behaviorClass));
            } catch (ClassNotFoundException e) {
                throw new FlowableException("加载Behavior Class异常",e);
            }
        }
        if (log.isErrorEnabled()) {
            log.error("无法判断{}是否多实例行为",event);
        }
        throw new FlowableException("非FlowableActivityEvent");
    }

    private void setNextUserTaskInfoVariable(DelegateExecution execution, String key, Object value) {
        execution.setVariableLocal(key,value);
    }
}
