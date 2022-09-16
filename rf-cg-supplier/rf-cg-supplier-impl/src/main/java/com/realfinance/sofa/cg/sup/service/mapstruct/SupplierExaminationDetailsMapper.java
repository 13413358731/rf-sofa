package com.realfinance.sofa.cg.sup.service.mapstruct;

import com.realfinance.sofa.cg.sup.domain.SupplierExamination;
import com.realfinance.sofa.cg.sup.model.CgSupplierExaminationDetailsDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface SupplierExaminationDetailsMapper extends ToDtoMapper<SupplierExamination, CgSupplierExaminationDetailsDto> {

}
