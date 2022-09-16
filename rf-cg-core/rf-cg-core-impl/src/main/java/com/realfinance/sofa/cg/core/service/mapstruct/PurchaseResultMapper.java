package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.purresult.PurchaseResult;
import com.realfinance.sofa.cg.core.model.CgPurchaseResultDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface PurchaseResultMapper extends ToDtoMapper<PurchaseResult, CgPurchaseResultDto> {


}
