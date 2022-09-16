package com.realfinance.sofa.cg.service.mapstruct;


import com.realfinance.sofa.cg.core.model.CgProductSaveDto;
import com.realfinance.sofa.cg.core.model.ProductLibraryDto;
import com.realfinance.sofa.cg.model.cg.CgProductLibrarySaveRepuest;
import com.realfinance.sofa.cg.model.cg.CgProductLibraryVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface ProductLibraryMapper {


    CgProductSaveDto toSaveDto(CgProductLibrarySaveRepuest saveRepuest);

    @Mapping(target = "createdUser", source = "createdUserId")
    @Mapping(target = "modifiedUser", source = "modifiedUserId")
    CgProductLibraryVo toVo(ProductLibraryDto libraryDto);



}
