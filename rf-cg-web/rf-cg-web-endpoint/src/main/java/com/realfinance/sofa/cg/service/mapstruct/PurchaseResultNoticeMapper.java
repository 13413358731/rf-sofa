package com.realfinance.sofa.cg.service.mapstruct;

import com.realfinance.sofa.cg.core.model.CgAttSaveDto;
import com.realfinance.sofa.cg.core.model.CgPurchaseResultNoticeDto;
import com.realfinance.sofa.cg.core.model.CgPurchaseResultNoticeSaveDto;
import com.realfinance.sofa.cg.core.model.CgRequirementAttDto;
import com.realfinance.sofa.cg.model.cg.CgAttVo;
import com.realfinance.sofa.cg.model.cg.CgPurchaseResultNoticeSaveRequset;
import com.realfinance.sofa.cg.model.cg.CgPurchaseResultNoticeVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface PurchaseResultNoticeMapper {



    @Mapping(target = "createdUser", source = "createdUserId")
    @Mapping(target = "modifiedUser", source = "modifiedUserId")
    @Mapping(target = "contractProducer", source = "contractProducerId")
    @Mapping(target = "projectProducer", source = "projectProducerId")
    CgPurchaseResultNoticeVo toVo(CgPurchaseResultNoticeDto dto);

    @Mapping(target = "projectId", source = "projectId")
    @Mapping(target = "projectExecutionId", source = "projectExecutionId")
    @Mapping(target = "projectProducerId", source = " projectProducer")
    @Mapping(target = "contractProducerId", source = "contractProducer")
    CgPurchaseResultNoticeSaveDto toSaveDto(CgPurchaseResultNoticeSaveRequset requset);


    @Mapping(target = "createdUser", source = "createdUserId")
    CgAttVo cgAttSaveDtoToCgAttVo(CgAttSaveDto cgAttSaveDto);
}
