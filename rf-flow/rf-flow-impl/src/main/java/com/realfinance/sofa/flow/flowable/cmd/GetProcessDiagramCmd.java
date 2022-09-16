package com.realfinance.sofa.flow.flowable.cmd;

import com.realfinance.sofa.common.datascope.DataScopeUtils;
import com.realfinance.sofa.flow.flowable.FlowConstants;
import com.realfinance.sofa.flow.flowable.diagram.CustomProcessDiagramGenerator;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.engine.HistoryService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.flowable.engine.impl.persistence.entity.ActivityInstanceEntity;
import org.flowable.engine.impl.util.CommandContextUtil;
import org.flowable.engine.runtime.ActivityInstance;
import org.flowable.image.ProcessDiagramGenerator;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GetProcessDiagramCmd implements Command<InputStream>, Serializable {

    private String businessCode;
    private String businessKey;

    public GetProcessDiagramCmd(String businessCode, String businessKey) {
        this.businessCode = businessCode;
        this.businessKey = businessKey;
    }

    @Override
    public InputStream execute(CommandContext commandContext) {
        ProcessEngineConfigurationImpl processEngineConfiguration = CommandContextUtil.getProcessEngineConfiguration(commandContext);
        HistoryService historyService = processEngineConfiguration.getHistoryService();
        List<HistoricProcessInstance> historicProcessInstances = historyService.createHistoricProcessInstanceQuery()
                .processInstanceReferenceType(FlowConstants.PROCESS_INSTANCE_REFERENCE_TYPE)
                .processInstanceReferenceId(businessCode)
                .processInstanceBusinessKey(businessKey)
                .processInstanceTenantId(DataScopeUtils.loadTenantId())
                .orderByProcessInstanceStartTime()
                .desc()
                .listPage(0,1);
        if (historicProcessInstances.isEmpty()) {
            return null;
        }
        HistoricProcessInstance historicProcessInstance = historicProcessInstances.get(0);
        List<HistoricActivityInstance> historicActivityInstances = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(historicProcessInstance.getId())
                .list();
        BpmnModel bpmnModel = processEngineConfiguration.getRepositoryService()
                .getBpmnModel(historicProcessInstance.getProcessDefinitionId());
        if (bpmnModel == null) {
            return null;
        }
        List<String> highLightedActivities = new ArrayList<>();
        List<String> highLightedFlows = new ArrayList<>();
        //获取流程图
        // TODO: 2020/12/25 可参考org.flowable.rest.service.api.runtime.process.ProcessInstanceDiagramResource的实现
        for (HistoricActivityInstance hi : historicActivityInstances) {
            String activityType = hi.getActivityType();
            if (activityType.equals("sequenceFlow") || activityType.equals("exclusiveGateway")) {
                highLightedFlows.add(hi.getActivityId());
            } else if (activityType.equals("userTask") || activityType.equals("startEvent")) {
                highLightedActivities.add(hi.getActivityId());
            }
        }
        ProcessDiagramGenerator processDiagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();
        if (processDiagramGenerator instanceof CustomProcessDiagramGenerator) {
            List<ActivityInstanceEntity> activityInstances = CommandContextUtil.getActivityInstanceEntityManager(commandContext)
                    .findActivityInstancesByProcessInstanceId(historicProcessInstance.getId(), false);
            List<String> redList = activityInstances.stream().filter(e -> e.getDurationInMillis() == null).map(ActivityInstance::getActivityId).collect(Collectors.toList());
            return ((CustomProcessDiagramGenerator) processEngineConfiguration.getProcessDiagramGenerator())
                    .generateDiagram(bpmnModel, "bmp", highLightedActivities, highLightedFlows, redList, "宋体",
                            "宋体", "宋体", this.getClass().getClassLoader(), 1.0, true);
        } else {
            return processDiagramGenerator.generateDiagram(bpmnModel, "bmp", highLightedActivities, highLightedFlows, "宋体",
                    "宋体", "宋体", this.getClass().getClassLoader(), 1.0, true);
        }
    }
}
