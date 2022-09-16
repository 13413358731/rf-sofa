package com.realfinance.sofa.cg.service.mapstruct;

import com.realfinance.sofa.cg.model.cg.CgSupplierEvaluationMainVo;
import com.realfinance.sofa.cg.sup.model.*;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface CgSupplierEvaluationMainMapper {

    CgSupplierEvaluationMainVo toVo(CgSupplierEvaluationMainDto dto);

    CgSupplierEvaluationMainVo toVo(CgSupplierEvaluationMainDetailsDto dto);

    CgSupplierEvaluationMainDetailsSaveDto toSaveDto(CgSupplierEvaluationMainVo vo);
}
