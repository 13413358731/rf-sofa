package com.realfinance.sofa.cg.core.facade;

import com.realfinance.sofa.cg.core.model.CgMultipleReleaseDetailsDto;
import com.realfinance.sofa.cg.core.model.CgMultipleReleaseDetailsSaveDto;
import com.realfinance.sofa.cg.core.model.CgMultipleReleaseDto;
import com.realfinance.sofa.cg.core.model.CgMultipleReleaseQueryCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;

public interface CgMultipleReleaseFacade {

    Page<CgMultipleReleaseDto> list(CgMultipleReleaseQueryCriteria queryCriteria, @NotNull Pageable pageable);

    CgMultipleReleaseDto getById(@NotNull Integer id);

    CgMultipleReleaseDetailsDto getDetailsById(@NotNull Integer id);

    Integer save(@NotNull CgMultipleReleaseDetailsSaveDto saveDto);

    void updateStatus(Integer id, String status);
}
