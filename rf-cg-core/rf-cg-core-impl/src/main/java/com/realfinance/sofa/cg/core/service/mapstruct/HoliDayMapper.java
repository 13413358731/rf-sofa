package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.HoliDay;
import com.realfinance.sofa.cg.core.model.HoliDayDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface HoliDayMapper extends ToDtoMapper<HoliDay, HoliDayDto> {
}
