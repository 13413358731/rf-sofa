package com.realfinance.sofa.cg.sup.service.mapstruct;

import com.realfinance.sofa.cg.sup.domain.MassMessaging;
import com.realfinance.sofa.cg.sup.model.MassMessagingDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface MassMessagingMapper extends ToDtoMapper<MassMessaging, MassMessagingDto> {
}
