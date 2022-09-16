package com.realfinance.sofa.cg.service.mapstruct;

import com.realfinance.sofa.cg.core.model.CgGradeSupDto;
import com.realfinance.sofa.cg.model.cg.CgGradeSupVo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface CgGradeSupMapper {

//    CgGradeSupVo toVo(CgGradeSupSumDetailsDto dto);
    CgGradeSupVo toVo(CgGradeSupDto dto);

    CgGradeSupDto toSaveDto(CgGradeSupVo dto);

    List<CgGradeSupDto> toSaveDto(List<CgGradeSupVo> dto);



}
