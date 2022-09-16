package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.ExpertConfirm;
import com.realfinance.sofa.cg.core.model.CgExpertConfirmDto;
import com.realfinance.sofa.cg.core.model.CgExpertConfirmSaveDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface ExpertConfirmSaveMapper extends ToEntityMapper<ExpertConfirm, CgExpertConfirmSaveDto> {

}
