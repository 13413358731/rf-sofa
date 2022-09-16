package com.realfinance.sofa.system.service.mapstruct;

import com.realfinance.sofa.system.model.DepartmentDto;
import com.realfinance.sofa.system.domain.Department;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface DepartmentMapper extends ToDtoMapper<Department, DepartmentDto> {

}
