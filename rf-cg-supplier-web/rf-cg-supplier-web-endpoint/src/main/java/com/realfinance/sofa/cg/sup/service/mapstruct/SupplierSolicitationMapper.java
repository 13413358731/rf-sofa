package com.realfinance.sofa.cg.sup.service.mapstruct;


import com.realfinance.sofa.cg.sup.model.CgSupplierSolicitationDetailsDto;
import com.realfinance.sofa.cg.sup.model.SupplierSolicitationVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SupplierSolicitationMapper {

    SupplierSolicitationMapper INSTANCE = Mappers.getMapper( SupplierSolicitationMapper.class );

    SupplierSolicitationVo toVo(CgSupplierSolicitationDetailsDto dto);

}
