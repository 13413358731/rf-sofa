package com.realfinance.sofa.cg.sup.service.mapstruct;

import com.realfinance.sofa.cg.sup.domain.SupplierEvaluationMain;
import com.realfinance.sofa.cg.sup.domain.SupplierEvaluationProcessMng;
import com.realfinance.sofa.cg.sup.model.CgSupEvaluationProcessMngDetailsDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierEvaluationMainDetailsDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface SupEvaluationProcessMngDetailsMapper extends ToDtoMapper<SupplierEvaluationProcessMng, CgSupEvaluationProcessMngDetailsDto> {

}
