package com.realfinance.sofa.flow.service.mapstruct;

import com.realfinance.sofa.flow.domain.BizModel;
import com.realfinance.sofa.flow.model.BizModelDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface BizModelMapper extends ToDtoMapper<BizModel, BizModelDto> {

}
