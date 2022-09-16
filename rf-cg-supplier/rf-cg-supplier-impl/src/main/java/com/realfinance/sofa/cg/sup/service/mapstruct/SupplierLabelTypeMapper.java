package com.realfinance.sofa.cg.sup.service.mapstruct;

import com.realfinance.sofa.cg.sup.domain.SupplierLabelType;
import com.realfinance.sofa.cg.sup.model.CgSupplierLabelTypeDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface SupplierLabelTypeMapper extends ToDtoMapper<SupplierLabelType, CgSupplierLabelTypeDto> {
}
