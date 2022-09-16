package com.realfinance.sofa.cg.sup.service.mapstruct;


import com.realfinance.sofa.cg.sup.domain.SupplierAnnouncement;
import com.realfinance.sofa.cg.sup.model.SupplierAnnouncementDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface SupplierAnnouncementMapper extends ToEntityMapper<SupplierAnnouncement, SupplierAnnouncementDto>,ToDtoMapper<SupplierAnnouncement,SupplierAnnouncementDto> {
}
