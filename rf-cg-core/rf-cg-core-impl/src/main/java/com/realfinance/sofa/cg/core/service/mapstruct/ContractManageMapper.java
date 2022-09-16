package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.contract.ContractManage;
import com.realfinance.sofa.cg.core.model.CgContractManageDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface ContractManageMapper extends ToDtoMapper<ContractManage, CgContractManageDto> {
}
