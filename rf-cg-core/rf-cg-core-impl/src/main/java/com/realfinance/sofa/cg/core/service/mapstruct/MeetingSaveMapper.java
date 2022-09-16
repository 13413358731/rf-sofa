package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.meeting.Meeting;
import com.realfinance.sofa.cg.core.model.CgMeetingDetailsDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface MeetingSaveMapper extends ToEntityMapper<Meeting, CgMeetingDetailsDto> {
}
