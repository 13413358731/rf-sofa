package com.realfinance.sofa.cg.service.mapstruct;

import com.realfinance.sofa.cg.core.model.CgProjectEvalDto;
import com.realfinance.sofa.cg.model.cg.CgProjectEvalVo;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class, CgProjectMapper.class, CgSupplierMapper.class})
public interface CgProjectEvalMapper {

    CgProjectEvalVo toVo(CgProjectEvalDto dto);

    CgProjectEvalDto toSaveDto(CgProjectEvalVo vo);

}
