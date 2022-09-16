package com.realfinance.sofa.cg.service.mapstruct;

import com.realfinance.sofa.cg.model.flow.BizModelSaveRequest;
import com.realfinance.sofa.cg.model.flow.BizModelVo;
import com.realfinance.sofa.flow.model.BizModelDto;
import com.realfinance.sofa.flow.model.BizModelSaveDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface FlowBizModelMapper {

    @Mapping(target = "department", source = "departmentId")
    @Mapping(target = "createdUser", source = "createdUserId")
    @Mapping(target = "modifiedUser", source = "modifiedUserId")
    BizModelVo bizModelDto2BizModelVo(BizModelDto bizModelDto);

    @Mapping(target = "departmentId", source = "department")
    BizModelSaveDto bizModelSaveRequest2BizModelSaveDto(BizModelSaveRequest bizModelSaveRequest);
}
