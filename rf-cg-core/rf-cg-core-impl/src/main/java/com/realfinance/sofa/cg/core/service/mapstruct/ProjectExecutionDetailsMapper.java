package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.exec.ProjectExecution;
import com.realfinance.sofa.cg.core.domain.exec.ProjectExecutionStep;
import com.realfinance.sofa.cg.core.domain.exec.ProjectExecutionSup;
import com.realfinance.sofa.cg.core.model.CgProjectExecutionDetailsDto;
import com.realfinance.sofa.cg.core.model.CgProjectExecutionStepDto;
import com.realfinance.sofa.cg.core.model.CgProjectExecutionSupDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface ProjectExecutionDetailsMapper extends ToDtoMapper<ProjectExecution, CgProjectExecutionDetailsDto> {

    List<CgProjectExecutionSupDto> projectExecutionSupListToCgProjectExecutionSupDtoList(List<ProjectExecutionSup> list);

    List<CgProjectExecutionStepDto> projectExecutionStepListToCgProjectExecutionStepDtoList(List<ProjectExecutionStep> list);
}
