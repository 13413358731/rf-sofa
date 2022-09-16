package com.realfinance.sofa.cg.sup.service.mapstruct;

import com.realfinance.sofa.cg.sup.domain.SupplierEvaluationProcessMng;
import com.realfinance.sofa.cg.sup.model.CgSupEvaluationProcessMngDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface SupEvaluationProcessMngMapper extends ToDtoMapper<SupplierEvaluationProcessMng, CgSupEvaluationProcessMngDto> {

}
