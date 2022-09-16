package com.realfinance.sofa.system.service.mapstruct;

import com.realfinance.sofa.system.domain.Role;
import com.realfinance.sofa.system.model.RoleDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface RoleMapper extends ToDtoMapper<Role, RoleDto> {

}
