package com.realfinance.sofa.cg.core.service.mapstruct;

import org.mapstruct.MappingTarget;

public interface ToEntityMapper<Entity,Dto> {

    Entity toEntity(Dto dto);

    Entity updateEntity(@MappingTarget Entity target, Dto from);
}
