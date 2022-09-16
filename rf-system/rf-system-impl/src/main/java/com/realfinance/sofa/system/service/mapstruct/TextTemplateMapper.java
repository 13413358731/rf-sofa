package com.realfinance.sofa.system.service.mapstruct;

import com.realfinance.sofa.system.domain.TextTemplate;
import com.realfinance.sofa.system.model.TextTemplateDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface TextTemplateMapper extends ToDtoMapper<TextTemplate, TextTemplateDto> {
}
