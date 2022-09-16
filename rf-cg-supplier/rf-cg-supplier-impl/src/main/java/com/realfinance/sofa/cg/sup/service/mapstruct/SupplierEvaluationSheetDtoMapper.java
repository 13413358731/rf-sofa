package com.realfinance.sofa.cg.sup.service.mapstruct;

import com.realfinance.sofa.cg.sup.domain.SupplierEvaluationSheetMain;
import com.realfinance.sofa.cg.sup.model.CgSupplierEvaluationSheetDetailsSaveDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierEvaluationSheetMainDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface SupplierEvaluationSheetDtoMapper extends ToDtoMapper<SupplierEvaluationSheetMain, CgSupplierEvaluationSheetDetailsSaveDto> {

}
