package com.realfinance.sofa.cg.sup.service.mapstruct;

import com.realfinance.sofa.cg.sup.domain.SupplierBadBehavior;
import com.realfinance.sofa.cg.sup.model.CgSupplierBadBehaviorSaveDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface SupplierBadBehaviorSaveMapper extends ToEntityMapper<SupplierBadBehavior, CgSupplierBadBehaviorSaveDto> {
}
