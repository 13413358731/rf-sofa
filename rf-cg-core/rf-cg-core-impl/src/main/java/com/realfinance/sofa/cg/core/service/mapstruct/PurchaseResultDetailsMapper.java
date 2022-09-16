package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.purresult.PurchaseResult;
import com.realfinance.sofa.cg.core.model.CgPurchaseResultDetailsDto;
import com.realfinance.sofa.cg.core.model.CgPurchaseResultDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface PurchaseResultDetailsMapper extends ToDtoMapper<PurchaseResult, CgPurchaseResultDetailsDto> {


}
