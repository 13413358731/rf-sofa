package com.realfinance.sofa.system.service.mapstruct;

import com.realfinance.sofa.system.domain.TextTemplate;
import com.realfinance.sofa.system.model.TextTemplateDetailsDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface TextTemplateDetailsMapper extends ToDtoMapper<TextTemplate, TextTemplateDetailsDto> {
}
