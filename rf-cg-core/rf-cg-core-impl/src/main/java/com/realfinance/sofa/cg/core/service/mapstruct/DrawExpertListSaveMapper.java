package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.DrawExpertList;
import com.realfinance.sofa.cg.core.model.CgDrawExpertListSaveDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface DrawExpertListSaveMapper extends ToEntityMapper<DrawExpertList, CgDrawExpertListSaveDto> {
}
