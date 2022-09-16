package com.realfinance.sofa.cg.sup.service.mapstruct;

import com.realfinance.sofa.cg.sup.domain.SupplierAssessment;
import com.realfinance.sofa.cg.sup.model.CgSupplierAssessmentDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface SupplierAssessmentMapper extends ToDtoMapper<SupplierAssessment, CgSupplierAssessmentDto> {

}
