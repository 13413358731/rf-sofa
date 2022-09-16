package com.realfinance.sofa.cg.service.mapstruct;


import com.realfinance.sofa.cg.model.system.AnnouncementChannelVo;
import com.realfinance.sofa.cg.sup.model.SupplierAnnouncementChannelDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface AnnouncementChannelMapper {

    @Mapping(target = "createdUser", source = "createdUserId")
    @Mapping(target = "modifiedUser", source = "modifiedUserId")
    AnnouncementChannelVo announcementChannelDto2AnnouncementChannelVo(SupplierAnnouncementChannelDto channelDto);

}
