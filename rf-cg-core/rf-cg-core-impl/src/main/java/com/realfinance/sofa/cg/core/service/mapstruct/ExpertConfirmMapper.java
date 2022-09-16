package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.ExpertConfirm;
import com.realfinance.sofa.cg.core.model.CgExpertConfirmDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface ExpertConfirmMapper extends ToDtoMapper<ExpertConfirm, CgExpertConfirmDto> {

}
