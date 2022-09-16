package com.realfinance.sofa.cg.sup.service.mapstruct;

import com.realfinance.sofa.cg.sup.domain.SupplierAccount;
import com.realfinance.sofa.cg.sup.model.CgSupplierAccountDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface SupplierAccountMapper extends ToDtoMapper<SupplierAccount, CgSupplierAccountDto> {
}
