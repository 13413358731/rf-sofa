package com.realfinance.sofa.cg.sup.service.mapstruct;

import com.realfinance.sofa.cg.sup.domain.SupplierAssessment;
import com.realfinance.sofa.cg.sup.domain.SupplierEvaluationMain;
import com.realfinance.sofa.cg.sup.model.CgSupplierAssessmentDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierEvaluationMainDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface SupplierEvaluationMapper extends ToDtoMapper<SupplierEvaluationMain, CgSupplierEvaluationMainDto> {

}
