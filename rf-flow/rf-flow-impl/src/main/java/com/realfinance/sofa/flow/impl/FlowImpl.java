package com.realfinance.sofa.flow.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.realfinance.sofa.common.datascope.DataScopeUtils;
import com.realfinance.sofa.flow.domain.Biz;
import com.realfinance.sofa.flow.domain.BizModel;
import com.realfinance.sofa.flow.exception.RfFlowException;
import com.realfinance.sofa.flow.facade.FlowFacade;
import com.realfinance.sofa.flow.flowable.FlowConstants;
import com.realfinance.sofa.flow.flowable.cmd.*;
import com.realfinance.sofa.flow.model.*;
import com.realfinance.sofa.flow.repository.BizModelRepository;
import com.realfinance.sofa.flow.repository.BizRepository;
import com.realfinance.sofa.flow.util.ExceptionUtils;
import com.realfinance.sofa.flow.util.FlowableUtils;
import com.realfinance.sofa.flow.util.SystemQuery;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.UserTask;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.impl.util.IoUtil;
import org.flowable.engine.*;
import org.flowable.engine.form.StartFormData;
import org.flowable.engine.form.TaskFormData;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.history.HistoricProcessInstanceQuery;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.task.Comment;
import org.flowable.task.api.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

import static com.realfinance.sofa.flow.util.ExceptionUtils.businessException;
import static com.realfinance.sofa.flow.util.ExceptionUtils.entityNotFound;

@Service
@SofaService(interfaceType = FlowFacade.class, uniqueId = "${service.rf-flow.id}",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "jvm")
        })
@Transactional
public class FlowImpl implements FlowFacade {
    private static final Logger log = LoggerFactory.getLogger(FlowImpl.class);

    private final BizRepository bizRepository;
    private final BizModelRepository bizModelRepository;
    private final RuntimeService runtimeService;
    private final FormService formService;
    private final TaskService taskService;
    private final IdentityService identityService;
    private final RepositoryService repositoryService;
    private final HistoryService historyService;
    private final ManagementService managementService;

    public FlowImpl(BizRepository bizRepository,
                    BizModelRepository bizModelRepository,
                    RuntimeService runtimeService,
                    FormService formService,
                    TaskService taskService,
                    IdentityService identityService,
                    RepositoryService repositoryService,
                    HistoryService historyService,
                    ManagementService managementService) {
        this.bizRepository = bizRepository;
        this.bizModelRepository = bizModelRepository;
        this.runtimeService = runtimeService;
        this.formService = formService;
        this.taskService = taskService;
        this.identityService = identityService;
        this.repositoryService = repositoryService;
        this.historyService = historyService;
        this.managementService = managementService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String startProcess(String businessCode, String businessKey, Map<String, String> formData,String name) throws RfFlowException {
        Objects.requireNonNull(businessCode);
        Objects.requireNonNull(businessKey);
        BizModel bizModel = findBizModel(businessCode);
        // 新启动流程实例
        identityService.setAuthenticatedUserId(DataScopeUtils.loadPrincipalId().map(String::valueOf).orElse(null));
        try {
            ProcessInstance processInstance = managementService.executeCommand(new StartProcessCmd(bizModel,businessKey,formData,name));
            return processInstance.getId();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("启动流程异常", e);
            }
            throw businessException("启动流程失败，原因：" + e.getMessage(),e);
        } finally {
            identityService.setAuthenticatedUserId(null);
        }
    }

    @Override
    public String startProcessToUserId(@NotNull String businessCode, @NotNull String businessKey, Map<String, String> formData, Integer userId,String name) throws RfFlowException {
        Objects.requireNonNull(businessCode);
        Objects.requireNonNull(businessKey);
        BizModel bizModel = null;
        //判断是走默认业务模型还是正常业务模型
        if(isDefault(businessCode)){
            bizModel= findDefaultBizModel(businessCode);
        }else{
            bizModel= findBizModel(businessCode);
        }
        // 新启动流程实例
        identityService.setAuthenticatedUserId(userId.toString());
        try {
            ProcessInstance processInstance = managementService.executeCommand(new StartProcessCmd(bizModel,businessKey,formData,name,isDefault(businessCode)));
            return processInstance.getId();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("启动流程异常", e);
            }
            throw businessException("启动流程失败，原因：" + e.getMessage(),e);
        } finally {
            identityService.setAuthenticatedUserId(null);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteProcess(String businessCode, String processInstanceId, String comment) {
        Objects.requireNonNull(businessCode);
        Objects.requireNonNull(processInstanceId);
        identityService.setAuthenticatedUserId(DataScopeUtils.loadPrincipalId().map(String::valueOf).orElse(null));
        try {
            managementService.executeCommand(new DeleteProcessCmd(businessCode,processInstanceId,comment));
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("删除流程异常", e);
            }
            throw businessException("删除流程失败，原因：" + e.getMessage(),e);
        } finally {
            identityService.setAuthenticatedUserId(null);
        }
    }

