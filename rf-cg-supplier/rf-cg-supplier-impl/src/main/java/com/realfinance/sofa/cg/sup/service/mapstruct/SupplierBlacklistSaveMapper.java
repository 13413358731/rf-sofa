package com.realfinance.sofa.cg.sup.service.mapstruct;

import com.realfinance.sofa.cg.sup.domain.SupplierBlacklist;
import com.realfinance.sofa.cg.sup.model.CgSupplierBlacklistSaveDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface SupplierBlacklistSaveMapper extends ToEntityMapper<SupplierBlacklist, CgSupplierBlacklistSaveDto> {
}
