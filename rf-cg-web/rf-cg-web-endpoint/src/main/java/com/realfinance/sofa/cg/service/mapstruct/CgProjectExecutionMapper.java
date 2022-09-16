package com.realfinance.sofa.cg.service.mapstruct;

import com.realfinance.sofa.cg.core.model.*;
import com.realfinance.sofa.cg.model.cg.CgBusinessReplyVo;
import com.realfinance.sofa.cg.model.cg.CgProjectExecutionSupVo;
import com.realfinance.sofa.cg.model.cg.CgProjectExecutionVo;
import com.realfinance.sofa.cg.sup.model.CgBusinessReplyDetailsDto;
import com.realfinance.sofa.cg.sup.model.CgBusinessReplyDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class, CgProjectMapper.class, CgSupplierMapper.class})
public interface CgProjectExecutionMapper {

    CgProjectExecutionVo toVo(CgProjectExecutionDto dto);

    CgProjectExecutionVo toVo(CgProjectForExecDto dto);

    CgProjectExecutionVo toVo(CgProjectExecutionDetailsDto dto);

    CgProjectExecutionDetailsSaveDto toSaveDto(CgProjectExecutionVo vo);

    CgBusinessReplyVo toVo(CgBusinessReplyDto dto);

    CgBusinessReplyVo toVo(CgBusinessReplyDetailsDto dto);

    List<CgBusinessReplyVo> toVo(List<CgBusinessReplyDto> dto);

    @Mapping(target = "supplier", source = "supplierId")
    CgProjectExecutionSupVo cgProjectExecutionSupDtoToCgProjectExecutionSupVo(CgProjectExecutionSupDto dto);

    @Mapping(target = "supplierId", source = "supplier")
    CgProjectExecutionSupDto cgProjectExecutionSupVoToCgProjectExecutionSupDto(CgProjectExecutionSupVo vo);


}
