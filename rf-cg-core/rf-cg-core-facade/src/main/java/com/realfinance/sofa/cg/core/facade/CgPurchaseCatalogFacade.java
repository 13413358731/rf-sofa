package com.realfinance.sofa.cg.core.facade;

import com.realfinance.sofa.cg.core.model.CgPurchaseCatalogDto;
import com.realfinance.sofa.cg.core.model.CgPurchaseCatalogQueryCriteria;
import com.realfinance.sofa.cg.core.model.CgPurchaseCatalogSaveDto;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

public interface CgPurchaseCatalogFacade {

    List<CgPurchaseCatalogDto> list(CgPurchaseCatalogQueryCriteria queryCriteria);

    Integer save(@NotNull CgPurchaseCatalogSaveDto saveDto);

    /**
     * 批量更新启用状态
     * @param ids 采购目录ID集合
     * @param enabled 启用状态
     */
    void updateEnabled(@NotNull Set<Integer> ids, @NotNull Boolean enabled);

    CgPurchaseCatalogDto  getById(@NotNull Integer id);

}
