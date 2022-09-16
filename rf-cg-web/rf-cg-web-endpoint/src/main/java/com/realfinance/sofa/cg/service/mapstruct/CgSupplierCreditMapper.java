package com.realfinance.sofa.cg.service.mapstruct;

import com.realfinance.sofa.cg.model.cg.CgSupplierCreditVo;
import com.realfinance.sofa.cg.sup.model.CgSupplierCreditDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface CgSupplierCreditMapper {

    CgSupplierCreditVo toVo(CgSupplierCreditDto dto);
}
