package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.ExpertLabelType;
import com.realfinance.sofa.cg.core.model.CgExpertLabelTypeDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface ExpertLabelTypeMapper extends ToDtoMapper<ExpertLabelType, CgExpertLabelTypeDto> {
}
