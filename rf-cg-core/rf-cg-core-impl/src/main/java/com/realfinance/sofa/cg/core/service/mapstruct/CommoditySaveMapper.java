package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.commodity.Commodity;
import com.realfinance.sofa.cg.core.domain.vendor.VendorRating;
import com.realfinance.sofa.cg.core.model.CgCommoditySaveDto;
import com.realfinance.sofa.cg.core.model.CgVendorRatingsSaveDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface CommoditySaveMapper extends ToEntityMapper<Commodity, CgCommoditySaveDto> {
}
