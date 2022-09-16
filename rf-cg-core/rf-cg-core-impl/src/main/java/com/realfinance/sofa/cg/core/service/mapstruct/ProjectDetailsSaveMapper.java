package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.proj.*;
import com.realfinance.sofa.cg.core.model.*;
import com.realfinance.sofa.common.util.SpringContextHolder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface ProjectDetailsSaveMapper extends ToEntityMapper<Project, CgProjectDetailsSaveDto> {

    default List<ProjectAtt> cgProjectAttSaveDtoCollectionToProjectAttList(Collection<CgAttSaveDto> list) {
        if ( list == null ) {
            return null;
        }

        List<ProjectAtt> list1 = new ArrayList<ProjectAtt>( list.size() );
        CustomMapper customMapper = SpringContextHolder.getBean(CustomMapper.class);
        for ( CgAttSaveDto cgRequirementAttSaveDto : list ) {
            if (cgRequirementAttSaveDto == null) {
                continue;
            }
            if (cgRequirementAttSaveDto.getId() == null) {
                ProjectAtt entity = toEntity(cgRequirementAttSaveDto);
//                entity.setSource("PROJECT");
                entity.setSource(cgRequirementAttSaveDto.getSource());
                list1.add( entity );
            } else {
                ProjectAtt one = customMapper.resolveEntity(cgRequirementAttSaveDto.getId(),ProjectAtt.class);
                list1.add( updateEntity( one, cgRequirementAttSaveDto ) );
            }
        }

        return list1;
    }

    default List<ProjectItem> cgProjectItemDtoCollectionToProjectItemList(Collection<CgProjectItemSaveDto> list) {
        if ( list == null ) {
            return null;
        }

        List<ProjectItem> list1 = new ArrayList<>( list.size() );
        CustomMapper customMapper = SpringContextHolder.getBean(CustomMapper.class);
        for ( CgProjectItemSaveDto cgAttSaveDto : list ) {
            if (cgAttSaveDto == null) {
                continue;
            }
            if (cgAttSaveDto.getId() == null) {
                ProjectItem entity = toEntity(cgAttSaveDto);
                entity.setSource("PROJECT");
                list1.add( entity );
            } else {
                ProjectItem one = customMapper.resolveEntity(cgAttSaveDto.getId(),ProjectItem.class);
                list1.add( updateEntity( one, cgAttSaveDto ) );
            }
        }

        return list1;
    }

    default List<ProjectSup> cgProjectSupDtoCollectionToProjectSupList(Collection<CgProjectSupDto> set) {
        if ( set == null ) {
            return null;
        }

        List<ProjectSup> list = new ArrayList<ProjectSup>( set.size() );
        CustomMapper customMapper = SpringContextHolder.getBean(CustomMapper.class);
        for ( CgProjectSupDto cgProjectSupDto : set ) {
            if (cgProjectSupDto == null) {
                continue;
            }
            if (cgProjectSupDto.getId() == null) {
                ProjectSup entity = toEntity(cgProjectSupDto);
                entity.setSource("PROJECT");
                list.add( entity );
            } else {
                ProjectSup one = customMapper.resolveEntity(cgProjectSupDto.getId(), ProjectSup.class);
                list.add( updateEntity( one, cgProjectSupDto) );
            }
        }

        return list;
    }

    default List<ProjectEval> cgProjectEvalDtoCollectionToProjectEvalList(Collection<CgProjectEvalDto> set) {
        if ( set == null ) {
            return null;
        }

        List<ProjectEval> list = new ArrayList<ProjectEval>( set.size() );
        CustomMapper customMapper = SpringContextHolder.getBean(CustomMapper.class);
        for ( CgProjectEvalDto cgProjectEvalDto : set ) {
            if (cgProjectEvalDto == null) {
                continue;
            }
            if (cgProjectEvalDto.getId() == null) {
                list.add( toEntity( cgProjectEvalDto ) );
            } else {
                ProjectEval one = customMapper.resolveEntity(cgProjectEvalDto.getId(),ProjectEval.class);
                list.add( updateEntity( one, cgProjectEvalDto ) );
            }
        }

        return list;
    }

    default List<ProjectEvalRule> cgProjectEvalRuleDtoCollectionToProjectEvalRuleList(Collection<CgProjectEvalRuleDto> set) {
        if ( set == null ) {
            return null;
        }

        List<ProjectEvalRule> list = new ArrayList<ProjectEvalRule>( set.size() );
        CustomMapper customMapper = SpringContextHolder.getBean(CustomMapper.class);
        for ( CgProjectEvalRuleDto cgProjectEvalDto : set ) {
            if (cgProjectEvalDto == null) {
                continue;
            }
            if (cgProjectEvalDto.getId() == null) {
                list.add( toEntity( cgProjectEvalDto ) );
            } else {
                ProjectEvalRule one = customMapper.resolveEntity(cgProjectEvalDto.getId(),ProjectEvalRule.class);
                list.add( updateEntity( one, cgProjectEvalDto ) );
            }
        }

        return list;
    }

    default List<ProjectDrawExpertRule> cgProjectDrawExpertRuleDtoListToProjectDrawExpertRuleList(List<CgProjectDrawExpertRuleDto> set) {
        if ( set == null ) {
            return null;
        }

        List<ProjectDrawExpertRule> list = new ArrayList<ProjectDrawExpertRule>( set.size() );
        CustomMapper customMapper = SpringContextHolder.getBean(CustomMapper.class);
        for ( CgProjectDrawExpertRuleDto cgProjectDrawExpertRuleDto : set ) {
            if (cgProjectDrawExpertRuleDto == null) {
                continue;
            }
            if (cgProjectDrawExpertRuleDto.getId() == null) {
                list.add( toEntity( cgProjectDrawExpertRuleDto ) );
            } else {
                ProjectDrawExpertRule one = customMapper.resolveEntity(cgProjectDrawExpertRuleDto.getId(),ProjectDrawExpertRule.class);
                list.add( updateEntity( one, cgProjectDrawExpertRuleDto ) );
            }
        }

        return list;
    }

    ProjectAtt toEntity(CgAttSaveDto saveDto);
    ProjectAtt updateEntity(@MappingTarget ProjectAtt entity, CgAttSaveDto saveDto);

    ProjectItem toEntity(CgProjectItemSaveDto saveDto);
    ProjectItem updateEntity(@MappingTarget ProjectItem entity, CgProjectItemSaveDto saveDto);

    ProjectSup toEntity(CgProjectSupDto cgProjectSupDto);
    ProjectSup updateEntity(@MappingTarget ProjectSup entity, CgProjectSupDto dto);

    ProjectEval toEntity(CgProjectEvalDto dto);
    ProjectEval updateEntity(@MappingTarget ProjectEval entity, CgProjectEvalDto dto);

    ProjectEvalRule toEntity(CgProjectEvalRuleDto dto);
    ProjectEvalRule updateEntity(@MappingTarget ProjectEvalRule entity, CgProjectEvalRuleDto dto);

    ProjectDrawExpertRule toEntity(CgProjectDrawExpertRuleDto dto);
    ProjectDrawExpertRule updateEntity(@MappingTarget ProjectDrawExpertRule entity, CgProjectDrawExpertRuleDto dto);
}
