package com.realfinance.sofa.flow.service.mapstruct;

import com.realfinance.sofa.flow.domain.Biz;
import com.realfinance.sofa.flow.model.BizSaveDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface BizSaveMapper extends ToEntityMapper<Biz, BizSaveDto> {
}
