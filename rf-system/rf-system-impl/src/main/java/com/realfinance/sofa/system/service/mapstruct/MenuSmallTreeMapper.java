package com.realfinance.sofa.system.service.mapstruct;

import com.realfinance.sofa.system.domain.Menu;
import com.realfinance.sofa.system.model.MenuSmallTreeDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface MenuSmallTreeMapper extends ToDtoMapper<Menu, MenuSmallTreeDto> {
    @Override
    @Mapping(target = "children", ignore = true)
    MenuSmallTreeDto toDto(Menu department);
}
