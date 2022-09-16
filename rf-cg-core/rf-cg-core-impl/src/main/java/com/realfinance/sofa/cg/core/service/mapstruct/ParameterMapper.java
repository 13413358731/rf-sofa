package com.realfinance.sofa.cg.core.service.mapstruct;


import com.realfinance.sofa.cg.core.domain.Parameter;
import com.realfinance.sofa.cg.core.model.ParameterDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface ParameterMapper extends ToDtoMapper<Parameter, ParameterDto>{
}
