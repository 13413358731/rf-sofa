package com.realfinance.sofa.cg.service.mapstruct;

import com.realfinance.sofa.cg.core.model.CgPurResultAttDto;
import com.realfinance.sofa.cg.core.model.CgPurResultSupDto;
import com.realfinance.sofa.cg.core.model.CgPurchaseResultDetailsDto;
import com.realfinance.sofa.cg.core.model.CgPurchaseResultDto;
import com.realfinance.sofa.cg.model.cg.CgPurResultAttVo;
import com.realfinance.sofa.cg.model.cg.CgPurResultSupVo;
import com.realfinance.sofa.cg.model.cg.CgPurchaseResultVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class, CgProjectMapper.class, CgSupplierMapper.class})
public interface CgPurchaseResultMapper {

    @Mapping(target = "createdUser", source = "createdUserId")
    @Mapping(target = "modifiedUser", source = "modifiedUserId")
    @Mapping(target = "project",source = "projectId")
    CgPurchaseResultVo toVo(CgPurchaseResultDto dto);

    @Mapping(target = "createdUser", source = "createdUserId")
    @Mapping(target = "modifiedUser", source = "modifiedUserId")
    @Mapping(target = "project",source = "projectId")
    CgPurchaseResultVo toVo(CgPurchaseResultDetailsDto dto);

    @Mapping(target = "createdUser", source = "createdUserId")
    CgPurResultAttVo cgRequirementAttDtoToCgPurResultAttVo(CgPurResultAttDto cgPurResultAttDto);

    CgPurchaseResultDetailsDto toSaveDto(CgPurchaseResultVo vo);

    CgPurResultSupVo cgPurchaseResultSupDtoToCgPurchaseResultSupVo(CgPurResultSupDto cgPurResultSupDto);

//
//    CgBusinessReplyVo toVo(CgBusinessReplyDto dto);
//
//    List<CgBusinessReplyVo> toVo(List<CgBusinessReplyDto> dto);
//
//    @Mapping(target = "supplier", source = "supplierId")
//    CgProjectExecutionSupVo cgProjectExecutionSupDtoToCgProjectExecutionSupVo(CgProjectExecutionSupDto dto);
//    @Mapping(target = "supplierId", source = "supplier")
//    CgProjectExecutionSupDto cgProjectExecutionSupVoToCgProjectExecutionSupDto(CgProjectExecutionSupVo vo);


}
