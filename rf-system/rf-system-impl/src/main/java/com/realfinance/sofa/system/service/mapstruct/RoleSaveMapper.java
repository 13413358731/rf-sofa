package com.realfinance.sofa.system.service.mapstruct;

import com.realfinance.sofa.system.domain.Role;
import com.realfinance.sofa.system.model.RoleSaveDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface RoleSaveMapper extends ToEntityMapper<Role, RoleSaveDto> {

}