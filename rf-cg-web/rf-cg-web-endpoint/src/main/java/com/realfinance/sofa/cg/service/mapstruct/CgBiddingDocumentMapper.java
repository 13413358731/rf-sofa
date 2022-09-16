package com.realfinance.sofa.cg.service.mapstruct;

import com.realfinance.sofa.cg.core.model.*;
import com.realfinance.sofa.cg.model.cg.*;
import com.realfinance.sofa.cg.sup.model.CgAttachmentDto;
import com.realfinance.sofa.cg.sup.model.CgBusinessReplyDetailsDto;
import com.realfinance.sofa.cg.sup.model.CgBusinessReplyDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface CgBiddingDocumentMapper {

    @Mapping(target = "createdUser", source = "createdUserId")
    @Mapping(target = "modifiedUser", source = "modifiedUserId")
    @Mapping(target = "department", source = "departmentId")
    CgBiddingDocumentVo toVo(CgBiddingDocumentDto dto);

    @Mapping(target = "createdUser", source = "createdUserId")
    @Mapping(target = "modifiedUser", source = "modifiedUserId")
    @Mapping(target = "department", source = "departmentId")
    CgBiddingDocumentVo toVo(CgBiddingDocumentDetailsDto dto);

    CgBiddingDocumentDetailsSaveDto toSaveDto(CgBiddingDocumentVo vo);

    @Mapping(target = "supplier", source = "supplierId")
    CgBusinessReplyVo toVo(CgBusinessReplyDto dto);

    @Mapping(target = "supplier", source = "supplierId")
    CgBusinessReplyVo toVo(CgBusinessReplyDetailsDto dto);

    List<CgAttachmentDto> tranAtt(List<CgProjectExecutionAttDto> dto);

    @Mapping(target = "supplier", source = "supplierId")
    CgProjectExecutionSupVo toVo(CgProjectExecutionSupDto dto);

    CgProjectExecutionStepVo toVo(CgProjectExecutionStepDto dto);

    @Mapping(target = "createdUser", source = "createdUserId")
    @Mapping(target = "modifiedUser", source = "modifiedUserId")
    CgAttVo toVo(CgProjectExecutionAttDto dto);
}
