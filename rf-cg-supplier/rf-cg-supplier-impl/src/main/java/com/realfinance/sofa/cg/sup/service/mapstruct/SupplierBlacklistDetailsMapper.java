package com.realfinance.sofa.cg.sup.service.mapstruct;

import com.realfinance.sofa.cg.sup.domain.SupplierBlacklist;
import com.realfinance.sofa.cg.sup.model.CgSupplierBlacklistDetailsDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface SupplierBlacklistDetailsMapper extends ToDtoMapper<SupplierBlacklist, CgSupplierBlacklistDetailsDto> {

}
