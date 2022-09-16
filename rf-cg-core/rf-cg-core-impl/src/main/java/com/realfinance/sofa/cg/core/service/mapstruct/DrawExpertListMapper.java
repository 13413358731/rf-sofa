package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.DrawExpert;
import com.realfinance.sofa.cg.core.domain.DrawExpertList;
import com.realfinance.sofa.cg.core.model.CgDrawExpertDto;
import com.realfinance.sofa.cg.core.model.CgDrawExpertListDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface DrawExpertListMapper extends ToDtoMapper<DrawExpertList, CgDrawExpertListDto> {

}
