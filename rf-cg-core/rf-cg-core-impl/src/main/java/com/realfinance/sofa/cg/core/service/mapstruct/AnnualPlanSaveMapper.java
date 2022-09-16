package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.plan.AnnualPlan;
import com.realfinance.sofa.cg.core.model.AnnualPlanSaveDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface AnnualPlanSaveMapper extends  ToEntityMapper<AnnualPlan, AnnualPlanSaveDto>{
}
