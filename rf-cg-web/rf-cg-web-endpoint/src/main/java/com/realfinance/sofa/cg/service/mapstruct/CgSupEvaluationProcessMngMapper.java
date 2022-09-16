package com.realfinance.sofa.cg.service.mapstruct;

import com.realfinance.sofa.cg.model.cg.CgSupEvaluationProcessMngVo;
import com.realfinance.sofa.cg.sup.model.*;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface CgSupEvaluationProcessMngMapper {

    CgSupEvaluationProcessMngVo toVo(CgSupEvaluationProcessMngDto dto);

    CgSupEvaluationProcessMngVo toVo(CgSupEvaluationProcessMngDetailsDto dto);

    CgSupEvaluationProcessMngDetailsSaveDto toSaveDto(CgSupEvaluationProcessMngVo vo);
}
