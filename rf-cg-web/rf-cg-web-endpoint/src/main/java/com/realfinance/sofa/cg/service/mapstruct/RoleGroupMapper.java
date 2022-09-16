package com.realfinance.sofa.cg.service.mapstruct;

import com.realfinance.sofa.cg.model.system.RoleGroupSaveRequest;
import com.realfinance.sofa.cg.model.system.RoleGroupVo;
import com.realfinance.sofa.system.model.RoleGroupDto;
import com.realfinance.sofa.system.model.RoleGroupSaveDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface RoleGroupMapper {

    @Mapping(target = "createdUser", source = "createdUserId")
    @Mapping(target = "modifiedUser", source = "modifiedUserId")
    RoleGroupVo roleGroupDto2RoleGroupVo(RoleGroupDto roleGroupDto);

    RoleGroupSaveDto roleGroupSaveRequest2RoleGroupSaveDto(RoleGroupSaveRequest roleGroupSaveRequest);
}
