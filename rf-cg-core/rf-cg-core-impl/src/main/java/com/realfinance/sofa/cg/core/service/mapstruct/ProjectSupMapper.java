package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.proj.ProjectSup;
import com.realfinance.sofa.cg.core.domain.req.RequirementSup;
import com.realfinance.sofa.cg.core.model.CgProjectSupDto;
import com.realfinance.sofa.cg.core.model.CgRequirementSupDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface ProjectSupMapper extends ToDtoMapper<ProjectSup, CgProjectSupDto>{
}
