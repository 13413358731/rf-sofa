package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.req.*;
import com.realfinance.sofa.cg.core.model.CgRequirementDetailsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface HistoricRequirementDetailsMapper extends ToDtoMapper<HistoricRequirement, CgRequirementDetailsDto> {

    @Mapping(target = "createdUserId", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "modifiedUserId", ignore = true)
    @Mapping(target = "modifiedTime", ignore = true)
    @Mapping(target = "v", ignore = true)
    @Mapping(target = "id", source = "id", ignore = true)
    @Mapping(target = "srcId", source = "id")
    HistoricRequirement fromRequirement(Requirement requirement);

    @Mapping(target = "id", source = "id", ignore = true)
    @Mapping(target = "srcId", source = "id")
    @Mapping(target = "requirement", source = "requirement", ignore = true)
    HistoricRequirementAtt requirementAttToHistoricRequirementAtt(RequirementAtt requirementAtt);

    @Mapping(target = "id", source = "id", ignore = true)
    @Mapping(target = "srcId", source = "id")
    @Mapping(target = "requirement", source = "requirement", ignore = true)
    HistoricRequirementOaDatum requirementOaDatumToHistoricRequirementOaDatum(RequirementOaDatum requirementOaDatum);

    @Mapping(target = "id", source = "id", ignore = true)
    @Mapping(target = "srcId", source = "id")
    @Mapping(target = "requirement", source = "requirement", ignore = true)
    HistoricRequirementItem requirementItemToHistoricRequirementItem(RequirementItem requirementItem);

    @Mapping(target = "id", source = "id", ignore = true)
    @Mapping(target = "srcId", source = "id")
    @Mapping(target = "requirement", source = "requirement", ignore = true)
    HistoricRequirementSup requirementSupToHistoricRequirementSup(RequirementSup requirementSup);
}
