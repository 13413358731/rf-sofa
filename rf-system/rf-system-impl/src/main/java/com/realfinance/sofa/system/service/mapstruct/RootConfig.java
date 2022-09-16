package com.realfinance.sofa.system.service.mapstruct;

import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

@MapperConfig(
        uses = {CustomMapper.class},
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RootConfig {


}
