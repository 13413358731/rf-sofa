package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.Expert;
import com.realfinance.sofa.cg.core.domain.meeting.MeetingConferee;
import com.realfinance.sofa.cg.core.model.CgExpertSaveDto;
import com.realfinance.sofa.cg.core.model.CgMeetingConfereeDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface MeetingConfereeSaveMapper extends ToEntityMapper<MeetingConferee, CgMeetingConfereeDto> {
}
