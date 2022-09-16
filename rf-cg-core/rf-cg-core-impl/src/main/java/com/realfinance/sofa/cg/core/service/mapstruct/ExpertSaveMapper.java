package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.Expert;
import com.realfinance.sofa.cg.core.model.CgExpertSaveDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface ExpertSaveMapper extends ToEntityMapper<Expert, CgExpertSaveDto> {
}
