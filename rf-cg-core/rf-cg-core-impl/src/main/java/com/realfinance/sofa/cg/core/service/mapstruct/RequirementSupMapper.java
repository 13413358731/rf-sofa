package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.req.RequirementRelationship;
import com.realfinance.sofa.cg.core.domain.req.RequirementSup;
import com.realfinance.sofa.cg.core.model.CgRequirementRelationshipDto;
import com.realfinance.sofa.cg.core.model.CgRequirementSupDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface RequirementSupMapper extends ToDtoMapper<RequirementSup, CgRequirementSupDto>{
}
