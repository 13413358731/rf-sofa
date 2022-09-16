package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.PurchaseCatalog;
import com.realfinance.sofa.cg.core.model.CgPurchaseCatalogDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface PurchaseCatalogMapper extends ToDtoMapper<PurchaseCatalog, CgPurchaseCatalogDto> {
}
