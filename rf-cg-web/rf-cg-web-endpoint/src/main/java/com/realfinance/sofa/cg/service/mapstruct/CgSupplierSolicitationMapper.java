package com.realfinance.sofa.cg.service.mapstruct;


import com.realfinance.sofa.cg.model.cg.CgAttVo;
import com.realfinance.sofa.cg.model.cg.CgSupplierSolicitationSaveRequest;
import com.realfinance.sofa.cg.model.cg.CgSupplierSolicitationVo;
import com.realfinance.sofa.cg.sup.model.CgSupplierSolicitationDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierSolicitationSaveDto;
import com.realfinance.sofa.cg.sup.model.SupplierAnnouncementAttachmentDto;
import com.realfinance.sofa.cg.sup.model.SupplierSolicitationAttachmentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface CgSupplierSolicitationMapper {

    CgSupplierSolicitationSaveDto  SolicitationSaveRequest2SolicitationSaveDto(CgSupplierSolicitationSaveRequest saveRequest);


    @Mapping(target = "createdUser", source = "createdUserId")
    @Mapping(target = "modifiedUser", source = "modifiedUserId")
    CgSupplierSolicitationVo  SolicitationDetailsDto2SolicitationVo(CgSupplierSolicitationDto SolicitationDto);

    @Mapping(target = "createdUser", source = "createdUserId")
    @Mapping(target = "modifiedUser", source = "modifiedUserId")
    CgAttVo SupplierSolicitationAttachmentDto2CgAttVo(SupplierSolicitationAttachmentDto announcementDto);
}
