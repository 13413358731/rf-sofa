package com.realfinance.sofa.cg.service.mapstruct;

import com.realfinance.sofa.cg.core.model.*;
import com.realfinance.sofa.cg.model.cg.CgContractManageVo;
import com.realfinance.sofa.cg.model.cg.CgVendorRatingsVo;
import org.mapstruct.Mapper;

import javax.validation.constraints.NotNull;
import java.util.Set;


@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface CgVendorRatingsMapper {

    CgVendorRatingsVo toVo(CgVendorRatingsDto cgVendorRatingsDto);

//    CgVendorRatingsVo toVo(CgContractManageDetailsDto cgContractManageDetailsDto);

    CgVendorRatingsSaveDto toSaveDto(CgVendorRatingsVo vo);



}
