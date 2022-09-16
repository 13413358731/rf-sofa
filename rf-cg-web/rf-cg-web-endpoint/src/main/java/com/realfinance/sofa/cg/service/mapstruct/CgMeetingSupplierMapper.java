package com.realfinance.sofa.cg.service.mapstruct;

import com.realfinance.sofa.cg.core.model.CgMeetingConfereeDto;
import com.realfinance.sofa.cg.core.model.CgProjectExecutionSupDto;
import com.realfinance.sofa.cg.model.cg.CgMeetingConfereeVo;
import com.realfinance.sofa.cg.model.cg.CgProjectExecutionSupVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface CgMeetingSupplierMapper {

    @Mapping(target = "supplier", source = "supplierId")
    CgProjectExecutionSupVo toVo(CgProjectExecutionSupDto dto);
//    CgMeetingConfereeVo toVo(CgMeetingDetailsDto dto);

    CgProjectExecutionSupDto toSaveDto(CgProjectExecutionSupVo dto);



}
