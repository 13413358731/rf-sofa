package com.realfinance.sofa.cg.sup.service.mapstruct;

import com.realfinance.sofa.cg.sup.domain.BusinessProject;
import com.realfinance.sofa.cg.sup.model.CgBusinessProjectDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface BusinessProjectMapper extends ToDtoMapper<BusinessProject, CgBusinessProjectDto> {

}
