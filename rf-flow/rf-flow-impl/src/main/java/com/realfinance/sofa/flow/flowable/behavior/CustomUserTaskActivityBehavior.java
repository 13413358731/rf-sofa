package com.realfinance.sofa.flow.flowable.behavior;

import org.flowable.bpmn.model.UserTask;
import org.flowable.common.engine.impl.el.ExpressionManager;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.flowable.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.flowable.task.service.TaskService;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

import static com.realfinance.sofa.flow.flowable.FlowConstants.*;

public class CustomUserTaskActivityBehavior extends UserTaskActivityBehavior {

    public static final Logger log = LoggerFactory.getLogger(CustomUserTaskActivityBehavior.class);

    public CustomUserTaskActivityBehavior(UserTask userTask) {
        super(userTask);
    }

    @Override
    protected void handleAssignments(TaskService taskService, String assignee, String owner, List<String> candidateUsers, List<String> candidateGroups, TaskEntity task, ExpressionManager expressionManager, DelegateExecution execution, ProcessEngineConfigurationImpl processEngineConfiguration) {

        Object nextAssignee = execution.getVariableLocal(ASSIGNEE_VARIABLE_KEY);
        if (nextAssignee != null) {
            assignee = nextAssignee.toString();
        }

        Object nextPriority = execution.getVariableLocal(PRIORITY_VARIABLE_KEY);
        if (nextPriority instanceof Integer) {
            task.setPriority((Integer) nextPriority);
        }

        Object nextDueDate = execution.getVariableLocal(DUE_DATE_VARIABLE_KEY);
        if (nextDueDate instanceof Long) {
            task.setDueDate(new Date((Long) nextDueDate));
        }
        super.handleAssignments(taskService, assignee, owner, candidateUsers, candidateGroups, task, expressionManager, execution, processEngineConfiguration);
    }
}
