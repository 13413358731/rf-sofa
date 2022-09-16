package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.PurchaseCatalog;
import com.realfinance.sofa.cg.core.model.CgPurchaseCatalogSaveDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface PurchaseCatalogSaveMapper extends ToEntityMapper<PurchaseCatalog, CgPurchaseCatalogSaveDto> {
}
