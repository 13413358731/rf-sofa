package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.meeting.GradeSup;
import com.realfinance.sofa.cg.core.model.CgGradeSupDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface GradeSupMapper extends ToDtoMapper<GradeSup, CgGradeSupDto> {
}
