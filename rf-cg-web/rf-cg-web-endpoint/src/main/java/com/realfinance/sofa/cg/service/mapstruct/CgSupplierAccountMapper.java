package com.realfinance.sofa.cg.service.mapstruct;

import com.realfinance.sofa.cg.model.cg.CgSupplierAccountVo;
import com.realfinance.sofa.cg.sup.model.CgSupplierAccountCreateDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierAccountDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface CgSupplierAccountMapper {

    @Mapping(target = "createdUser", source = "createdUserId")
    @Mapping(target = "modifiedUser", source = "modifiedUserId")
    CgSupplierAccountVo toVo(CgSupplierAccountDto cgSupplierAccountDto);

    @Mapping(target = "enabled", defaultValue = "true")
    CgSupplierAccountCreateDto toSaveDto(CgSupplierAccountVo vo);
}
