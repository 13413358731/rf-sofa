package com.realfinance.sofa.cg.service.mapstruct;

import com.realfinance.sofa.cg.core.model.CgAfterSalesDto;
import com.realfinance.sofa.cg.core.model.CgCommodityDto;
import com.realfinance.sofa.cg.model.cg.CgAfterSalesVo;
import com.realfinance.sofa.cg.model.cg.CgCommodityVo;
import org.mapstruct.Mapper;


@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface CgAfterSalesMapper {

    CgAfterSalesVo toVo(CgAfterSalesDto cgAfterSalesDto);

//    CgVendorRatingsVo toVo(CgContractManageDetailsDto cgContractManageDetailsDto);

    CgAfterSalesDto toSaveDto(CgAfterSalesVo vo);



}
