package com.realfinance.sofa.system.service.mapstruct;

import org.mapstruct.MappingTarget;

public interface ToEntityMapper<Entity,Dto> {

    Entity toEntity(Dto dto);

    Entity updateEntity(@MappingTarget Entity target, Dto from);
}
