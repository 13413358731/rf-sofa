package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.HoliDay;
import com.realfinance.sofa.cg.core.model.HoliDaySaveDto;
import org.mapstruct.Mapper;


@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface HoliDaySaveMapper extends  ToEntityMapper<HoliDay, HoliDaySaveDto> {
}
