package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.exec.ProjectExecutionAtt;
import com.realfinance.sofa.cg.core.domain.meeting.MeetingConferee;
import com.realfinance.sofa.cg.core.model.CgMeetingConfereeDto;
import com.realfinance.sofa.cg.core.model.CgProjectExecutionAttDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface MeetingFileMapper extends ToDtoMapper<ProjectExecutionAtt, CgProjectExecutionAttDto> {
}
