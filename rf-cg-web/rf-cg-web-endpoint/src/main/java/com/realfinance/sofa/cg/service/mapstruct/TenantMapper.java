package com.realfinance.sofa.cg.service.mapstruct;

import com.realfinance.sofa.cg.model.system.TenantSaveRequest;
import com.realfinance.sofa.cg.model.system.TenantVo;
import com.realfinance.sofa.system.model.TenantDto;
import com.realfinance.sofa.system.model.TenantSaveDto;
import com.realfinance.sofa.system.model.TenantSmallDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface TenantMapper {

    TenantVo tenantSmallDto2TenantVo(TenantSmallDto tenantSmallDto);

    @Mapping(target = "createdUser", source = "createdUserId")
    @Mapping(target = "modifiedUser", source = "modifiedUserId")
    TenantVo tenantDto2TenantVo(TenantDto tenantDto);

    TenantSaveDto tenantSaveRequest2TenantSaveDto(TenantSaveRequest tenantSaveRequest);
}
