package com.realfinance.sofa.cg.sup.service.mapstruct;

import com.realfinance.sofa.cg.sup.domain.BusinessProject;
import com.realfinance.sofa.cg.sup.model.CgBusinessProjectDetailsDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface BusinessProjectDetailsMapper extends ToDtoMapper<BusinessProject, CgBusinessProjectDetailsDto> {

}
