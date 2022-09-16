package com.realfinance.sofa.cg.service.mapstruct;

import com.realfinance.sofa.cg.core.model.CgProjectScheduleUserDetailsSaveDto;
import com.realfinance.sofa.cg.core.model.CgProjectScheduleUserDto;
import com.realfinance.sofa.cg.core.model.CgRequirementDetailsSaveDto;
import com.realfinance.sofa.cg.core.model.CgRequirementDto;
import com.realfinance.sofa.cg.model.cg.CgProjectScheduleUserVo;
import com.realfinance.sofa.cg.model.cg.CgRequirementVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface CgProjectScheduleUserMapper {

    @Mapping(target = "createdUser", source = "createdUserId")
    @Mapping(target = "modifiedUser", source = "modifiedUserId")
    CgProjectScheduleUserVo toVo(CgProjectScheduleUserDto cgProjectScheduleUserDto);

    CgProjectScheduleUserDetailsSaveDto toSaveDto(CgProjectScheduleUserVo cgProjectScheduleUserVo);

//    @Mapping(target = "useDepartmentIds", source = "useDepartments")
    @Mapping(target = "purDepartmentId", source = "purDepartment")
    @Mapping(target = "contractCreatedUserId", source = "contractCreatedUser")
    @Mapping(target = "departmentId", source = "department")
    CgRequirementDto cgRequirementVoToCgRequirementDto(CgRequirementVo vo);
}
