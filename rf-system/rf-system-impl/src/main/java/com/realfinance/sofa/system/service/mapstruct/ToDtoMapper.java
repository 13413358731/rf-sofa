package com.realfinance.sofa.system.service.mapstruct;

import org.mapstruct.InheritConfiguration;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface ToDtoMapper<Entity,Dto> {
    @InheritConfiguration
    Dto toDto(Entity entity);
    @InheritConfiguration
    List<Dto> toDtoList(Collection<Entity> entities);
    @InheritConfiguration
    Set<Dto> toDtoSet(Collection<Entity> entities);
}
