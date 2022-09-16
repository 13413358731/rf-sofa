package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.DrawExpert;
import com.realfinance.sofa.cg.core.model.CgDrawExpertDetailsDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface DrawExpertDetailsMapper extends ToDtoMapper<DrawExpert, CgDrawExpertDetailsDto> {

}
