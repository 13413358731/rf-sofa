package com.realfinance.sofa.cg.service.mapstruct;

import com.realfinance.sofa.cg.core.model.CgMeetingConfereeDto;
import com.realfinance.sofa.cg.model.cg.CgMeetingConfereeVo;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface CgMeetingConfereeMapper {

    CgMeetingConfereeVo toVo(CgMeetingConfereeDto dto);
//    CgMeetingConfereeVo toVo(CgMeetingDetailsDto dto);

    CgMeetingConfereeDto toSaveDto(CgMeetingConfereeVo dto);



}
