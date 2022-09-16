package com.realfinance.sofa.cg.service.mapstruct;

import com.realfinance.sofa.cg.core.model.AnnualPlanSaveDto;
import com.realfinance.sofa.cg.core.model.CgPurchasePlanDto;
import com.realfinance.sofa.cg.core.model.CgPurchasePlanSaveDto;
import com.realfinance.sofa.cg.model.cg.CgAnnualPlanSaveRequest;
import com.realfinance.sofa.cg.model.cg.CgPurchasePlanSaveRequest;
import com.realfinance.sofa.cg.model.cg.CgPurchasePlanVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface CgPurchasePlanMapper {


    CgPurchasePlanSaveDto toSaveDto(CgPurchasePlanSaveRequest request);

    AnnualPlanSaveDto toSaveDto(CgAnnualPlanSaveRequest request);

    @Mapping(target = "createdUser", source = "createdUserId")
    @Mapping(target = "modifiedUser", source = "modifiedUserId")
    CgPurchasePlanVo toVo(CgPurchasePlanDto dto);

}
