package com.realfinance.sofa.system.service.mapstruct;

import com.realfinance.sofa.system.domain.RoleGroup;
import com.realfinance.sofa.system.model.RoleGroupDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface RoleGroupMapper extends ToDtoMapper<RoleGroup, RoleGroupDto> {
}
