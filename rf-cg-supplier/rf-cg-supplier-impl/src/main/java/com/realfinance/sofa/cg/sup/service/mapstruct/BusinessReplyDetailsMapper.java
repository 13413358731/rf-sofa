package com.realfinance.sofa.cg.sup.service.mapstruct;

import com.realfinance.sofa.cg.sup.domain.BusinessReply;
import com.realfinance.sofa.cg.sup.model.CgBusinessReplyDetailsDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface BusinessReplyDetailsMapper extends ToDtoMapper<BusinessReply, CgBusinessReplyDetailsDto> {


}
