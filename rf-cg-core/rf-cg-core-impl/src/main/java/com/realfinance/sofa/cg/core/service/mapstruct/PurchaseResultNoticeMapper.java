package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.PurchaseResultNotice;
import com.realfinance.sofa.cg.core.model.CgPurchaseResultNoticeDto;
import org.mapstruct.Mapper;


@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface PurchaseResultNoticeMapper extends ToDtoMapper<PurchaseResultNotice, CgPurchaseResultNoticeDto> {
}
