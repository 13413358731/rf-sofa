package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.DrawExpert;
import com.realfinance.sofa.cg.core.model.CgDrawExpertSaveDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface DrawExpertSaveMapper extends ToEntityMapper<DrawExpert, CgDrawExpertSaveDto> {
}
