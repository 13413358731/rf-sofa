package com.realfinance.sofa.system.service.mapstruct;

import com.realfinance.sofa.system.domain.Department;
import com.realfinance.sofa.system.model.DepartmentSaveDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface DepartmentSaveMapper extends ToEntityMapper<Department, DepartmentSaveDto> {

}
