package com.realfinance.sofa.cg.core.facade;

import com.realfinance.sofa.cg.core.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.util.Set;

public interface CgCommodityFacade {

    Page<CgCommodityDto> list(CgCommodityQueryCriteria queryCriteria, @NotNull Pageable pageable);

//    CgContractManageDetailsDto getDetailsById(@NotNull Integer id);

    Integer save(@NotNull CgCommoditySaveDto saveDto);
    void delete(@NotNull Set<Integer> ids);

}
