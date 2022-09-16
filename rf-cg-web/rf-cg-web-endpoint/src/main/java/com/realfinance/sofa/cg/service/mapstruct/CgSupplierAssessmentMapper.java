package com.realfinance.sofa.cg.service.mapstruct;

import com.realfinance.sofa.cg.model.cg.CgSupplierAssessmentVo;
import com.realfinance.sofa.cg.sup.model.CgSupplierAssessmentDetailsDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierAssessmentDetailsSaveDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierAssessmentDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierAssessmentSaveDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface CgSupplierAssessmentMapper {

    CgSupplierAssessmentVo toVo(CgSupplierAssessmentDto dto);

    CgSupplierAssessmentVo toVo(CgSupplierAssessmentDetailsDto dto);

    CgSupplierAssessmentDetailsSaveDto toSaveDto(CgSupplierAssessmentVo vo);
}
