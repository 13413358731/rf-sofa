package com.realfinance.sofa.cg.sup.service.mapstruct;

import com.realfinance.sofa.cg.sup.domain.SupplierEvaluationSheetMain;
import com.realfinance.sofa.cg.sup.model.CgSupplierEvaluationSheetDetailsDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface SupplierEvaluationSheetDetailsMapper extends ToDtoMapper<SupplierEvaluationSheetMain, CgSupplierEvaluationSheetDetailsDto> {

}
