package com.realfinance.sofa.cg.sup.facade;

import com.realfinance.sofa.cg.sup.model.CgSupplierLabelDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierLabelQueryCriteria;
import com.realfinance.sofa.cg.sup.model.CgSupplierLabelSaveDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierLabelSmallDto;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

public interface CgSupplierLabelFacade {

    /**
     * 查询
     * @param queryCriteria
     * @return
     */
    List<CgSupplierLabelDto> list(CgSupplierLabelQueryCriteria queryCriteria);

    /**
     * 保存
     * @param saveDto
     * @return
     */
    Integer save(@NotNull CgSupplierLabelSaveDto saveDto);

    /**
     * 删除
     * @param ids 供应商标签ID集合
     */
    void delete(@NotNull Set<Integer> ids);

    CgSupplierLabelSmallDto querySupplierLabelSmallDto(@NotNull Integer supplierLabelSmallId);
}
