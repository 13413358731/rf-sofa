package com.realfinance.sofa.cg.sup.service.mapstruct;

import com.realfinance.sofa.cg.sup.domain.SupplierBlacklist;
import com.realfinance.sofa.cg.sup.model.CgSupplierBlacklistDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface SupplierBlacklistMapper extends ToDtoMapper<SupplierBlacklist, CgSupplierBlacklistDto> {

}
