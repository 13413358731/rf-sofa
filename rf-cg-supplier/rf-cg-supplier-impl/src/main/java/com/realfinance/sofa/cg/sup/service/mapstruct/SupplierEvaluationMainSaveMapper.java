package com.realfinance.sofa.cg.sup.service.mapstruct;

import com.realfinance.sofa.cg.sup.domain.SupplierAssessment;
import com.realfinance.sofa.cg.sup.domain.SupplierEvaluationMain;
import com.realfinance.sofa.cg.sup.model.CgSupplierAssessmentDetailsSaveDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierEvaluationMainDetailsDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierEvaluationMainDetailsSaveDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface SupplierEvaluationMainSaveMapper extends ToEntityMapper<SupplierEvaluationMain, CgSupplierEvaluationMainDetailsSaveDto> {
}
