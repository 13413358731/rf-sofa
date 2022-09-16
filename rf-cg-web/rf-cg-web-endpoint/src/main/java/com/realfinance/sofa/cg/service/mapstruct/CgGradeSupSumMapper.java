package com.realfinance.sofa.cg.service.mapstruct;

import com.realfinance.sofa.cg.core.model.CgGradeSupSumDetailsDto;
import com.realfinance.sofa.cg.core.model.CgGradeSupSumDto;
import com.realfinance.sofa.cg.model.cg.CgGradeSupSumVo;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface CgGradeSupSumMapper {

    CgGradeSupSumVo toVo(CgGradeSupSumDetailsDto dto);
    CgGradeSupSumVo toVo(CgGradeSupSumDto dto);

    CgGradeSupSumDto toSaveDto(CgGradeSupSumVo dto);



}
