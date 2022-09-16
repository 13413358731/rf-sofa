package com.realfinance.sofa.cg.core.service.mapstruct;


import com.realfinance.sofa.cg.core.domain.ProductLibrary;
import com.realfinance.sofa.cg.core.model.CgProductSaveDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface ProductLibrarySaveMapper extends ToEntityMapper<ProductLibrary, CgProductSaveDto>{
}
