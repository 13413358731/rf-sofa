package com.realfinance.sofa.system.service.mapstruct;

import com.realfinance.sofa.system.domain.User;
import com.realfinance.sofa.system.model.UserSaveDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface UserSaveMapper extends ToEntityMapper<User, UserSaveDto> {

}
