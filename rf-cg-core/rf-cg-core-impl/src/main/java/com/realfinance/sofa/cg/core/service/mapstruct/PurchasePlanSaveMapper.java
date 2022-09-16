package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.plan.PurchasePlan;
import com.realfinance.sofa.cg.core.model.CgPurchasePlanSaveDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface PurchasePlanSaveMapper extends ToEntityMapper<PurchasePlan, CgPurchasePlanSaveDto>{
}
