package com.realfinance.sofa.flow.facade;

import com.realfinance.sofa.flow.model.BizDto;
import com.realfinance.sofa.flow.model.BizSaveDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.util.Set;

public interface BizMngFacade {

    Page<BizDto> list(String filter, @NotNull Pageable pageable);

    Integer save(@NotNull BizSaveDto saveDto);

    void delete(@NotNull Set<Integer> ids);
}
