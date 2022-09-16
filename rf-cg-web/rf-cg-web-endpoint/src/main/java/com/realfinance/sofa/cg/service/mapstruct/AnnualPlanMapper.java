package com.realfinance.sofa.cg.service.mapstruct;

import com.realfinance.sofa.cg.core.model.AnnualPlanDto;
import com.realfinance.sofa.cg.model.cg.AnnualPlanVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface AnnualPlanMapper {

    @Mapping(target = "createdUser", source = "createdUserId")
    @Mapping(target = "modifiedUser", source = "modifiedUserId")
    AnnualPlanVo  toVo(AnnualPlanDto dto);

}
