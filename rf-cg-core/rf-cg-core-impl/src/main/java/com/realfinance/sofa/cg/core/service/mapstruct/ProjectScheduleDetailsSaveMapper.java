package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.proj.ProjectSchedule;
import com.realfinance.sofa.cg.core.model.CgProjectScheduleDetailsSaveDto;
import com.realfinance.sofa.cg.core.model.CgProjectScheduleDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface ProjectScheduleDetailsSaveMapper extends ToEntityMapper<ProjectSchedule, CgProjectScheduleDetailsSaveDto> {

}
