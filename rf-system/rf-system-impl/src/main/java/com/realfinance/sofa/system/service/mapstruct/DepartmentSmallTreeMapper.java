package com.realfinance.sofa.system.service.mapstruct;

import com.realfinance.sofa.system.domain.Department;
import com.realfinance.sofa.system.model.DepartmentSmallTreeDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface DepartmentSmallTreeMapper extends ToDtoMapper<Department, DepartmentSmallTreeDto> {
    @Override
    @Mapping(target = "children", ignore = true)
    DepartmentSmallTreeDto toDto(Department department);
}
