package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.plan.AnnualPlan;
import com.realfinance.sofa.cg.core.model.AnnualPlanDetailsDto;
import com.realfinance.sofa.cg.core.model.AnnualPlanDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface AnnualPlanDetailsMapper extends  ToDtoMapper<AnnualPlan, AnnualPlanDetailsDto> {
}
