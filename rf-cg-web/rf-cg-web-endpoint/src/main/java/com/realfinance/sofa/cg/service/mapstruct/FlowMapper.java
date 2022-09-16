package com.realfinance.sofa.cg.service.mapstruct;

import com.realfinance.sofa.cg.model.flow.FlowTaskVo;
import com.realfinance.sofa.cg.model.flow.HistoricActivityInstanceVo;
import com.realfinance.sofa.cg.model.flow.ProcessInstanceVo;
import com.realfinance.sofa.cg.model.flow.TaskVo;
import com.realfinance.sofa.flow.model.HistoricActivityInstanceDto;
import com.realfinance.sofa.flow.model.ProcessInstanceDto;
import com.realfinance.sofa.flow.model.TaskDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface FlowMapper {
    @Mapping(target = "processInstance.startUser", source = "processInstance.startUserId")
    @Mapping(target = "activitiId", source = "taskDefinitionKey")
    FlowTaskVo taskDto2FlowTaskVo(TaskDto taskDto);

    TaskVo taskDto2TaskVo(TaskDto taskDto);

    @Mapping(target = "startUser", source = "startUserId")
    ProcessInstanceVo processInstanceDto2ProcessInstanceVo(ProcessInstanceDto processInstance);

    HistoricActivityInstanceVo historicActivityInstanceDto2HistoricActivityInstanceVo(HistoricActivityInstanceDto historicActivityInstanceDto);
}
