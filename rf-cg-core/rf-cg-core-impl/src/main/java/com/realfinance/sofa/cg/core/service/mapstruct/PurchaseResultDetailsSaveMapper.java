package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.exec.ProjectExecutionAtt;
import com.realfinance.sofa.cg.core.domain.purresult.PurchaseResult;
import com.realfinance.sofa.cg.core.model.CgProjectExecutionAttDto;
import com.realfinance.sofa.cg.core.model.CgPurchaseResultDetailsDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface PurchaseResultDetailsSaveMapper extends ToEntityMapper<PurchaseResult, CgPurchaseResultDetailsDto> {

}
