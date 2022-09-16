package com.realfinance.sofa.cg.sup.service.mapstruct;


import com.realfinance.sofa.cg.sup.domain.SupplierAnnouncementChannel;
import com.realfinance.sofa.cg.sup.model.SupplierAnnouncementChannelDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface SupplierAnnouncementChannelMapper extends ToEntityMapper<SupplierAnnouncementChannel, SupplierAnnouncementChannelDto>,ToDtoMapper<SupplierAnnouncementChannel, SupplierAnnouncementChannelDto> {
}
