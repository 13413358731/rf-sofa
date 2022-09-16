package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.req.*;
import com.realfinance.sofa.cg.core.model.*;
import com.realfinance.sofa.common.util.SpringContextHolder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface RequirementDetailsSaveMapper extends ToEntityMapper<Requirement, CgRequirementDetailsSaveDto> {

    default List<RequirementAtt> cgRequirementAttSaveDtoCollectionToRequirementAttList(Collection<CgAttSaveDto> list) {
        if ( list == null ) {
            return null;
        }

        List<RequirementAtt> list1 = new ArrayList<>( list.size() );
        CustomMapper customMapper = SpringContextHolder.getBean(CustomMapper.class);
        for ( CgAttSaveDto cgAttSaveDto : list ) {
            if (cgAttSaveDto == null) {
                continue;
            }
            if (cgAttSaveDto.getId() == null) {
                RequirementAtt entity = toEntity(cgAttSaveDto);
                entity.setSource(cgAttSaveDto.getSource());
                list1.add(entity );
            } else {
                RequirementAtt one = customMapper.resolveEntity(cgAttSaveDto.getId(),RequirementAtt.class);
                list1.add( updateEntity( one, cgAttSaveDto ) );
            }
        }

        return list1;
    }

    default List<RequirementItem> cgRequirementItemDtoCollectionToRequirementItemList(Collection<CgRequirementItemSaveDto> list) {
        if ( list == null ) {
            return null;
        }

        List<RequirementItem> list1 = new ArrayList<>( list.size() );
        CustomMapper customMapper = SpringContextHolder.getBean(CustomMapper.class);
        for ( CgRequirementItemSaveDto cgAttSaveDto : list ) {
            if (cgAttSaveDto == null) {
                continue;
            }
            if (cgAttSaveDto.getId() == null) {
                RequirementItem entity = toEntity(cgAttSaveDto);
                entity.setSource("REQUIREMENT");
                list1.add( entity );
            } else {
                RequirementItem one = customMapper.resolveEntity(cgAttSaveDto.getId(),RequirementItem.class);
                list1.add( updateEntity( one, cgAttSaveDto ) );
            }
        }

        return list1;
    }

    default List<RequirementSup> cgRequirementSupDtoCollectionToRequirementSupList(Collection<CgRequirementSupDto> set) {
        if ( set == null ) {
            return null;
        }

        List<RequirementSup> list = new ArrayList<RequirementSup>( set.size() );
        CustomMapper customMapper = SpringContextHolder.getBean(CustomMapper.class);
        for ( CgRequirementSupDto cgRequirementSupDto : set ) {
            if (cgRequirementSupDto == null) {
                continue;
            }
            if (cgRequirementSupDto.getId() == null) {
                RequirementSup entity = toEntity(cgRequirementSupDto);
                entity.setSource("REQUIREMENT");
                list.add( entity );
            } else {
                RequirementSup one = customMapper.resolveEntity(cgRequirementSupDto.getId(), RequirementSup.class);
                list.add( updateEntity( one, cgRequirementSupDto) );
            }
        }

        return list;
    }

    default List<RequirementOaDatum> cgRequirementOaDatumDtoCollectionToRequirementOaDatumList(Collection<CgRequirementOaDatumDto> set) {
        if ( set == null ) {
            return null;
        }

        List<RequirementOaDatum> list = new ArrayList<RequirementOaDatum>( set.size() );
        CustomMapper customMapper = SpringContextHolder.getBean(CustomMapper.class);
        for ( CgRequirementOaDatumDto cgRequirementItemSupDto : set ) {
            if (cgRequirementItemSupDto == null) {
                continue;
            }
            if (cgRequirementItemSupDto.getId() == null) {
                RequirementOaDatum entity = toEntity(cgRequirementItemSupDto);
                list.add( entity );
            } else {
                RequirementOaDatum one = customMapper.resolveEntity(cgRequirementItemSupDto.getId(),RequirementOaDatum.class);
                list.add( updateEntity( one, cgRequirementItemSupDto ) );
            }
        }

        return list;
    }

    RequirementAtt toEntity(CgAttSaveDto cgRequirementAttSaveDto);
    RequirementAtt updateEntity(@MappingTarget RequirementAtt entity, CgAttSaveDto saveDto);

    RequirementItem toEntity(CgRequirementItemSaveDto saveDto);
    RequirementItem updateEntity(@MappingTarget RequirementItem entity, CgRequirementItemSaveDto saveDto);

    RequirementSup toEntity(CgRequirementSupDto dto);
    RequirementSup updateEntity(@MappingTarget RequirementSup entity, CgRequirementSupDto dto);

    RequirementOaDatum toEntity(CgRequirementOaDatumDto dto);
    RequirementOaDatum updateEntity(@MappingTarget RequirementOaDatum entity, CgRequirementOaDatumDto dto);
}
