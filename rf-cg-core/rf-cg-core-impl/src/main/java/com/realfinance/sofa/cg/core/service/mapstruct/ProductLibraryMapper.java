package com.realfinance.sofa.cg.core.service.mapstruct;


import com.realfinance.sofa.cg.core.domain.ProductLibrary;
import com.realfinance.sofa.cg.core.model.ProductLibraryDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface ProductLibraryMapper extends ToDtoMapper<ProductLibrary, ProductLibraryDto> {
}
