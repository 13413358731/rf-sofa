package com.realfinance.sofa.cg.service.mapstruct;

import com.realfinance.sofa.cg.core.model.CgMeetingConfereeDto;
import com.realfinance.sofa.cg.core.model.CgProjectExecutionAttDto;
import com.realfinance.sofa.cg.model.cg.CgAttVo;
import com.realfinance.sofa.cg.model.cg.CgMeetingConfereeVo;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface CgMeetingFileMapper {

    CgAttVo toVo(CgProjectExecutionAttDto dto);
//    CgMeetingConfereeVo toVo(CgMeetingDetailsDto dto);

//    CgProjectExecutionAttDto toSaveDto(CgAttVo dto);



}
