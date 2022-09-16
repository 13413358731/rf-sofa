package com.realfinance.sofa.cg.sup.facade;

import com.realfinance.sofa.cg.sup.model.CgSupplierLabelTypeDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierLabelTypeSaveDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.util.Set;

public interface CgSupplierLabelTypeFacade {

    /**
     * 查询
     * @param filter 过滤条件
     * @param pageable
     * @return
     */
    Page<CgSupplierLabelTypeDto> list(String filter, @NotNull Pageable pageable);

    /**
     * 保存
     * @param saveDto
     * @return
     */
    Integer save(@NotNull CgSupplierLabelTypeSaveDto saveDto);

    /**
     * 删除
     * @param ids 标签类型ID集合
     */
    void delete(@NotNull Set<Integer> ids);
}
