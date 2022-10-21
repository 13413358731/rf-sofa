package com.realfinance.sofa.cg.service.mapstruct;

import com.realfinance.sofa.cg.core.model.CgCommodityDto;
import com.realfinance.sofa.cg.core.model.CgCommoditySaveDto;
import com.realfinance.sofa.cg.core.model.CgVendorRatingsDto;
import com.realfinance.sofa.cg.core.model.CgVendorRatingsSaveDto;
import com.realfinance.sofa.cg.model.cg.CgCommodityVo;
import com.realfinance.sofa.cg.model.cg.CgVendorRatingsVo;
import org.mapstruct.Mapper;


@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface CgCommodityMapper {

    CgCommodityVo toVo(CgCommodityDto cgCommodityDto);

//    CgVendorRatingsVo toVo(CgContractManageDetailsDto cgContractManageDetailsDto);

    CgCommoditySaveDto toSaveDto(CgCommodityVo vo);



}
