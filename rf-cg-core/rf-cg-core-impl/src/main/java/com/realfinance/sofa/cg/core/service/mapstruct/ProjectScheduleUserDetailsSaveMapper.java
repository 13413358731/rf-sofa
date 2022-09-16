package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.proj.ProjectScheduleUser;
import com.realfinance.sofa.cg.core.domain.req.Requirement;
import com.realfinance.sofa.cg.core.model.CgProjectScheduleUserDetailsSaveDto;
import com.realfinance.sofa.cg.core.model.CgRequirementDetailsSaveDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface ProjectScheduleUserDetailsSaveMapper extends ToEntityMapper<ProjectScheduleUser, CgProjectScheduleUserDetailsSaveDto> {

}
