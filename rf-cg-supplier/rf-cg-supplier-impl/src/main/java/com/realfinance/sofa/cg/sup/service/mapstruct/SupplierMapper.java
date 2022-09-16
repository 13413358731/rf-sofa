package com.realfinance.sofa.cg.sup.service.mapstruct;

import com.realfinance.sofa.cg.sup.domain.Supplier;
import com.realfinance.sofa.cg.sup.model.CgSupplierDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface SupplierMapper extends ToDtoMapper<Supplier, CgSupplierDto> {

}
