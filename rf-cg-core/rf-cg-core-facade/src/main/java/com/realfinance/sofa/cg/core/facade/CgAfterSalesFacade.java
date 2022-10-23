package com.realfinance.sofa.cg.core.facade;

import com.realfinance.sofa.cg.core.model.CgAfterSalesDto;
import com.realfinance.sofa.cg.core.model.CgAfterSalesQueryCriteria;
import com.realfinance.sofa.cg.core.model.CgCommodityDto;
import com.realfinance.sofa.cg.core.model.CgCommodityQueryCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.util.Set;

public interface CgAfterSalesFacade {

    Page<CgAfterSalesDto> list(CgAfterSalesQueryCriteria queryCriteria, @NotNull Pageable pageable);

//    CgContractManageDetailsDto getDetailsById(@NotNull Integer id);

    Integer save(@NotNull CgAfterSalesDto saveDto);
//
    void delete(@NotNull Set<Integer> ids);

}
