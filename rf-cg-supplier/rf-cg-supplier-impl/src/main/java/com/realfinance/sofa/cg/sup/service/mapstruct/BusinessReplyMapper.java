package com.realfinance.sofa.cg.sup.service.mapstruct;

import com.realfinance.sofa.cg.sup.domain.BusinessReply;
import com.realfinance.sofa.cg.sup.model.CgBusinessReplyDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface BusinessReplyMapper extends ToDtoMapper<BusinessReply, CgBusinessReplyDto> {


}
