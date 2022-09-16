package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.proj.Project;
import com.realfinance.sofa.cg.core.model.CgProjectDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface ProjectMapper extends ToDtoMapper<Project, CgProjectDto> {
}
