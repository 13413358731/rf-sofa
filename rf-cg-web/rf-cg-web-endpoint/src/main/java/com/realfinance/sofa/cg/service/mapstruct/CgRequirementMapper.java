package com.realfinance.sofa.cg.service.mapstruct;

import com.realfinance.sofa.cg.core.model.*;
import com.realfinance.sofa.cg.model.cg.CgAttVo;
import com.realfinance.sofa.cg.model.cg.CgRequirementSupVo;
import com.realfinance.sofa.cg.model.cg.CgRequirementVo;
import com.realfinance.sofa.cg.model.system.DepartmentVo;
import com.realfinance.sofa.common.util.SpringContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface CgRequirementMapper {

    @Mapping(target = "createdUser", source = "createdUserId")
    @Mapping(target = "modifiedUser", source = "modifiedUserId")
    @Mapping(target = "operator", source = "operatorUserId")
    @Mapping(target = "department", source = "departmentId")
    @Mapping(target = "useDepartments", source = "useDepartmentIds")
    @Mapping(target = "purDepartment", source = "purDepartmentId")
    @Mapping(target = "contractCreatedUser", source = "contractCreatedUserId")
    CgRequirementVo toVo(CgRequirementDto cgRequirementDto);


    @Mapping(target = "createdUser", source = "createdUserId")
    @Mapping(target = "modifiedUser", source = "modifiedUserId")
    @Mapping(target = "operator", source = "operatorUserId")
    @Mapping(target = "department", source = "departmentId")
    @Mapping(target = "useDepartments", source = "useDepartmentIds")
    @Mapping(target = "purDepartment", source = "purDepartmentId")
    @Mapping(target = "contractCreatedUser", source = "contractCreatedUserId")
    CgRequirementVo toVo(CgRequirementDetailsDto cgRequirementDetailsDto);

    @Mapping(target = "supplier", source = "supplierId")
    CgRequirementSupVo cgRequirementSupDtoToCgRequirementSupVo(CgRequirementSupDto cgRequirementSupDto);
    @Mapping(target = "supplierId", source = "supplier")
    CgRequirementSupDto cgRequirementSupVoToCgRequirementSupDto(CgRequirementSupVo vo);

    @Mapping(target = "useDepartmentIds", source = "useDepartments")
    @Mapping(target = "purDepartmentId", source = "purDepartment")
    @Mapping(target = "departmentId", source = "department")
    @Mapping(target = "contractCreatedUserId", source = "contractCreatedUser")
    CgRequirementDetailsSaveDto toSaveDto(CgRequirementVo vo);

    @Mapping(target = "createdUser", source = "createdUserId")
    CgAttVo cgRequirementAttDtoToCgAttVo(CgRequirementAttDto cgRequirementAttDto);

    default List<DepartmentVo> ids2DepartmentVoList(String ids) {
        if (StringUtils.isBlank(ids)) {
            return null;
        }
        CustomMapper customMapper = SpringContextHolder.getBean(CustomMapper.class);
        List<DepartmentVo> result = Stream.of(ids.split(","))
                .map(Integer::parseInt)
                .map(customMapper::resolveDepartmentVo)
                .collect(Collectors.toList());
        return result;
    }

    default String departmentVoList2Ids(List<DepartmentVo> voList) {
        if (voList == null || voList.isEmpty()) {
            return null;
        }
        String ids = voList.stream()
                .map(DepartmentVo::getId)
                .filter(Objects::nonNull)
                .map(Object::toString)
                .collect(Collectors.joining(","));
        return ids;
    }
}
