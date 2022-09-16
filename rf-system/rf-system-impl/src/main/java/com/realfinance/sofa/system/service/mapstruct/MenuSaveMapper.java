package com.realfinance.sofa.system.service.mapstruct;

import com.realfinance.sofa.system.domain.Menu;
import com.realfinance.sofa.system.model.MenuSaveDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface MenuSaveMapper extends ToEntityMapper<Menu, MenuSaveDto> {

}
