package com.realfinance.sofa.cg.service.mapstruct;

import com.realfinance.sofa.cg.model.cg.CgSupplierEvaluationSheetMainVo;
import com.realfinance.sofa.cg.sup.model.CgSupplierEvaluationSheetDetailsDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierEvaluationSheetDetailsSaveDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierEvaluationSheetMainDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface CgSupplierEvaluationSheetMapper {

    CgSupplierEvaluationSheetMainVo toVo(CgSupplierEvaluationSheetMainDto dto);

    CgSupplierEvaluationSheetMainVo toVo(CgSupplierEvaluationSheetDetailsDto dto);

    CgSupplierEvaluationSheetMainVo toVo(CgSupplierEvaluationSheetDetailsSaveDto dto);

    CgSupplierEvaluationSheetDetailsSaveDto toSaveDto(CgSupplierEvaluationSheetMainVo vo);
}
