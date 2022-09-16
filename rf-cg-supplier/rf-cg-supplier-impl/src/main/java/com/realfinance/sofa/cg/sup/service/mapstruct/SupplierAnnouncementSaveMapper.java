package com.realfinance.sofa.cg.sup.service.mapstruct;

import com.realfinance.sofa.cg.sup.domain.SupplierAnnouncement;
import com.realfinance.sofa.cg.sup.model.SupplierAnnouncementSaveDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface SupplierAnnouncementSaveMapper extends ToEntityMapper<SupplierAnnouncement, SupplierAnnouncementSaveDto>{
}
