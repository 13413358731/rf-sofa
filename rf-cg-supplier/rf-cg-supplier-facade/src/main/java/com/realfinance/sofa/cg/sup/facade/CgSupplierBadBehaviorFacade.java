package com.realfinance.sofa.cg.sup.facade;

import com.realfinance.sofa.cg.sup.model.CgSupplierBadBehaviorDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierBadBehaviorQueryCriteria;
import com.realfinance.sofa.cg.sup.model.CgSupplierBadBehaviorSaveDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.util.Set;

public interface CgSupplierBadBehaviorFacade {

    /**
     * 保存更新
     *
     * @param saveDto
     */
    Integer save(CgSupplierBadBehaviorSaveDto saveDto);

    /**
     * 删除
     *
     * @param ids
     */
    void delete(@NotNull Set<Integer> ids);


    /**
     * 列表
     *
     * @param
     */
    Page<CgSupplierBadBehaviorDto>  list(Pageable pageable, CgSupplierBadBehaviorQueryCriteria queryCriteria);

}
