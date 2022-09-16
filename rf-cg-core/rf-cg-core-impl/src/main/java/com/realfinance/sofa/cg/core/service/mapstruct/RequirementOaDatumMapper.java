package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.req.RequirementOaDatum;
import com.realfinance.sofa.cg.core.model.CgRequirementOaDatumDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface RequirementOaDatumMapper extends ToDtoMapper<RequirementOaDatum, CgRequirementOaDatumDto>{
}
