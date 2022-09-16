package com.realfinance.sofa.flow.flowable.behavior;

import org.apache.commons.collections.CollectionUtils;
import org.flowable.bpmn.model.Activity;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.impl.bpmn.behavior.AbstractBpmnActivityBehavior;
import org.flowable.engine.impl.bpmn.behavior.ParallelMultiInstanceBehavior;
import org.flowable.engine.impl.persistence.entity.ExecutionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Iterator;

import static com.realfinance.sofa.flow.flowable.FlowConstants.ASSIGNEES_VARIABLE_KEY;
import static com.realfinance.sofa.flow.flowable.FlowConstants.ASSIGNEE_VARIABLE_KEY;

public class CustomParallelMultiInstanceBehavior extends ParallelMultiInstanceBehavior {

    public static final Logger log = LoggerFactory.getLogger(CustomParallelMultiInstanceBehavior.class);

    public CustomParallelMultiInstanceBehavior(Activity activity, AbstractBpmnActivityBehavior innerActivityBehavior) {
        super(activity, innerActivityBehavior);
    }

    @Override
    @SuppressWarnings("rawtypes")
    protected int resolveLoopCardinality(DelegateExecution execution) {
        Collection assignees = getAssignees(execution);
        if (CollectionUtils.isEmpty(assignees)) {
            return super.resolveLoopCardinality(execution);
        } else {
            return assignees.size();
        }
    }

    @Override
    @SuppressWarnings("rawtypes")
    protected Object resolveCollection(DelegateExecution execution) {
        Collection assignees = getAssignees(execution);
        if (CollectionUtils.isEmpty(assignees)) {
            return super.resolveCollection(execution);
        } else {
            return assignees;
        }
    }

    @Override
    @SuppressWarnings("rawtypes")
    protected void executeOriginalBehavior(DelegateExecution execution, ExecutionEntity multiInstanceRootExecution, int loopCounter) {
        Collection assignees = getAssignees(execution);
        if (CollectionUtils.isNotEmpty(assignees)) {
            Object value = null;
            int index = 0;
            Iterator it = assignees.iterator();
            while (index <= loopCounter) {
                value = it.next();
                index++;
            }
            setLoopVariable(execution, ASSIGNEE_VARIABLE_KEY, value);
        }
        super.executeOriginalBehavior(execution, multiInstanceRootExecution, loopCounter);
    }

    @SuppressWarnings("rawtypes")
    protected Collection getAssignees(DelegateExecution execution) {
        Object value = execution.getVariableLocal(ASSIGNEES_VARIABLE_KEY);
        DelegateExecution parent = execution.getParent();
        while (value == null && parent != null) {
            value = parent.getVariableLocal(ASSIGNEES_VARIABLE_KEY);
            parent = parent.getParent();
        }
        if (log.isTraceEnabled()) {
            log.trace("Execution({})获取处理人：{}",execution.getId(),value);
        }
        if (value instanceof Collection) {
            return (Collection) value;
        } else {
            return null;
        }
    }
}
