package com.realfinance.sofa.cg.service.mapstruct;

import com.realfinance.sofa.cg.model.cg.CgSupplierBlacklistAttachmentVo;
import com.realfinance.sofa.cg.model.cg.CgSupplierBlacklistVo;
import com.realfinance.sofa.cg.sup.model.CgAttachmentDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierBlacklistDetailsDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierBlacklistDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierBlacklistSaveDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface CgSupplierBlacklistMapper {

    @Mapping(target = "createdUser", source = "createdUserId")
    @Mapping(target = "modifiedUser", source = "modifiedUserId")
    CgSupplierBlacklistVo toVo(CgSupplierBlacklistDto dto);

    @Mapping(target = "createdUser", source = "createdUserId")
    @Mapping(target = "modifiedUser", source = "modifiedUserId")
    CgSupplierBlacklistVo toDetailsVo(CgSupplierBlacklistDetailsDto dto);

    CgSupplierBlacklistSaveDto toSaveDto(CgSupplierBlacklistVo vo);

    @Mapping(target = "createdUser", source = "createdUserId")
    @Mapping(target = "modifiedUser", source = "modifiedUserId")
    CgSupplierBlacklistAttachmentVo CgAttachmentDto2CgSupplierBlacklistAttachmentVo(CgAttachmentDto announcementDto);

}
