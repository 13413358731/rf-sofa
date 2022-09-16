package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.exec.ProjectExecution;
import com.realfinance.sofa.cg.core.model.CgProjectExecutionDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface ProjectExecutionMapper extends ToDtoMapper<ProjectExecution, CgProjectExecutionDto> {


}
