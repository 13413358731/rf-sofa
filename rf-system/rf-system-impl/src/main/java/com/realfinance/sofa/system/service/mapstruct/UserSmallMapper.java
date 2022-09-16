package com.realfinance.sofa.system.service.mapstruct;

import com.realfinance.sofa.system.domain.User;
import com.realfinance.sofa.system.model.UserSmallDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class)
public interface UserSmallMapper extends ToDtoMapper<User, UserSmallDto> {

}
