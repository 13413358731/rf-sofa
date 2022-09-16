package com.realfinance.sofa.cg.sup.service.mapstruct;

import com.realfinance.sofa.cg.sup.domain.SupplierAssessment;
import com.realfinance.sofa.cg.sup.domain.SupplierAssessmentIndicator;
import com.realfinance.sofa.cg.sup.model.CgSupplierAssessmentDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierAssessmentIndicatorDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface SupplierAssessmentIndicatorMapper extends ToDtoMapper<SupplierAssessmentIndicator, CgSupplierAssessmentIndicatorDto> {

}
