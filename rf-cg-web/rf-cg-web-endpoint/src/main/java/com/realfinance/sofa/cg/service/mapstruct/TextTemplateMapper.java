package com.realfinance.sofa.cg.service.mapstruct;

import com.realfinance.sofa.cg.model.system.TextTemplateVo;
import com.realfinance.sofa.system.model.TextTemplateDetailsDto;
import com.realfinance.sofa.system.model.TextTemplateDto;
import com.realfinance.sofa.system.model.TextTemplateSaveDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface TextTemplateMapper {

    @Mapping(target = "createdUser", source = "createdUserId")
    @Mapping(target = "modifiedUser", source = "modifiedUserId")
    @Mapping(target = "department", source = "departmentId")
    TextTemplateVo toVo(TextTemplateDto dto);

    @Mapping(target = "createdUser", source = "createdUserId")
    @Mapping(target = "modifiedUser", source = "modifiedUserId")
    @Mapping(target = "department", source = "departmentId")
    TextTemplateVo toVo(TextTemplateDetailsDto dto);

    TextTemplateSaveDto toSaveDto(TextTemplateVo vo);
}
