package com.realfinance.sofa.system.service.mapstruct;

import com.realfinance.sofa.system.domain.MenuDataRule;
import com.realfinance.sofa.system.model.MenuDataRuleSaveDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface MenuDataRuleSaveMapper extends ToEntityMapper<MenuDataRule, MenuDataRuleSaveDto> {
}
