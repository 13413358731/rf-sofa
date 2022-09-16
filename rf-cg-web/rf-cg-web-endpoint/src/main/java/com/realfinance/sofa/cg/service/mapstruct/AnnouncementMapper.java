package com.realfinance.sofa.cg.service.mapstruct;

import com.realfinance.sofa.cg.model.cg.CgAttVo;
import com.realfinance.sofa.cg.model.system.AnnouncementDetailsVo;
import com.realfinance.sofa.cg.model.system.AnnouncementSaveRequest;
import com.realfinance.sofa.cg.model.system.AnnouncementVo;
import com.realfinance.sofa.cg.sup.model.SupplierAnnouncementAttachmentDto;
import com.realfinance.sofa.cg.sup.model.SupplierAnnouncementDetailsDto;
import com.realfinance.sofa.cg.sup.model.SupplierAnnouncementDto;
import com.realfinance.sofa.cg.sup.model.SupplierAnnouncementSaveDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface AnnouncementMapper {

    SupplierAnnouncementSaveDto announcementSaveRequest2SupplierAnnouncementSaveDto(AnnouncementSaveRequest request);

    @Mapping(target = "createdUser", source = "createdUserId")
    @Mapping(target = "modifiedUser", source = "modifiedUserId")
    AnnouncementVo announcementDto2AnnouncementVo  (SupplierAnnouncementDto announcementDto);

    @Mapping(target = "createdUser", source = "createdUserId")
    @Mapping(target = "modifiedUser", source = "modifiedUserId")
    AnnouncementDetailsVo toVo(SupplierAnnouncementDetailsDto  detailsDto);

    @Mapping(target = "createdUser", source = "createdUserId")
    @Mapping(target = "modifiedUser", source = "modifiedUserId")
    CgAttVo announcementDto2CgAttVo(SupplierAnnouncementAttachmentDto announcementDto);


}
