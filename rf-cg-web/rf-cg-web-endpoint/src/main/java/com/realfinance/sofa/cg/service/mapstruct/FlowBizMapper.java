package com.realfinance.sofa.cg.service.mapstruct;

import com.realfinance.sofa.cg.model.flow.BizSaveRequest;
import com.realfinance.sofa.cg.model.flow.BizVo;
import com.realfinance.sofa.flow.model.BizDto;
import com.realfinance.sofa.flow.model.BizSaveDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface FlowBizMapper {

    @Mapping(target = "createdUser", source = "createdUserId")
    @Mapping(target = "modifiedUser", source = "modifiedUserId")
    BizVo bizDto2BizVo(BizDto bizDto);

    BizSaveDto bizSaveRequest2BizSaveDto(BizSaveRequest bizSaveRequest);
}
