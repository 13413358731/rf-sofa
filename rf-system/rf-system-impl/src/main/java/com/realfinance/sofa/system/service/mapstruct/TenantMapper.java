package com.realfinance.sofa.system.service.mapstruct;

import com.realfinance.sofa.system.domain.Tenant;
import com.realfinance.sofa.system.model.TenantDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface TenantMapper extends ToDtoMapper<Tenant, TenantDto> {
}
