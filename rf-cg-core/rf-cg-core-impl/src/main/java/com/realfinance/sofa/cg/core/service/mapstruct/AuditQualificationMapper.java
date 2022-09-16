package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.meeting.AuditQualification;
import com.realfinance.sofa.cg.core.model.CgAuditQualificationDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface AuditQualificationMapper extends ToDtoMapper<AuditQualification, CgAuditQualificationDto> {
}
