package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.ExpertLabel;
import com.realfinance.sofa.cg.core.model.CgExpertLabelDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface ExpertLabelMapper extends ToDtoMapper<ExpertLabel, CgExpertLabelDto> {
}
