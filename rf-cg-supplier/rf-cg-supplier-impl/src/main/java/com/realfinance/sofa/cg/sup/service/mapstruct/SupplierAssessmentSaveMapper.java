package com.realfinance.sofa.cg.sup.service.mapstruct;

import com.realfinance.sofa.cg.sup.domain.SupplierAssessment;
import com.realfinance.sofa.cg.sup.model.CgSupplierAssessmentDetailsSaveDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierAssessmentSaveDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface SupplierAssessmentSaveMapper extends ToEntityMapper<SupplierAssessment, CgSupplierAssessmentDetailsSaveDto> {
}
