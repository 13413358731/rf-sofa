package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.ExpertLabel;
import com.realfinance.sofa.cg.core.model.CgExpertLabelSmallDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface ExpertLabelSmallMapper extends ToDtoMapper<ExpertLabel, CgExpertLabelSmallDto> {
}
