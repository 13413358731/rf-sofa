package com.realfinance.sofa.system.service.mapstruct;

import com.realfinance.sofa.system.domain.Tenant;
import com.realfinance.sofa.system.model.TenantSaveDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface TenantSaveMapper extends ToEntityMapper<Tenant, TenantSaveDto> {
}
