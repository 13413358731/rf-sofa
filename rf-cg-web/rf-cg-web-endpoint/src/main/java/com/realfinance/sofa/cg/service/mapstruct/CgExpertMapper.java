package com.realfinance.sofa.cg.service.mapstruct;

import com.realfinance.sofa.cg.core.model.CgExpertDetailsDto;
import com.realfinance.sofa.cg.core.model.CgExpertDto;
import com.realfinance.sofa.cg.core.model.CgExpertSaveDto;
import com.realfinance.sofa.cg.core.model.CgExpertSmallDto;
import com.realfinance.sofa.cg.model.cg.CgExpertSaveRequest;
import com.realfinance.sofa.cg.model.cg.CgExpertVo;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface CgExpertMapper {

    CgExpertVo toVo(CgExpertSmallDto dto);
    CgExpertVo toVo(CgExpertDto dto);
    CgExpertVo toVo(CgExpertDetailsDto dto);

    CgExpertSaveDto toSaveDto(CgExpertVo vo);
}
