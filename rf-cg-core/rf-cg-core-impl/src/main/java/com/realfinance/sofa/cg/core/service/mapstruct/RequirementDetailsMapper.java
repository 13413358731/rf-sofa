package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.req.Requirement;
import com.realfinance.sofa.cg.core.model.CgRequirementDetailsDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface RequirementDetailsMapper extends ToDtoMapper<Requirement, CgRequirementDetailsDto> {
}
