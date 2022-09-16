package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.contract.ContractManage;
import com.realfinance.sofa.cg.core.domain.vendor.VendorRating;
import com.realfinance.sofa.cg.core.model.CgContractManageDetailsDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface VendorRatingsDetailsMapper extends ToDtoMapper<VendorRating, CgContractManageDetailsDto> {

}
