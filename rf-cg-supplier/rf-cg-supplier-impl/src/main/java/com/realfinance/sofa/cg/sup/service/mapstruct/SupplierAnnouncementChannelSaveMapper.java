package com.realfinance.sofa.cg.sup.service.mapstruct;



import com.realfinance.sofa.cg.sup.domain.SupplierAnnouncementChannel;
import com.realfinance.sofa.cg.sup.model.SupplierAnnouncementChannelSaveDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface SupplierAnnouncementChannelSaveMapper extends ToEntityMapper<SupplierAnnouncementChannel, SupplierAnnouncementChannelSaveDto>,ToDtoMapper<SupplierAnnouncementChannel,SupplierAnnouncementChannelSaveDto>{
}
