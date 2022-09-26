package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.AfterSales.AfterSales;
import com.realfinance.sofa.cg.core.domain.commodity.Commodity;
import com.realfinance.sofa.cg.core.model.CgAfterSalesDto;
import com.realfinance.sofa.cg.core.model.CgCommodityDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface AfterSalesMapper extends ToDtoMapper<AfterSales, CgAfterSalesDto> {
}
