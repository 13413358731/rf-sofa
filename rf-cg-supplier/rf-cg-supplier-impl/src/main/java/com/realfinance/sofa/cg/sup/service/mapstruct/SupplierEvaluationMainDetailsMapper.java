package com.realfinance.sofa.cg.sup.service.mapstruct;

import com.realfinance.sofa.cg.sup.domain.SupplierEvaluationMain;
import com.realfinance.sofa.cg.sup.model.CgSupplierEvaluationMainDetailsDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface SupplierEvaluationMainDetailsMapper extends ToDtoMapper<SupplierEvaluationMain, CgSupplierEvaluationMainDetailsDto> {

}
