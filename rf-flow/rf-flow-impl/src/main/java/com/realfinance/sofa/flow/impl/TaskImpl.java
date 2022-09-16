package com.realfinance.sofa.flow.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.realfinance.sofa.common.datascope.DataScopeUtils;
import com.realfinance.sofa.flow.domain.Biz;
import com.realfinance.sofa.flow.facade.TaskFacade;
import com.realfinance.sofa.flow.flowable.FlowConstants;
import com.realfinance.sofa.flow.flowable.cmd.ClaimTaskCmd;
import com.realfinance.sofa.flow.flowable.cmd.CompleteTaskCmd;
import com.realfinance.sofa.flow.model.HistoricTaskInstanceQueryCriteria;
import com.realfinance.sofa.flow.model.ProcessInstanceDto;
import com.realfinance.sofa.flow.model.TaskDto;
import com.realfinance.sofa.flow.model.TaskQueryCriteria;
import com.realfinance.sofa.flow.repository.BizRepository;
import org.flowable.engine.*;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ExecutionQuery;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.NativeTaskQuery;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.task.api.history.HistoricTaskInstanceQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.realfinance.sofa.flow.util.ExceptionUtils.businessException;
import static com.realfinance.sofa.flow.util.ExceptionUtils.entityNotFound;
import static com.realfinance.sofa.flow.util.QueryCriteriaUtils.*;

@Service
@SofaService(interfaceType = TaskFacade.class, uniqueId = "${service.rf-flow.id}",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "jvm")
        })
@Transactional(readOnly = true)
public class TaskImpl implements TaskFacade {

    public static final Logger log = LoggerFactory.getLogger(TaskImpl.class);

    private final BizRepository bizRepository;
    private final TaskService taskService;
    private final HistoryService historyService;
    private final RuntimeService runtimeService;
    private final IdentityService identityService;
    private final ManagementService managementService;

    public TaskImpl(BizRepository bizRepository,
                    TaskService taskService,
                    HistoryService historyService,
                    RuntimeService runtimeService,
                    IdentityService identityService,
                    ManagementService managementService) {
        this.bizRepository = bizRepository;
        this.taskService = taskService;
        this.historyService = historyService;
        this.runtimeService = runtimeService;
        this.identityService = identityService;
        this.managementService = managementService;
    }


    @Override
    public Page<TaskDto> list(TaskQueryCriteria queryCriteria, Pageable pageable) {
        TaskQuery taskQuery = setQueryCriteria(taskService.createTaskQuery(), queryCriteria);
        /*if (!DataScopeUtils.loadSkipValidateTenantId()) {
            taskQuery.taskTenantId(DataScopeUtils.loadTenantId());
        }*/
        taskQuery.or().
                taskCandidateOrAssigned(queryCriteria.getCandidateOrAssigned()).
                taskCandidateGroupIn(queryCriteria.getCandidateGroupIn());
        long count = taskQuery.count();
        if (count > 0) {
            setSort(taskQuery, pageable.getSort());
            if (queryCriteria.getReferenceIds() != null && queryCriteria.getReferenceIds() != "") {
                List<Task> tasks = taskQuery.list();
                List<TaskDto> content = tasks.stream().map(this::toTaskDto).collect(Collectors.toList());
                String[] referenceId = queryCriteria.getReferenceIds().split(",");
                List<TaskDto> list = new ArrayList<>();
                for (String id : referenceId) {
                    if (id.length() != 0) {
                        List<TaskDto> collect = content.stream().filter(e -> e.getProcessInstance().getReferenceId().equals(id)).collect(Collectors.toList());
                        list.addAll(collect);
                    }
                }
                content.retainAll(list);
                //判断当前分页是否有数据
                if (content.size() > (int) pageable.getOffset()) {
                    //判断当前分页数据 是否大于10条
                    if ((content.size() - (int) pageable.getOffset()) > ((int) pageable.getOffset() + pageable.getPageSize())) {
                        content.subList((int) pageable.getOffset(), pageable.getPageSize());
                        return new PageImpl<>(content, pageable, pageable.getPageSize());
                    } else {
                        content.subList((int) pageable.getOffset(), content.size());
                        return new PageImpl<>(content, pageable, (content.size() - (int) pageable.getOffset()));
                    }
                } else {
                    return Page.empty(pageable);
                }
            }
            else {
                List<Task> tasks = taskQuery.listPage((int) pageable.getOffset(), pageable.getPageSize());
                List<TaskDto> content = tasks.stream().map(this::toTaskDto).collect(Collectors.toList());
                return new PageImpl<>(content, pageable, count);
            }
        } else {
            return Page.empty(pageable);
        }
    }

