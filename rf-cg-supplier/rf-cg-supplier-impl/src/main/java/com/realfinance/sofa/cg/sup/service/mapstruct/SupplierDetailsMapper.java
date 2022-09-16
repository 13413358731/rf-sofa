package com.realfinance.sofa.cg.sup.service.mapstruct;

import com.realfinance.sofa.cg.sup.domain.Supplier;
import com.realfinance.sofa.cg.sup.model.CgSupplierDetailsDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface SupplierDetailsMapper extends ToDtoMapper<Supplier, CgSupplierDetailsDto> {

}
