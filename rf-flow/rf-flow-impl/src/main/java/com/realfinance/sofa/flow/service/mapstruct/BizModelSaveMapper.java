package com.realfinance.sofa.flow.service.mapstruct;

import com.realfinance.sofa.flow.domain.BizModel;
import com.realfinance.sofa.flow.model.BizModelSaveDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface BizModelSaveMapper extends ToEntityMapper<BizModel, BizModelSaveDto> {

}
