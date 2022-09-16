package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.commodity.Commodity;
import com.realfinance.sofa.cg.core.domain.vendor.VendorRating;
import com.realfinance.sofa.cg.core.model.CgCommodityDto;
import com.realfinance.sofa.cg.core.model.CgVendorRatingsDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface CommodityMapper extends ToDtoMapper<Commodity, CgCommodityDto> {
}
