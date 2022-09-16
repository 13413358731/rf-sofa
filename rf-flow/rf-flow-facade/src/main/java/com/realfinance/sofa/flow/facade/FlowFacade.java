package com.realfinance.sofa.flow.facade;

import com.realfinance.sofa.flow.exception.RfFlowException;
import com.realfinance.sofa.flow.model.HistoricActivityInstanceDto;
import com.realfinance.sofa.flow.model.NextUserTaskInfo;
import com.realfinance.sofa.flow.model.ProcessInstanceDto;
import com.realfinance.sofa.flow.model.TaskDto;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

public interface FlowFacade {

    /**
     * 启动流程
     * @param businessCode 业务编码
     * @param businessKey 业务主键
     * @param formData 表单数据
     * @param name 流程名称自定义值
     * @return
     * @throws RfFlowException
     */
    String startProcess(@NotNull String businessCode, @NotNull String businessKey, Map<String, String> formData,String name) throws RfFlowException;

    /**
     * 启动流程(自定义流程启动人)
     * @param businessCode 业务编码
     * @param businessKey 业务主键
     * @param formData 表单数据
     * @param userId 启动人id
     * @param name 流程名称自定义值
     * @return
     * @throws RfFlowException
     */
    String startProcessToUserId(@NotNull String businessCode, @NotNull String businessKey, Map<String, String> formData,Integer userId,String name) throws RfFlowException;


    /**
     * 终止流程
     * @param businessCode 业务编码
     * @param processInstanceId 流程实例ID
     * @param comment 终止原因
     */
    void deleteProcess(@NotNull String businessCode, @NotNull String processInstanceId, String comment);

    /**
     * 获取流程图
     * @param businessCode 业务编码
     * @param businessKey 业务主键
     * @return
     */
    byte[] getProcessDiagram(String businessCode, String businessKey);

    /**
     * 获取流程流转历史列表
     * @param businessCode 业务编码
     * @param businessKey 业务主键
     * @param latestProcessInstance 最后一次流程实例
     * @return
     */
    List<HistoricActivityInstanceDto> listHistoricActivityInstance(String businessCode, String businessKey, boolean latestProcessInstance);

    /**
     * 获取启动表单
     * @param businessCode 业务编码
     * @return
     */
    Object getStartForm(String businessCode);

    /**
     * 获取任务表单
     * @param businessCode 业务编码
     * @param taskId 任务ID
     * @return
     */
    Object getTaskForm(String businessCode, String taskId);

    /**
     * 获取流程实例
     * @param businessCode 业务编码
     * @param businessKey 业务主键
     * @return
     */
    ProcessInstanceDto getProcessInstance(String businessCode, String businessKey);

    /**
     * 获取Assignee为当前登陆人的任务
     * @param processInstanceId 流程实例ID
     * @return
     */
    TaskDto getTask(String processInstanceId);

    /**
     * 完成任务
     * @param businessCode 业务编码
     * @param taskId 任务ID
     * @param comment 评语
     * @param nextUserTaskInfo 下节点信息
     * @param formData 表单数据
     * @throws RfFlowException
     */
    void completeTask(@NotNull String businessCode, @NotNull String taskId, String comment, NextUserTaskInfo nextUserTaskInfo, Map<String, String> formData) throws RfFlowException;

    /**
     * 完成委托任务
     * @param businessCode 业务编码
     * @param taskId 任务ID
     * @param comment 评语
     * @param formData 表单数据
     * @throws RfFlowException
     */
    void resolveTask(String businessCode, String taskId, String comment, Map<String, String> formData) throws RfFlowException;

    /**
     * 查询可退回的用户任务
     * @param taskId
     * @return
     */
    List<TaskDto> listBackUserTask(String taskId);

    /**
     * 回退任务
     * @param businessCode 业务编码
     * @param taskId 任务ID
     * @param comment 回退原因
     * @param nextNode 回退节点
     * @throws RfFlowException
     */
    void backTask(@NotNull String businessCode, @NotNull String taskId, String comment, @NotNull String nextNode) throws RfFlowException;

    /**
     * 委派任务
     * @param businessCode 业务编码
     * @param taskId 任务ID
     * @param targetUserId 目标用户ID
     * @param comment 原因
     */
    void delegateTask(@NotNull String businessCode, @NotNull String taskId, @NotNull String targetUserId, String comment);

    /**
     * 转办任务
     * @param businessCode 业务编码
     * @param taskId 任务ID
     * @param targetUserId 目标用户ID
     * @param comment 原因
     */
    void turnTask(@NotNull String businessCode, @NotNull String taskId, @NotNull String targetUserId, String comment);

}
