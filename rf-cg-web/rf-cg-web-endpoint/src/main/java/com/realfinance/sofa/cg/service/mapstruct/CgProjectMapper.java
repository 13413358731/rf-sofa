package com.realfinance.sofa.cg.service.mapstruct;

import com.realfinance.sofa.cg.core.model.*;
import com.realfinance.sofa.cg.model.cg.CgAttVo;
import com.realfinance.sofa.cg.model.cg.CgProjectDrawExpertRuleVo;
import com.realfinance.sofa.cg.model.cg.CgProjectSupVo;
import com.realfinance.sofa.cg.model.cg.CgProjectVo;
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
public interface CgProjectMapper {

    @Mapping(target = "createdUser", source = "createdUserId")
    @Mapping(target = "modifiedUser", source = "modifiedUserId")
    @Mapping(target = "department", source = "departmentId")
    @Mapping(target = "reqDepartment", source = "reqDepartmentId")
    @Mapping(target = "reqUser", source = "reqUserId")
    @Mapping(target = "supLabel", source = "supLabelId")
    @Mapping(target = "contractCreatedUser", source = "contractCreatedUserId")
    @Mapping(target = "useDepartments", source = "useDepartmentIds")
    CgProjectVo toVo(CgProjectDto cgProjectDto);

    @Mapping(target = "createdUser", source = "createdUserId")
    @Mapping(target = "modifiedUser", source = "modifiedUserId")
    @Mapping(target = "department", source = "departmentId")
    @Mapping(target = "reqDepartment", source = "reqDepartmentId")
    @Mapping(target = "reqUser", source = "reqUserId")
    @Mapping(target = "supLabel", source = "supLabelId")
    CgProjectVo toVo(CgProjectForExecDto cgProjectDto);

    @Mapping(target = "createdUser", source = "createdUserId")
    @Mapping(target = "modifiedUser", source = "modifiedUserId")
    @Mapping(target = "department", source = "departmentId")
    @Mapping(target = "reqDepartment", source = "reqDepartmentId")
    @Mapping(target = "reqUser", source = "reqUserId")
    @Mapping(target = "supLabel", source = "supLabelId")
    @Mapping(target = "useDepartments", source = "useDepartmentIds")
    @Mapping(target = "contractCreatedUser", source = "contractCreatedUserId")
    CgProjectVo toVo(CgProjectDetailsDto cgProjectDetailsDto);

    CgProjectVo toVo(CgProjectSmallDto cgProjectSmallDto);

    @Mapping(target = "supplier", source = "supplierId")
    CgProjectSupVo cgProjectSupDtoToCgProjectSupVo(CgProjectSupDto cgProjectSupDto);
    @Mapping(target = "supplierId", source = "supplier")
    CgProjectSupDto cgProjectSupVoToCgProjectSupDto(CgProjectSupVo vo);

    @Mapping(target = "expertDept", source = "expertDeptId")
    @Mapping(target = "expertLabel", source = "expertLabelId")
    CgProjectDrawExpertRuleVo cgProjectDrawExpertRuleDtoToCgProjectDrawExpertRuleVo(CgProjectDrawExpertRuleDto dto);
    @Mapping(target = "expertDeptId", source = "expertDept")
    @Mapping(target = "expertLabelId", source = "expertLabel")
    CgProjectDrawExpertRuleDto cgProjectDrawExpertRuleVoToCgProjectDrawExpertRuleDto(CgProjectDrawExpertRuleVo vo);

    @Mapping(target = "supLabelId", source = "supLabel")
    @Mapping(target = "contractCreatedUserId", source = "contractCreatedUser")
    @Mapping(target = "useDepartmentIds", source = "useDepartments")
//    @Mapping(target = "expertDescription", expression = "java(generateExpertDescription(vo))")
    CgProjectDetailsSaveDto toSaveDto(CgProjectVo vo);

    @Mapping(target = "createdUser", source = "createdUserId")
    CgAttVo cgProjectAttDtoToCgAttVo(CgProjectAttDto cgProjectAttDto);

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

//    default String generateExpertDescription(CgProjectVo vo) {
//        if (vo == null) {
//            return "";
//        }
//        List<CgProjectDrawExpertRuleVo> projectDrawExpertRules = vo.getProjectDrawExpertRules();
//        if (projectDrawExpertRules == null || projectDrawExpertRules.isEmpty()) {
//            return "";
//        }
//        Integer expertCount = projectDrawExpertRules.stream()
//                .map(CgProjectDrawExpertRuleVo::getExpertCount).reduce(Integer::sum).orElse(0);
//        StringBuilder sb = new StringBuilder();
//        for (CgProjectDrawExpertRuleVo e : projectDrawExpertRules) {
//            if (e.getExpertDept() != null && e.getExpertCount() != null) {
//                sb.append(e.getExpertDept().getName())
//                        .append("(")
//                        .append(e.getExpertCount())
//                        .append("/")
//                        .append(expertCount)
//                        .append(")；");
//            }
//        }
//        return sb.toString();
//    }
}
