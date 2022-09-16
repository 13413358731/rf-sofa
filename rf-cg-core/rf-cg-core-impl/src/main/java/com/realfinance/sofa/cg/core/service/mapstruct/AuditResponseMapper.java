package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.meeting.AuditQualification;
import com.realfinance.sofa.cg.core.domain.meeting.AuditResponse;
import com.realfinance.sofa.cg.core.model.CgAuditQualificationDto;
import com.realfinance.sofa.cg.core.model.CgAuditResponseDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface AuditResponseMapper extends ToDtoMapper<AuditResponse, CgAuditResponseDto> {
}
