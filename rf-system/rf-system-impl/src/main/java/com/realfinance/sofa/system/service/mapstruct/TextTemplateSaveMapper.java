package com.realfinance.sofa.system.service.mapstruct;

import com.realfinance.sofa.system.domain.TextTemplate;
import com.realfinance.sofa.system.model.TextTemplateSaveDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface TextTemplateSaveMapper extends ToEntityMapper<TextTemplate, TextTemplateSaveDto> {
}
