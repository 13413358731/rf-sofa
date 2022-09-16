package com.realfinance.sofa.cg.service.mapstruct;

import com.realfinance.sofa.cg.core.model.CgProjectScheduleDetailsSaveDto;
import com.realfinance.sofa.cg.core.model.CgProjectScheduleDto;
import com.realfinance.sofa.cg.core.model.CgRequirementDto;
import com.realfinance.sofa.cg.model.cg.CgProjectScheduleVo;
import com.realfinance.sofa.cg.model.cg.CgRequirementVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface CgProjectScheduleMapper {

    @Mapping(target = "createdUser", source = "createdUserId")
    @Mapping(target = "modifiedUser", source = "modifiedUserId")
    CgProjectScheduleVo toVo(CgProjectScheduleDto cgProjectScheduleDto);

    @Mapping(target = "createdUser", source = "createdUserId")
    @Mapping(target = "modifiedUser", source = "modifiedUserId")
    @Mapping(target = "operator", source = "operatorUserId")
    @Mapping(target = "department", source = "departmentId")
//    @Mapping(target = "useDepartments", source = "useDepartmentIds")
    @Mapping(target = "purDepartment", source = "purDepartmentId")
    @Mapping(target = "contractCreatedUser", source = "contractCreatedUserId")
    CgRequirementVo cgRequirementDtoToCgRequirementVo(CgRequirementDto cgRequirementDto);

    CgProjectScheduleDetailsSaveDto toSaveDto(CgProjectScheduleVo cgProjectScheduleVo);

    @Mapping(target = "purDepartmentId", source = "purDepartment")
    @Mapping(target = "contractCreatedUserId", source = "contractCreatedUser")
    @Mapping(target = "departmentId", source = "department")
    CgRequirementDto cgRequirementVoToCgRequirementDto(CgRequirementVo vo);

}
