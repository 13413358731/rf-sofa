package com.realfinance.sofa.cg.service.mapstruct;

import com.realfinance.sofa.cg.model.system.UserDetailsVo;
import com.realfinance.sofa.cg.model.system.UserSaveRequest;
import com.realfinance.sofa.cg.model.system.UserVo;
import com.realfinance.sofa.system.model.UserDetailsDto;
import com.realfinance.sofa.system.model.UserDto;
import com.realfinance.sofa.system.model.UserSaveDto;
import com.realfinance.sofa.system.model.UserSmallDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface UserMapper {

    UserVo userSmallDto2UserVo(UserSmallDto userSmallDto);

    @Mapping(target = "createdUser", source = "createdUserId")
    @Mapping(target = "modifiedUser", source = "modifiedUserId")
    UserVo userDto2UserVo(UserDto userDto);

    @Mapping(target = "createdUser", source = "createdUserId")
    @Mapping(target = "modifiedUser", source = "modifiedUserId")
    UserDetailsVo userDetailsDto2UserDetailsVo(UserDetailsDto userDetailsDto);

    UserSaveDto userSaveRequest2UserSaveDto(UserSaveRequest userSaveRequest);
}
