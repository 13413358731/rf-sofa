package com.realfinance.sofa.cg.service.mapstruct;

import com.realfinance.sofa.cg.model.cg.CgSupplierAssessmentIndicatorVo;
import com.realfinance.sofa.cg.model.cg.CgSupplierAssessmentVo;
import com.realfinance.sofa.cg.sup.model.CgSupplierAssessmentDetailsDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierAssessmentDetailsSaveDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierAssessmentDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierAssessmentIndicatorDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface CgSupplierAssessmentIndicatorMapper {

    CgSupplierAssessmentIndicatorVo toVo(CgSupplierAssessmentIndicatorDto dto);

//    CgSupplierAssessmentIndicatorVo toVo(CgSupplierAssessmentDetailsDto dto);

//    CgSupplierAssessmentDetailsSaveDto toSaveDto(CgSupplierAssessmentIndicatorVo vo);
}
