package com.realfinance.sofa.cg.service.mapstruct;

import com.realfinance.sofa.cg.core.model.*;
import com.realfinance.sofa.cg.model.cg.CgDrawExpertVo;
import com.realfinance.sofa.cg.model.cg.CgExpertConfirmVo;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface CgExpertConfirmMapper {

    CgExpertConfirmVo toVo(CgExpertConfirmDto dto);
//    CgExpertComfirmVo toVo(CgExpertCOmfirmDetailsDto dto);

    CgExpertConfirmSaveDto toSaveDto(CgExpertConfirmVo vo);
}
