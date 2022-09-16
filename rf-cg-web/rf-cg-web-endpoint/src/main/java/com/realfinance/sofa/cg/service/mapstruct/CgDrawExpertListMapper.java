package com.realfinance.sofa.cg.service.mapstruct;

import com.realfinance.sofa.cg.core.model.CgDrawExpertDto;
import com.realfinance.sofa.cg.core.model.CgDrawExpertListDto;
import com.realfinance.sofa.cg.core.model.CgDrawExpertListSaveDto;
import com.realfinance.sofa.cg.core.model.CgDrawExpertSaveDto;
import com.realfinance.sofa.cg.model.cg.CgDrawExpertListVo;
import com.realfinance.sofa.cg.model.cg.CgDrawExpertVo;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface CgDrawExpertListMapper {

//    CgDrawExpertVo toVo(CgDrawExpertSmallDto dto);
    CgDrawExpertListVo toVo(CgDrawExpertListDto dto);
//    CgDrawExpertVo toVo(CgDrawExpertDetailsDto dto);

    CgDrawExpertListSaveDto toSaveDto(CgDrawExpertListVo vo);
}
