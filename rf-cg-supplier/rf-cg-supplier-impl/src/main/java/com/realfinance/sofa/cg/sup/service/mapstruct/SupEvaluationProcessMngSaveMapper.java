package com.realfinance.sofa.cg.sup.service.mapstruct;

import com.realfinance.sofa.cg.sup.domain.SupplierEvaluationProcessMng;
import com.realfinance.sofa.cg.sup.model.CgSupEvaluationProcessMngDetailsSaveDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface SupEvaluationProcessMngSaveMapper extends ToEntityMapper<SupplierEvaluationProcessMng, CgSupEvaluationProcessMngDetailsSaveDto> {
}
