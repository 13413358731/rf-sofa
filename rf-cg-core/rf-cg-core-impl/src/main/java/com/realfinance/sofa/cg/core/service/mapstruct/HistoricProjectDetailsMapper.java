package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.proj.*;
import com.realfinance.sofa.cg.core.model.CgProjectDetailsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface HistoricProjectDetailsMapper extends ToDtoMapper<HistoricProject, CgProjectDetailsDto> {

    @Mapping(target = "createdUserId", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "modifiedUserId", ignore = true)
    @Mapping(target = "modifiedTime", ignore = true)
    @Mapping(target = "v", ignore = true)
    @Mapping(target = "id", source = "id", ignore = true)
    @Mapping(target = "srcId", source = "id")
    HistoricProject fromProject(Project project);

    @Mapping(target = "id", source = "id", ignore = true)
    @Mapping(target = "srcId", source = "id")
    @Mapping(target = "project", source = "project", ignore = true)
    HistoricProjectAtt projectAttToHistoricProjectAtt(ProjectAtt projectAtt);

    @Mapping(target = "id", source = "id", ignore = true)
    @Mapping(target = "srcId", source = "id")
    @Mapping(target = "project", source = "project", ignore = true)
    HistoricProjectOaDatum projectOaDatumToHistoricProjectOaDatum(ProjectOaDatum projectOaDatum);

    @Mapping(target = "id", source = "id", ignore = true)
    @Mapping(target = "srcId", source = "id")
    @Mapping(target = "project", source = "project", ignore = true)
    HistoricProjectItem projectItemToHistoricProjectItem(ProjectItem projectItem);

    @Mapping(target = "id", source = "id", ignore = true)
    @Mapping(target = "srcId", source = "id")
    @Mapping(target = "project", source = "project", ignore = true)
    HistoricProjectSup projectSupToHistoricProjectSup(ProjectSup projectSup);

    @Mapping(target = "id", source = "id", ignore = true)
    @Mapping(target = "srcId", source = "id")
    @Mapping(target = "project", source = "project", ignore = true)
    HistoricProjectEval projectEvalToHistoricProjectEval(ProjectEval projectEval);

    @Mapping(target = "id", source = "id", ignore = true)
    @Mapping(target = "srcId", source = "id")
    @Mapping(target = "projectEval", source = "projectEval", ignore = true)
    HistoricProjectEvalRule projectEvalRuleToHistoricProjectEvalRule(ProjectEvalRule projectEvalRule);

    @Mapping(target = "id", source = "id", ignore = true)
    @Mapping(target = "srcId", source = "id")
    @Mapping(target = "project", source = "project", ignore = true)
    HistoricProjectDrawExpertRule projectDrawExpertRuleToHistoricProjectDrawExpertRule(ProjectDrawExpertRule projectDrawExpertRule);
}
