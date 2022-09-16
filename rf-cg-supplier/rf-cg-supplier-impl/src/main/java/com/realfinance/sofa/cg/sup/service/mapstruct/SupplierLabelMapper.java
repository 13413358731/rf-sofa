package com.realfinance.sofa.cg.sup.service.mapstruct;

import com.realfinance.sofa.cg.sup.domain.SupplierLabel;
import com.realfinance.sofa.cg.sup.model.CgSupplierLabelDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface SupplierLabelMapper extends ToDtoMapper<SupplierLabel, CgSupplierLabelDto> {
}
