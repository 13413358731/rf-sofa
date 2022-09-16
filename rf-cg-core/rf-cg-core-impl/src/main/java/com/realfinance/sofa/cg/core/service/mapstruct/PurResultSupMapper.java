package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.purresult.PurResultSupplier;
import com.realfinance.sofa.cg.core.model.CgPurResultSupDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface PurResultSupMapper extends ToDtoMapper<PurResultSupplier, CgPurResultSupDto>{
}
