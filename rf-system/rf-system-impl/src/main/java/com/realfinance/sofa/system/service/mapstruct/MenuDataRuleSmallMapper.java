package com.realfinance.sofa.system.service.mapstruct;

import com.realfinance.sofa.system.domain.MenuDataRule;
import com.realfinance.sofa.system.model.MenuDataRuleSmallDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface MenuDataRuleSmallMapper extends ToDtoMapper<MenuDataRule, MenuDataRuleSmallDto> {
}
