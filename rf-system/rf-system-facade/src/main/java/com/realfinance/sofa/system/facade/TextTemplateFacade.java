package com.realfinance.sofa.system.facade;

import com.realfinance.sofa.system.model.TextTemplateDetailsDto;
import com.realfinance.sofa.system.model.TextTemplateDto;
import com.realfinance.sofa.system.model.TextTemplateQueryCriteria;
import com.realfinance.sofa.system.model.TextTemplateSaveDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.util.Set;

public interface TextTemplateFacade {
    Page<TextTemplateDto> list(TextTemplateQueryCriteria queryCriteria, @NotNull Pageable pageable);

    TextTemplateDetailsDto getDetailsById(@NotNull Integer id);

    String getText(@NotNull Integer id);

    Integer save(@NotNull TextTemplateSaveDto saveDto);

    void delete(@NotNull Set<Integer> ids);
}
