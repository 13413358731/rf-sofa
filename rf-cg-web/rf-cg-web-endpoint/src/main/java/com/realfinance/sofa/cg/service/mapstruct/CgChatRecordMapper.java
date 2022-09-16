package com.realfinance.sofa.cg.service.mapstruct;

import com.realfinance.sofa.cg.core.model.CgChatRecordSaveDto;
import com.realfinance.sofa.cg.model.cg.CgChatRecordVo;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface CgChatRecordMapper {

    CgChatRecordVo toVo(CgChatRecordSaveDto dto);
//    CgMeetingVo toVo(CgMeetingDetailsDto dto);

}
