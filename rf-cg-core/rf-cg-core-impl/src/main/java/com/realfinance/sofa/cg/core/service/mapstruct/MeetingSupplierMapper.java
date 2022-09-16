package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.exec.ProjectExecutionSup;
import com.realfinance.sofa.cg.core.model.CgProjectExecutionSupDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface MeetingSupplierMapper extends ToDtoMapper<ProjectExecutionSup, CgProjectExecutionSupDto> {
}
