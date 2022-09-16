package com.realfinance.sofa.cg.service.mapstruct;


import com.realfinance.sofa.cg.core.model.ParameterDto;
import com.realfinance.sofa.cg.model.cg.ParameterVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface ParameterMapper {



    @Mapping(target = "createdUser", source = "createdUserId")
    @Mapping(target = "modifiedUser", source = "modifiedUserId")
    ParameterVo  toVo(ParameterDto dto);


}
