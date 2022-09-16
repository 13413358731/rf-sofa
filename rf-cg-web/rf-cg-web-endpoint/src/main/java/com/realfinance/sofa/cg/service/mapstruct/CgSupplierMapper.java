package com.realfinance.sofa.cg.service.mapstruct;

import com.realfinance.sofa.cg.model.cg.CgSupplierVo;
import com.realfinance.sofa.cg.sup.model.CgSupplierDetailsDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierSmallDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface CgSupplierMapper {

    CgSupplierVo toVo(CgSupplierSmallDto dto);
    @Mapping(target = "createdUser", source = "createdUserId")
    @Mapping(target = "modifiedUser", source = "modifiedUserId")
    CgSupplierVo toVo(CgSupplierDto dto);
    @Mapping(target = "createdUser", source = "createdUserId")
    @Mapping(target = "modifiedUser", source = "modifiedUserId")
    CgSupplierVo toVo(CgSupplierDetailsDto dto);
}
