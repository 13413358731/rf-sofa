package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.meeting.MeetingChatRecord;
import com.realfinance.sofa.cg.core.model.CgChatRecordSaveDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface ChatRecordMapper extends ToDtoMapper<MeetingChatRecord, CgChatRecordSaveDto> {
}
