package com.realfinance.sofa.cg.service.mapstruct;


import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface HoliDayMapper {
}
