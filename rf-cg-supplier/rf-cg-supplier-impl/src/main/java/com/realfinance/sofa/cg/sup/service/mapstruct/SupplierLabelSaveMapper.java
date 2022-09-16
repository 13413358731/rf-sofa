package com.realfinance.sofa.cg.sup.service.mapstruct;

import com.realfinance.sofa.cg.sup.domain.SupplierLabel;
import com.realfinance.sofa.cg.sup.model.CgSupplierLabelSaveDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface SupplierLabelSaveMapper extends ToEntityMapper<SupplierLabel, CgSupplierLabelSaveDto> {
}
