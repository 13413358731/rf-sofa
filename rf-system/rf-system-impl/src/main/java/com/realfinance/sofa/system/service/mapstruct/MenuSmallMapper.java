package com.realfinance.sofa.system.service.mapstruct;

import com.realfinance.sofa.system.domain.Menu;
import com.realfinance.sofa.system.model.MenuSmallDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface MenuSmallMapper extends ToDtoMapper<Menu, MenuSmallDto> {


}
