package com.realfinance.sofa.cg.sup.service.mapstruct;

import org.mapstruct.MappingTarget;

public interface ToEntityMapper<Entity,Dto> {

    Entity toEntity(Dto dto);

    Entity updateEntity(@MappingTarget Entity target, Dto from);
}
