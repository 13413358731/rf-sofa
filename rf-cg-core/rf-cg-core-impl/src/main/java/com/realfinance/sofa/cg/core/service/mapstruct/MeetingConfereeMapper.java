package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.meeting.Meeting;
import com.realfinance.sofa.cg.core.domain.meeting.MeetingConferee;
import com.realfinance.sofa.cg.core.model.CgMeetingConfereeDto;
import com.realfinance.sofa.cg.core.model.CgMeetingDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface MeetingConfereeMapper extends ToDtoMapper<MeetingConferee, CgMeetingConfereeDto> {
}
