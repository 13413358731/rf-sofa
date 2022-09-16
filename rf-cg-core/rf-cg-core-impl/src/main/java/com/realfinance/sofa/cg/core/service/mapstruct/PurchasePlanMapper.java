package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.plan.PurchasePlan;
import com.realfinance.sofa.cg.core.model.CgPurchasePlanDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface PurchasePlanMapper extends  ToDtoMapper<PurchasePlan, CgPurchasePlanDto>{
}
