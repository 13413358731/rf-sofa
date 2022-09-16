package com.realfinance.sofa.cg.service.mapstruct;

import com.realfinance.sofa.cg.core.model.CgMeetingDetailsDto;
import com.realfinance.sofa.cg.core.model.CgMeetingDto;
import com.realfinance.sofa.cg.model.cg.CgMeetingVo;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface CgMeetingMapper {

    CgMeetingVo toVo(CgMeetingDto dto);
    CgMeetingVo toVo(CgMeetingDetailsDto dto);

    CgMeetingDetailsDto toSaveDto(CgMeetingVo dto);



}
