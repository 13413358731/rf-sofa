package com.realfinance.sofa.cg.service.mapstruct;

import com.realfinance.sofa.cg.core.model.*;
import com.realfinance.sofa.cg.model.cg.CgDrawExpertVo;
import com.realfinance.sofa.cg.model.cg.CgExpertVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface CgDrawExpertMapper {


    CgDrawExpertVo toVo(CgDrawExpertDto dto);

    @Mapping(target = "createdUser", source = "createdUserId")
    @Mapping(target = "modifiedUser", source = "modifiedUserId")
    CgDrawExpertVo toVo(CgDrawExpertDetailsDto dto);


    CgDrawExpertSaveDto toSaveDto(CgDrawExpertVo vo);
}
