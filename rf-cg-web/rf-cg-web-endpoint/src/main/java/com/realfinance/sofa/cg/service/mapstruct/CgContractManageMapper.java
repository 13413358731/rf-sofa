package com.realfinance.sofa.cg.service.mapstruct;

import com.realfinance.sofa.cg.core.model.*;
import com.realfinance.sofa.cg.model.cg.CgContractManageVo;
import org.mapstruct.Mapper;


@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface CgContractManageMapper {

    CgContractManageVo toVo(CgContractManageDto cgContractManageDto);

    CgContractManageVo toVo(CgContractManageDetailsDto cgContractManageDetailsDto);

    CgContractManageSaveDto toSaveDto(CgContractManageVo vo);


}
