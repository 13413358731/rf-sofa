package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.exec.release.MultipleRelease;
import com.realfinance.sofa.cg.core.model.CgMultipleReleaseDetailsDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface MultipleReleaseDetailsMapper extends ToDtoMapper<MultipleRelease, CgMultipleReleaseDetailsDto> {
}
