package com.realfinance.sofa.cg.service.mapstruct;

import com.realfinance.sofa.cg.model.cg.CgSupplierExaminationVo;
import com.realfinance.sofa.cg.sup.model.CgSupplierExaminationDetailsDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierExaminationDetailsSaveDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierExaminationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface CgSupplierExaminationMapper {
    @Mapping(target = "createdUser", source = "createdUserId")
    @Mapping(target = "modifiedUser", source = "modifiedUserId")
    CgSupplierExaminationVo toVo(CgSupplierExaminationDto dto);

    @Mapping(target = "createdUser", source = "createdUserId")
    @Mapping(target = "modifiedUser", source = "modifiedUserId")
    CgSupplierExaminationVo toVo(CgSupplierExaminationDetailsDto dto);

    CgSupplierExaminationDetailsSaveDto toSaveDto(CgSupplierExaminationVo vo);
}