    @Override
    public byte[] getProcessDiagram(String businessCode, String businessKey) {
        Objects.requireNonNull(businessCode);
        Objects.requireNonNull(businessKey);
        try {
            InputStream inputStream = managementService.executeCommand(new GetProcessDiagramCmd(businessCode, businessKey));
            if (inputStream == null) {
                return null;
            }
            return IoUtil.readInputStream(inputStream,"流程图");
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("查询流程图异常", e);
            }
            throw businessException("查询流程图失败，原因：" + e.getMessage(),e);
        }
    }

    @Override
    public List<HistoricActivityInstanceDto> listHistoricActivityInstance(String businessCode, String businessKey, boolean latestProcessInstance) {
        Objects.requireNonNull(businessCode);
        Objects.requireNonNull(businessKey);
        // 根据业务编码和业务主键查询流程实例历史
        HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery()
                .processInstanceReferenceType(FlowConstants.PROCESS_INSTANCE_REFERENCE_TYPE)
                .processInstanceReferenceId(businessCode)
                .processInstanceBusinessKey(businessKey)
                .processInstanceTenantId(DataScopeUtils.loadTenantId())
                .orderByProcessInstanceStartTime()
                .desc();

        List<HistoricProcessInstance> historicProcessInstances;
        if (latestProcessInstance) {
            historicProcessInstances = historicProcessInstanceQuery.listPage(0,1);
        } else {
            historicProcessInstances = historicProcessInstanceQuery.list();
        }

        if (historicProcessInstances.isEmpty()) {
            throw businessException("找不到流程实例");
        }

        List<HistoricActivityInstanceDto> actList = new ArrayList<>();

        for (HistoricProcessInstance historicProcessInstance : historicProcessInstances) {

            List<HistoricActivityInstance> historicActivityInstances = historyService.createHistoricActivityInstanceQuery()
                    .processInstanceId(historicProcessInstance.getId())
                    .orderByHistoricActivityInstanceStartTime()
                    .asc()
                    .list();

            for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
                if ("userTask".equals(historicActivityInstance.getActivityType())
                        || "startEvent".equals(historicActivityInstance.getActivityType())
                        || "endEvent".equals(historicActivityInstance.getActivityType())) {
                    // 给节点增加一个序号
                    HistoricActivityInstanceDto historicActivityInstanceDto = new HistoricActivityInstanceDto();
                    historicActivityInstanceDto.setActivityName(historicActivityInstance.getActivityName());
                    historicActivityInstanceDto.setStartTime(historicActivityInstance.getStartTime());
                    historicActivityInstanceDto.setEndTime(historicActivityInstance.getEndTime());
                    historicActivityInstanceDto.setActivityType(historicActivityInstance.getActivityType());
                    historicActivityInstanceDto.setDurationInMillis(historicActivityInstance.getDurationInMillis());
                    historicActivityInstanceDto.setDeleteReason(historicActivityInstance.getDeleteReason());
                    if ("startEvent".equals(historicActivityInstance.getActivityType())) {
                        if (StringUtils.isEmpty(historicActivityInstanceDto.getActivityName())) {
                            historicActivityInstanceDto.setActivityName("流程开始");
                        }
                        if (StringUtils.isNotBlank(historicProcessInstance.getStartUserId())) {
                            historicActivityInstanceDto.setAssignee(historicProcessInstance.getStartUserId());
                        }
                    } else if ("userTask".equals(historicActivityInstance.getActivityType())) {
                        // 获取任务执行人名称
                        if (StringUtils.isNotBlank(historicActivityInstance.getAssignee())) {
                            historicActivityInstanceDto.setAssignee(historicActivityInstance.getAssignee());
                        }
                        // 获取意见评论内容 和 附件
                        if (StringUtils.isNotBlank(historicActivityInstance.getTaskId())) {
                            List<Comment> commentList = taskService.getTaskComments(historicActivityInstance.getTaskId());
                            if (commentList.size() > 0) {
                                Collections.reverse(commentList);
                                List<CommentDto> flowComments = new ArrayList<>();
                                commentList.forEach(comment -> {
                                    CommentDto commentDto = new CommentDto();
                                    commentDto.setTime(comment.getTime());
                                    commentDto.setTaskId(comment.getTaskId());
                                    if (comment.getUserId() != null) {
                                        commentDto.setUserId(Integer.parseInt(comment.getUserId()));
                                    }
//                                    commentDto.setMessage(((CommentEntityImpl) comment).getMessage());
                                    commentDto.setFullMessage(comment.getFullMessage());

                                    flowComments.add(commentDto);
                                });
                                historicActivityInstanceDto.setComments(flowComments);
                            }
                        }
                    } else if ("endEvent".equals(historicActivityInstance.getActivityType())) {
                        if (StringUtils.isEmpty(historicActivityInstanceDto.getActivityName())) {
                            historicActivityInstanceDto.setActivityName("流程结束");
                        }
                    }
                    actList.add(historicActivityInstanceDto);
                }
            }
        }
        return actList;
    }

    @Override
    public Object getStartForm(String businessCode) {
        BizModel bizModel = findBizModel(businessCode);
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(bizModel.getModelKey())
                .processDefinitionTenantId(DataScopeUtils.loadTenantId())
                .latestVersion()
                .singleResult();
        if (processDefinition == null) {
            throw businessException("找不到流程定义");
        }
        StartFormData startFormData = formService.getStartFormData(processDefinition.getId());
        return managementService.executeCommand(new GetFormPropertiesJsonCmd(startFormData));
    }

    @Override
    public Object getTaskForm(String businessCode, String taskId) {
        TaskFormData taskFormData = formService.getTaskFormData(taskId);
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(taskFormData.getTask().getProcessInstanceId())
                .processInstanceReferenceType(FlowConstants.PROCESS_INSTANCE_REFERENCE_TYPE)
                .processInstanceReferenceId(businessCode)
                .processInstanceTenantId(DataScopeUtils.loadTenantId())
                .singleResult();
        if (processInstance == null) {
            throw businessException("找不到流程");
        }
        return managementService.executeCommand(new GetFormPropertiesJsonCmd(taskFormData));
    }

    @Override
    public ProcessInstanceDto getProcessInstance(String businessCode, String businessKey) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceReferenceType(FlowConstants.PROCESS_INSTANCE_REFERENCE_TYPE)
                .processInstanceReferenceId(businessCode)
                .processInstanceBusinessKey(businessKey)
                .processInstanceTenantId(DataScopeUtils.loadTenantId())
                .singleResult();
        if (processInstance == null) {
            return null;
        }

        ProcessInstanceDto processInstanceDto = new ProcessInstanceDto();
        processInstanceDto.setId(processInstance.getId());
        processInstanceDto.setName(processInstance.getName());
        processInstanceDto.setBusinessKey(processInstance.getBusinessKey());
        if (processInstance.getStartUserId() != null) {
            processInstanceDto.setStartUserId(Integer.parseInt(processInstance.getStartUserId()));
        }
        processInstanceDto.setStartTime(processInstance.getStartTime());
        processInstanceDto.setSuspended(processInstance.isSuspended());
        return processInstanceDto;
    }

    @Override
    public TaskDto getTask(String processInstanceId) {
        identityService.setAuthenticatedUserId(DataScopeUtils.loadPrincipalId().map(String::valueOf).orElse(null));
        try {
            List<TaskDto> tasks = managementService.executeCommand(new GetTaskDtoByProcessInstanceIdCmd(processInstanceId));
            if (tasks.isEmpty()) {
                return null;
            }
            return tasks.get(0);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("查询任务异常", e);
            }
            throw businessException("查询任务失败，原因：" + e.getMessage(),e);
        } finally {
            identityService.setAuthenticatedUserId(null);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void completeTask(String businessCode, String taskId, String comment, NextUserTaskInfo nextUserTaskInfo, Map<String, String> formData) throws RfFlowException {
        Objects.requireNonNull(businessCode);
        Objects.requireNonNull(taskId);
        identityService.setAuthenticatedUserId(DataScopeUtils.loadPrincipalId().map(String::valueOf).orElse(null));
        try {
            managementService.executeCommand(new CompleteTaskCmd(businessCode,taskId,comment,nextUserTaskInfo,formData));
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("完成任务异常", e);
            }
            throw businessException("完成任务失败，原因：" + e.getMessage(),e);
        } finally {
            identityService.setAuthenticatedUserId(null);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resolveTask(String businessCode, String taskId, String comment, Map<String, String> formData) throws RfFlowException {
        Objects.requireNonNull(businessCode);
        Objects.requireNonNull(taskId);
        identityService.setAuthenticatedUserId(DataScopeUtils.loadPrincipalId().map(String::valueOf).orElse(null));
        try {
            managementService.executeCommand(new ResolveTaskCmd(businessCode,taskId,comment,formData));
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("完成委派任务异常", e);
            }
            throw businessException("完成委派任务失败，原因：" + e.getMessage(),e);
        } finally {
            identityService.setAuthenticatedUserId(null);
        }
    }

    @Override
    public List<TaskDto> listBackUserTask(String taskId) {
        // 当前任务 task
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        // 获取流程定义信息
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(task.getProcessDefinitionId()).singleResult();
        // 获取所有节点信息，暂不考虑子流程情况
        Process process = repositoryService.getBpmnModel(processDefinition.getId()).getProcesses().get(0);
        Collection<FlowElement> flowElements = process.getFlowElements();
        // 获取当前任务节点元素
        UserTask source = (UserTask) process.getFlowElement(task.getTaskDefinitionKey());
        // 获取节点的所有路线
        List<List<UserTask>> roads = FlowableUtils.findRoad(source, null, null, null);
        // 可回退的节点列表
        List<UserTask> userTaskList = new ArrayList<>();
        for (List<UserTask> road : roads) {
            if (userTaskList.size() == 0) {
                // 还没有可回退节点直接添加
                userTaskList = road;
            } else {
                // 如果已有回退节点，则比对取交集部分
                userTaskList.retainAll(road);
            }
        }
        return userTaskList.stream().map(e -> {
            TaskDto taskDto = new TaskDto();
            taskDto.setTaskDefinitionKey(e.getId());
            taskDto.setName(e.getName());
            return taskDto;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void backTask(String businessCode, String taskId, String comment, String nextNode) throws RfFlowException {
        Objects.requireNonNull(businessCode);
        Objects.requireNonNull(taskId);
        Objects.requireNonNull(nextNode);
        identityService.setAuthenticatedUserId(DataScopeUtils.loadPrincipalId().map(String::valueOf).orElse(null));
        try {
            managementService.executeCommand(new BackTaskCmd(businessCode,taskId,comment,nextNode));
        } catch (FlowableException e) {
            if (log.isErrorEnabled()) {
                log.error("回退任务异常", e);
            }
            throw businessException("回退任务失败，原因：" + e.getMessage(),e);
        } finally {
            identityService.setAuthenticatedUserId(null);
        }
    }

    @Override
    public void delegateTask(String businessCode, String taskId, String targetUserId, String comment) {
        Objects.requireNonNull(businessCode);
        Objects.requireNonNull(taskId);
        Objects.requireNonNull(targetUserId);
        identityService.setAuthenticatedUserId(DataScopeUtils.loadPrincipalId().map(String::valueOf).orElse(null));
        try {
            managementService.executeCommand(new DelegateTaskCmd(businessCode,taskId,targetUserId,comment));
        } catch (FlowableException e) {
            if (log.isErrorEnabled()) {
                log.error("委派任务异常", e);
            }
            throw businessException("委派任务失败，原因：" + e.getMessage(),e);
        } finally {
            identityService.setAuthenticatedUserId(null);
        }
    }

    @Override
    public void turnTask(String businessCode, String taskId, String targetUserId, String comment) {
        Objects.requireNonNull(businessCode);
        Objects.requireNonNull(taskId);
        Objects.requireNonNull(targetUserId);
        identityService.setAuthenticatedUserId(DataScopeUtils.loadPrincipalId().map(String::valueOf).orElse(null));
        try {
            managementService.executeCommand(new TurnTaskCmd(businessCode,taskId,targetUserId,comment));
        } catch (FlowableException e) {
            if (log.isErrorEnabled()) {
                log.error("转办任务异常", e);
            }
            throw businessException("转办任务失败，原因：" + e.getMessage(),e);
        } finally {
            identityService.setAuthenticatedUserId(null);
        }
    }

    /**
     * 查询业务模型
     * @param businessCode 业务编码
     * @return
     */
    protected BizModel findBizModel(String businessCode) {
        return DataScopeUtils.loadDepartmentId().flatMap(departmentId -> {
                    // 如果部门ID存在 查出业务对象
                    Biz biz = bizRepository.findByCode(businessCode)
                            .orElseThrow(() -> entityNotFound(Biz.class, "code", businessCode));
                    // 先查本部门下是否配置了业务模型
                    return bizModelRepository.findByBizAndDepartmentIdAndDeploymentIdNotNull(biz, departmentId)
                            // 本部门下没有则调用系统接口查询父级部门ID路径，然后查询父级部门中是否配置业务模型
                            .or(() -> {
                                List<Integer> departmentIds = queryDepartmentIdPath(departmentId);
                                if (departmentIds.size() < 2) { // 没有父级部门
                                    return Optional.empty();
                                }
                                List<BizModel> bizModels = bizModelRepository.findByBizAndDepartmentIdInAndDeploymentIdNotNull(biz, departmentIds);
                                Collections.reverse(departmentIds); // 反转数组，从子部门往根部门遍历
                                for (Integer id : departmentIds) {
                                    for (BizModel bizModel : bizModels) {
                                        if (Objects.equals(id, bizModel.getDepartmentId())) {
                                            return Optional.of(bizModel);
                                        }
                                    }
                                }
                                return Optional.empty();
                            });
                })
                // 找不到业务模型 抛出异常
                .orElseThrow(() -> {
                    if (log.isInfoEnabled()) {
                        log.info("找不到业务模型，bizCode：{}，departmentId：{}", businessCode, DataScopeUtils.loadDepartmentId().orElse(null));
                    }
                    return ExceptionUtils.businessException("找不到业务模型");
                });
    }

    /**
     * 查询默认业务模型
     * 用于后端直接执行
     * @param businessCode 业务编码
     * @return
     */
    protected BizModel findDefaultBizModel(String businessCode) {
        Optional<Integer> defaultDepartmentId = Optional.ofNullable(484);
        return defaultDepartmentId.flatMap(departmentId -> {
            // 如果部门ID存在 查出业务对象
            Biz biz = bizRepository.findByCode(businessCode)
                    .orElseThrow(() -> entityNotFound(Biz.class, "code", businessCode));
            // 先查本部门下是否配置了业务模型
            return bizModelRepository.findByBizAndDepartmentIdAndDeploymentIdNotNull(biz, departmentId)
                    // 本部门下没有则调用系统接口查询父级部门ID路径，然后查询父级部门中是否配置业务模型
                    .or(() -> {
                        List<Integer> departmentIds = queryDepartmentIdPath(departmentId);
                        if (departmentIds.size() < 2) { // 没有父级部门
                            return Optional.empty();
                        }
                        List<BizModel> bizModels = bizModelRepository.findByBizAndDepartmentIdInAndDeploymentIdNotNull(biz, departmentIds);
                        Collections.reverse(departmentIds); // 反转数组，从子部门往根部门遍历
                        for (Integer id : departmentIds) {
                            for (BizModel bizModel : bizModels) {
                                if (Objects.equals(id, bizModel.getDepartmentId())) {
                                    return Optional.of(bizModel);
                                }
                            }
                        }
                        return Optional.empty();
                    });
        })
                // 找不到业务模型 抛出异常
                .orElseThrow(() -> {
                    if (log.isInfoEnabled()) {
                        log.info("找不到业务模型，bizCode：{}", businessCode);
                    }
                    return ExceptionUtils.businessException("找不到业务模型");
                });
    }

    /**
     * 判断是否走默认业务模型
     * @param businessCode
     * @return
     */
    protected boolean isDefault(String businessCode) {
        boolean flag = false;
        if("contract".equals(businessCode) || "evaluateReminder".equals(businessCode)){
            flag = true;
        }
        return flag;
    }

    /**
     * 调用系统查询接口查询父级部门ID路径
     * @param departmentId
     * @return
     */
    private List<Integer> queryDepartmentIdPath(Integer departmentId) {
        try {
            return SystemQuery.get().queryDepartmentIdPath(departmentId);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("查询部门ID路径异常", e);
            }
            throw businessException("查询部门ID路径失败",e);
        }
    }
}
