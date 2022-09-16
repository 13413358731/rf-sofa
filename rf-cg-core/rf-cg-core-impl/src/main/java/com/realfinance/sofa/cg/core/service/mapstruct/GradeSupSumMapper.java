package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.meeting.GradeSupSum;
import com.realfinance.sofa.cg.core.model.CgGradeSupSumDetailsDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface GradeSupSumMapper extends ToDtoMapper<GradeSupSum, CgGradeSupSumDetailsDto> {
}
