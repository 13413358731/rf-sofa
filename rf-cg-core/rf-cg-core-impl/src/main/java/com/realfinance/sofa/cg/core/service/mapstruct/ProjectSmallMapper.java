package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.proj.Project;
import com.realfinance.sofa.cg.core.model.CgProjectSmallDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface ProjectSmallMapper extends ToDtoMapper<Project, CgProjectSmallDto> {
}