    @Override
    public Page<TaskDto> listHistory(HistoricTaskInstanceQueryCriteria queryCriteria, Pageable pageable) {
        HistoricTaskInstanceQuery historicTaskInstanceQuery = setQueryCriteria(historyService.createHistoricTaskInstanceQuery(), queryCriteria);
        if (!DataScopeUtils.loadSkipValidateTenantId()) {
            historicTaskInstanceQuery.taskTenantId(DataScopeUtils.loadTenantId());
        }
        long count = historicTaskInstanceQuery.count();
        if (count > 0) {
            setSort(historicTaskInstanceQuery, pageable.getSort());
            List<HistoricTaskInstance> historicTaskInstances = historicTaskInstanceQuery.listPage((int) pageable.getOffset(), pageable.getPageSize());
            List<TaskDto> content = historicTaskInstances.stream().map(this::toTaskDto).collect(Collectors.toList());
            return new PageImpl<>(content, pageable, count);
        } else {
            return Page.empty(pageable);
        }
    }

    @Override
    public String getBizUrl(String id) {
        Objects.requireNonNull(id);
        HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery()
                .taskId(id)
                .singleResult();
        if (historicTaskInstance == null) {
            throw businessException("找不到任务[" + id + "]");
        }
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(historicTaskInstance.getProcessInstanceId())
                .singleResult();
        if (FlowConstants.PROCESS_INSTANCE_REFERENCE_TYPE.equals(historicProcessInstance.getReferenceType())) {
            String bizCode = historicProcessInstance.getReferenceId();
            Biz biz = bizRepository.findByCode(bizCode).orElseThrow(() -> entityNotFound(Biz.class, "code", bizCode));
            String url = biz.getUrl();
            ExpressionParser elParser = new SpelExpressionParser();
            Expression expression = elParser.parseExpression(url, new TemplateParserContext());
            StandardEvaluationContext context = new StandardEvaluationContext(historyService);
            context.setVariable("businessCode", historicProcessInstance.getReferenceId());
            context.setVariable("businessKey", historicProcessInstance.getBusinessKey());
            context.setVariable("taskId", historicTaskInstance.getId());
            context.setVariable("processInstance", historicProcessInstance.getId());
            return expression.getValue(context, String.class);
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void claim(String id) {
        Objects.requireNonNull(id);
        String principalId = DataScopeUtils.loadPrincipalId().map(String::valueOf).orElse(null);
        identityService.setAuthenticatedUserId(principalId);
        try {
            managementService.executeCommand(new ClaimTaskCmd(id));
        } catch (Exception e) {
            log.error("签收任务异常", e);
            throw businessException("签收任务失败，原因：" + e.getMessage());
        } finally {
            identityService.setAuthenticatedUserId(null);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void complete(String businessCode, String id) {
        Objects.requireNonNull(businessCode);
        Objects.requireNonNull(id);
        String principalId = DataScopeUtils.loadPrincipalId().map(String::valueOf).orElse(null);
        identityService.setAuthenticatedUserId(principalId);
        try {
            managementService.executeCommand(new CompleteTaskCmd(businessCode, id, "", null, null));
        } catch (Exception e) {
            log.error("完成任务异常", e);
            throw businessException("完成任务失败，原因：" + e.getMessage());
        } finally {
            identityService.setAuthenticatedUserId(null);
        }
    }

    private TaskDto toTaskDto(Task e) {
        TaskDto taskDto = new TaskDto();
        taskDto.setId(e.getId());
        taskDto.setName(e.getName());
        taskDto.setCreateTime(e.getCreateTime());
        taskDto.setTaskDefinitionKey(e.getTaskDefinitionKey());
        taskDto.setDueDate(e.getDueDate());
        taskDto.setPriority(e.getPriority());
        taskDto.setExecutionId(e.getExecutionId());
        if (e.getDelegationState() != null) {
            taskDto.setDelegateStatus(e.getDelegationState().name());
        }
        taskDto.setClaimTime(e.getClaimTime());
        if (e.getOwner() != null) {
            taskDto.setOwner(Integer.parseInt(e.getOwner()));
        }
        if (e.getAssignee() != null) {
            taskDto.setAssignee(Integer.parseInt(e.getAssignee()));
        }

        if (e.getProcessInstanceId() != null) {
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                    .processInstanceId(e.getProcessInstanceId())
                    .singleResult();
            ProcessInstanceDto processInstanceDto = new ProcessInstanceDto();
            processInstanceDto.setId(processInstance.getId());
            processInstanceDto.setName(processInstance.getName());
            processInstanceDto.setBusinessKey(processInstance.getBusinessKey());
            if (processInstance.getStartUserId() != null) {
                processInstanceDto.setStartUserId(Integer.parseInt(processInstance.getStartUserId()));
            }
            processInstanceDto.setStartTime(processInstance.getStartTime());
            processInstanceDto.setSuspended(processInstance.isSuspended());
            processInstanceDto.setReferenceType(processInstance.getReferenceType());
            processInstanceDto.setReferenceId(processInstance.getReferenceId());
            taskDto.setProcessInstance(processInstanceDto);
        }
        return taskDto;
    }

    private TaskDto toTaskDto(HistoricTaskInstance e) {
        TaskDto taskDto = new TaskDto();
        taskDto.setId(e.getId());
        taskDto.setName(e.getName());
        taskDto.setCreateTime(e.getCreateTime());
        taskDto.setEndTime(e.getEndTime());
        taskDto.setTaskDefinitionKey(e.getTaskDefinitionKey());
        taskDto.setDueDate(e.getDueDate());
        taskDto.setPriority(e.getPriority());
        taskDto.setExecutionId(e.getExecutionId());
        taskDto.setClaimTime(e.getClaimTime());
        if (e.getOwner() != null) {
            taskDto.setOwner(Integer.parseInt(e.getOwner()));
        }
        if (e.getAssignee() != null) {
            taskDto.setAssignee(Integer.parseInt(e.getAssignee()));
        }

        if (e.getProcessInstanceId() != null) {
            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                    .processInstanceId(e.getProcessInstanceId())
                    .singleResult();
            ProcessInstanceDto processInstanceDto = new ProcessInstanceDto();
            processInstanceDto.setId(historicProcessInstance.getId());
            processInstanceDto.setName(historicProcessInstance.getName());
            processInstanceDto.setBusinessKey(historicProcessInstance.getBusinessKey());
            if (historicProcessInstance.getStartUserId() != null) {
                processInstanceDto.setStartUserId(Integer.parseInt(historicProcessInstance.getStartUserId()));
            }
            processInstanceDto.setStartTime(historicProcessInstance.getStartTime());
            processInstanceDto.setEndTime(historicProcessInstance.getEndTime());
            processInstanceDto.setEndActivityId(historicProcessInstance.getEndActivityId());
            taskDto.setProcessInstance(processInstanceDto);
        }
        return taskDto;
    }

}
