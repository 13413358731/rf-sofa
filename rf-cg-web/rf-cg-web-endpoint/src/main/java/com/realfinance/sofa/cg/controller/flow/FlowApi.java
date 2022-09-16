package com.realfinance.sofa.cg.controller.flow;

import com.alibaba.fastjson.JSON;
import com.realfinance.sofa.cg.model.flow.FlowInfoVo;
import com.realfinance.sofa.cg.model.flow.FlowTaskVo;
import com.realfinance.sofa.cg.model.flow.HistoricActivityInstanceVo;
import com.realfinance.sofa.cg.service.mapstruct.FlowMapper;
import com.realfinance.sofa.common.util.SpringContextHolder;
import com.realfinance.sofa.flow.facade.FlowFacade;
import com.realfinance.sofa.flow.model.*;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 审批流程API
 */
public interface FlowApi {

    @GetMapping("flow/startprocessform")
    @Operation(summary = "流程启动表单信息")
    default ResponseEntity<?> flowStartProcessForm() {
        Object json = getFlowFacade().getStartForm(getBusinessCode());
        return ResponseEntity.ok(json);
    }

    @PostMapping("flow/startprocess")
    @Operation(summary = "流程启动", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content =
            {@Content(schema = @Schema(type = "string"))}))
    default ResponseEntity<String> flowStartProcess(@Parameter(description = "ID") @RequestParam Integer id,
                                                    @RequestBody(required = false) Map<String, String> formData) {
        String processInstanceId = getFlowFacade().startProcess(getBusinessCode(), id.toString(), formData,null);
        return ResponseEntity.ok(processInstanceId);
    }

    @DeleteMapping("flow/deleteprocess")
    @Operation(summary = "终止流程", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content =
            {@Content(schema = @Schema(type = "string", example = "{\"comment\":\"终止原因\"}"))}))
    default ResponseEntity<?> flowDeleteProcess(@Parameter(description = "ID") @RequestParam String processInstanceId,
                                                @RequestBody(required = false) Map<String, String> body) { // TODO: 2020/12/23 body
        getFlowFacade().deleteProcess(getBusinessCode(), processInstanceId, Optional.ofNullable(body).map(e -> e.get("comment")).orElse(null));
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "flow/processdiagram")
    @Operation(summary = "流程图")
    default ResponseEntity<byte[]> flowProcessDiagram(@Parameter(description = "ID") @RequestParam Integer id) {
        byte[] data = getFlowFacade().getProcessDiagram(getBusinessCode(), id.toString());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(data,httpHeaders, HttpStatus.OK);
    }

    @GetMapping("flow/processhistory")
    @Operation(summary = "流程历史流转信息")
    default ResponseEntity<List<HistoricActivityInstanceVo>> flowProcessHistory(@Parameter(description = "ID") @RequestParam Integer id,
                                                                                @Parameter(description = "是否最新一个流程") @RequestParam(required = false, defaultValue = "true") Boolean latestProcessInstance) {
        List<HistoricActivityInstanceDto> historicActivityInstance = getFlowFacade().listHistoricActivityInstance(getBusinessCode(), id.toString(),latestProcessInstance);
        FlowMapper flowMapper = SpringContextHolder.getBean(FlowMapper.class);
        return ResponseEntity.ok(historicActivityInstance.stream().map(flowMapper::historicActivityInstanceDto2HistoricActivityInstanceVo).collect(Collectors.toList()));
    }

    @PostMapping("flow/completetask")
    @Operation(summary = "完成流程任务")
    default ResponseEntity<?> flowCompleteTask(@RequestBody @Validated(FlowTaskVo.Complete.class) FlowTaskVo flowTaskVo) {
        getFlowFacade().completeTask(getBusinessCode(), flowTaskVo.getId(),
                flowTaskVo.getComment(), flowTaskVo.getNextUserTaskInfo(), flowTaskVo.getFormData());
        return ResponseEntity.ok().build();
    }

    @PostMapping("flow/resolvetask")
    @Operation(summary = "完成流程委派任务")
    default ResponseEntity<?> flowResolveTask(@RequestBody @Validated(FlowTaskVo.Resolve.class) FlowTaskVo flowTaskVo) {
        getFlowFacade().resolveTask(getBusinessCode(), flowTaskVo.getId(),
                flowTaskVo.getComment(), flowTaskVo.getFormData());
        return ResponseEntity.ok().build();
    }

    @GetMapping("flow/backtask")
    @Operation(summary = "获取可回退流程任务节点")
    default ResponseEntity<List<?>> flowBackTask(@Parameter(description = "任务ID") @RequestParam String taskId) {
        List<TaskDto> result = getFlowFacade().listBackUserTask(taskId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("flow/backtask")
    @Operation(summary = "回退流程任务")
    default ResponseEntity<?> flowBackTask(@RequestBody @Validated(FlowTaskVo.Back.class) FlowTaskVo flowTaskVo) {
        getFlowFacade().backTask(getBusinessCode(), flowTaskVo.getId(), flowTaskVo.getComment(), flowTaskVo.getActivitiId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("flow/delegatetask")
    @Operation(summary = "委派流程任务")
    default ResponseEntity<?> flowDelegateTask(@RequestBody @Validated(FlowTaskVo.Delegate.class) FlowTaskVo flowTaskVo) {
        getFlowFacade().delegateTask(getBusinessCode(), flowTaskVo.getId(), flowTaskVo.getTargetUserId(), flowTaskVo.getComment());
        return ResponseEntity.ok().build();
    }

    @PostMapping("flow/turntask")
    @Operation(summary = "转办流程任务")
    default ResponseEntity<?> flowTurnTask(@RequestBody @Validated(FlowTaskVo.Turn.class) FlowTaskVo flowTaskVo) {
        getFlowFacade().turnTask(getBusinessCode(), flowTaskVo.getId(), flowTaskVo.getTargetUserId(), flowTaskVo.getComment());
        return ResponseEntity.ok().build();
    }

    @PostMapping("flow/callback")
    @Operation(summary = "回调接口")
    @Hidden
    default ResponseEntity<FlowCallbackResponse> flowCallback(@RequestBody FlowCallbackRequest request) {
        return ResponseEntity.ok(FlowCallbackResponse.fail("未实现HTTP回调接口"));
    }

    /**
     * 获取流程信息
     * TODO 如果方法执行慢，可以将查询流程实例和查询任务合并在一起查询
     * @param businessKey
     * @return
     */
    default FlowInfoVo getFlowInfo(String businessKey) {
        FlowInfoVo flowInfoVo = new FlowInfoVo();
        ProcessInstanceDto processInstance = getFlowFacade().getProcessInstance(getBusinessCode(), businessKey);
        if (processInstance != null) {
            FlowMapper flowMapper = SpringContextHolder.getBean(FlowMapper.class);
            flowInfoVo.setFlowProcessInstance(flowMapper.processInstanceDto2ProcessInstanceVo(processInstance));
            TaskDto task = getFlowFacade().getTask(processInstance.getId());
            if (task != null) {
                FlowTaskVo flowTaskVo = flowMapper.taskDto2FlowTaskVo(task);
                Object taskForm = getFlowFacade().getTaskForm(getBusinessCode(), task.getId());
                if (taskForm != null) { // JSON转对象
                    try {
                        flowTaskVo.setFormProperties(JSON.parse((String) taskForm));
                    } catch (Exception ignored) {

                    }
                }
                flowInfoVo.setFlowTask(flowTaskVo);
            }
        }
        return flowInfoVo;
    }

    /**
     * 检查业务数据是否可修改
     * 逻辑为 没有正在执行的流程实例或者当前FlowTask为可编辑任务时 数据可以可以进行修改
     * @param flowInfo
     */
    default void checkBusinessDataIsEditable(FlowInfoVo flowInfo) {
        Objects.requireNonNull(flowInfo);
        if (flowInfo.getFlowProcessInstance() == null) {
            return;
        }
        FlowTaskVo flowTask = flowInfo.getFlowTask();
        if (flowTask != null
                && flowTask.getBusinessDataEditable() != null
                && flowTask.getBusinessDataEditable()) {
            return;
        }
        throw new RuntimeException("流程已启动，数据不可修改");
    }

    default String getBusinessCode() {
        throw new RuntimeException("找不到流程业务编码");
    }

    default FlowFacade getFlowFacade() {
        return SpringContextHolder.getBean(FlowFacade.class);
    }
}
