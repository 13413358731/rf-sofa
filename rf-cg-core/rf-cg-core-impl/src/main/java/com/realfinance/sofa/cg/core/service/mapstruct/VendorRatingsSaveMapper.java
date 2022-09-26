package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.contract.ContractManage;
import com.realfinance.sofa.cg.core.domain.vendor.VendorRating;
import com.realfinance.sofa.cg.core.model.CgContractManageSaveDto;
import com.realfinance.sofa.cg.core.model.CgVendorRatingsSaveDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface VendorRatingsSaveMapper extends ToEntityMapper<VendorRating, CgVendorRatingsSaveDto> {
}
