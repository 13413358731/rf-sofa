package com.realfinance.sofa.cg.sup.service.mapstruct;

import com.realfinance.sofa.cg.sup.domain.SupplierAssessment;
import com.realfinance.sofa.cg.sup.model.CgSupplierAssessmentDetailsDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface SupplierAssessmentDetailsMapper extends ToDtoMapper<SupplierAssessment, CgSupplierAssessmentDetailsDto> {

}
