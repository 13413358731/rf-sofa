package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.proj.Project;
import com.realfinance.sofa.cg.core.model.CgProjectDetailsDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface ProjectDetailsMapper extends ToDtoMapper<Project, CgProjectDetailsDto> {
}
