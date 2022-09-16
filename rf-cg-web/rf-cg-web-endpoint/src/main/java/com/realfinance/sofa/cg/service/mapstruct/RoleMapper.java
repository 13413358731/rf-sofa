package com.realfinance.sofa.cg.service.mapstruct;

import com.realfinance.sofa.cg.model.system.RoleSaveRequest;
import com.realfinance.sofa.cg.model.system.RoleVo;
import com.realfinance.sofa.system.model.RoleDto;
import com.realfinance.sofa.system.model.RoleSaveDto;
import com.realfinance.sofa.system.model.RoleSmallDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface RoleMapper {

    RoleVo roleSmallDto2RoleVo(RoleSmallDto roleSmallDto);

    @Mapping(target = "createdUser", source = "createdUserId")
    @Mapping(target = "modifiedUser", source = "modifiedUserId")
    RoleVo roleDto2RoleVo(RoleDto roleDto);

    RoleSaveDto roleSaveRequest2RoleSaveDto(RoleSaveRequest roleSaveRequest);
}
