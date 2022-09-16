package com.realfinance.sofa.cg.sup.service.mapstruct;

import com.realfinance.sofa.cg.sup.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SupplierEditMapper {
    SupplierEditMapper INSTANCE = Mappers.getMapper( SupplierEditMapper.class );

    CgSupplierExaminationDetailsSaveDto toSaveDto(CgSupplierExaminationDetailsSaveRequest request);

    CgSupplierEditVo toVo(CgSupplierDetailsDto dto);
    CgSupplierEditVo toVo(CgSupplierExaminationDetailsDto dto);

}
