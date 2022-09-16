package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.req.RequirementRelationship;
import com.realfinance.sofa.cg.core.model.CgRequirementRelationshipDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface RequirementRelationshipMapper extends ToDtoMapper<RequirementRelationship, CgRequirementRelationshipDto>{
}
