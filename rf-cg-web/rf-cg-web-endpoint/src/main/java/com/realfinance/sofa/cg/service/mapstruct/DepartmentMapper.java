package com.realfinance.sofa.cg.service.mapstruct;

import com.realfinance.sofa.cg.model.system.DepartmentSaveRequest;
import com.realfinance.sofa.cg.model.system.DepartmentTreeVo;
import com.realfinance.sofa.cg.model.system.DepartmentVo;
import com.realfinance.sofa.system.model.DepartmentDto;
import com.realfinance.sofa.system.model.DepartmentSaveDto;
import com.realfinance.sofa.system.model.DepartmentSmallDto;
import com.realfinance.sofa.system.model.DepartmentSmallTreeDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface DepartmentMapper {

    DepartmentTreeVo departmentSmallTreeDto2DepartmentTreeVo(DepartmentSmallTreeDto departmentSmallTreeDto);

    DepartmentVo departmentSmallDto2DepartmentVo(DepartmentSmallDto dto);

    @Mapping(target = "createdUser", source = "createdUserId")
    @Mapping(target = "modifiedUser", source = "modifiedUserId")
    DepartmentVo departmentDto2DepartmentVo(DepartmentDto departmentDto);

    DepartmentSaveDto departmentSaveRequest2DepartmentSaveDto(DepartmentSaveRequest departmentSaveRequest);


}
