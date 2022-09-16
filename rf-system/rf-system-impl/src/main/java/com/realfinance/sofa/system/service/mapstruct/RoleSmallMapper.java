package com.realfinance.sofa.system.service.mapstruct;

import com.realfinance.sofa.system.domain.Role;
import com.realfinance.sofa.system.model.RoleSmallDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface RoleSmallMapper extends ToDtoMapper<Role, RoleSmallDto> {

}
