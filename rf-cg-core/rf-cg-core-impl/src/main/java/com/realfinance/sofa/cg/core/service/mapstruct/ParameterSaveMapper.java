package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.Parameter;
import com.realfinance.sofa.cg.core.model.ParameterSaveDto;
import org.mapstruct.Mapper;


@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface ParameterSaveMapper extends ToEntityMapper<Parameter, ParameterSaveDto> {
}
