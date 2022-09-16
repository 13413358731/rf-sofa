package com.realfinance.sofa.cg.sup.service.mapstruct;

import com.realfinance.sofa.cg.sup.model.SupplierAnnouncementDto;
import com.realfinance.sofa.cg.sup.model.SupplierAnnouncementVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SupplierAnnoucemtMapper {

    SupplierAnnoucemtMapper INSTANCE = Mappers.getMapper( SupplierAnnoucemtMapper.class );


    SupplierAnnouncementVo toVo(SupplierAnnouncementDto dto);


}
