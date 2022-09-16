package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.proj.ProjectRelationship;
import com.realfinance.sofa.cg.core.domain.req.RequirementRelationship;
import com.realfinance.sofa.cg.core.model.CgProjectRelationshipDto;
import com.realfinance.sofa.cg.core.model.CgRequirementRelationshipDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface ProjectRelationshipMapper extends ToDtoMapper<ProjectRelationship, CgProjectRelationshipDto>{
}
