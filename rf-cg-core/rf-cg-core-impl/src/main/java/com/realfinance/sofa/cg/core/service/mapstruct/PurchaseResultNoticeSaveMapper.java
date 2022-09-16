package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.PurchaseResultNotice;
import com.realfinance.sofa.cg.core.model.CgPurchaseResultNoticeSaveDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface PurchaseResultNoticeSaveMapper extends ToEntityMapper<PurchaseResultNotice, CgPurchaseResultNoticeSaveDto>{
}
