package com.realfinance.sofa.system.service.mapstruct;

import com.realfinance.sofa.system.domain.MenuDataRule;
import com.realfinance.sofa.system.model.MenuDataRuleDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface MenuDataRuleMapper extends ToDtoMapper<MenuDataRule, MenuDataRuleDto> {
}
