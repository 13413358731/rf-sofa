package com.realfinance.sofa.system.service.mapstruct;

import com.realfinance.sofa.system.model.MenuDto;
import com.realfinance.sofa.system.domain.Menu;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface MenuMapper extends ToDtoMapper<Menu, MenuDto> {

}
