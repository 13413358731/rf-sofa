package com.realfinance.sofa.cg.sup.service.mapstruct;

import com.realfinance.sofa.cg.sup.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BusinessReplyMapper {
    BusinessReplyMapper INSTANCE = Mappers.getMapper( BusinessReplyMapper.class );

    CgBusinessReplyVo toVo(CgBusinessReplyDto dto);

    CgBusinessReplyVo toVo(CgBusinessReplyDetailsDto dto);

    List<CgAttachmentDto> toDto(List<AttachmentVo> vo);

}
