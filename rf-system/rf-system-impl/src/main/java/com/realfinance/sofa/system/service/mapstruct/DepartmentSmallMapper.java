package com.realfinance.sofa.system.service.mapstruct;

import com.realfinance.sofa.system.domain.Department;
import com.realfinance.sofa.system.model.DepartmentSmallDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface DepartmentSmallMapper extends ToDtoMapper<Department, DepartmentSmallDto> {

}
