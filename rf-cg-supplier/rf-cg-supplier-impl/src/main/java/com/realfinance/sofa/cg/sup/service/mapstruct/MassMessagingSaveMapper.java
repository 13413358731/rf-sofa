package com.realfinance.sofa.cg.sup.service.mapstruct;

import com.realfinance.sofa.cg.sup.domain.MassMessaging;
import com.realfinance.sofa.cg.sup.model.MassMessagingSaveDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface MassMessagingSaveMapper extends ToEntityMapper<MassMessaging, MassMessagingSaveDto> {
}
