package com.realfinance.sofa.cg.core.facade;

import com.realfinance.sofa.cg.core.model.CgDepartmentDelegateDto;
import com.realfinance.sofa.cg.core.model.CgDrawExpertDto;
import com.realfinance.sofa.cg.core.model.CgDrawExpertQueryCriteria;
import com.realfinance.sofa.cg.core.model.CgDrawExpertSaveDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.util.Set;

public interface CgDepartmentDelegateFacade {

    /**
     * 查询
     * 该方法可以使用数据权限
     * @param queryCriteria
     * @param pageable
     * @return
     */
    Page<CgDepartmentDelegateDto> list(CgDrawExpertQueryCriteria queryCriteria, @NotNull Pageable pageable);

    /**
     * 保存
     * @param saveDto
     * @return
     */
    Integer save(@NotNull CgDrawExpertSaveDto saveDto);

    /**
     * 删除
     * @param ids 黑名单ID集合
     */
    void delete(@NotNull Set<Integer> ids);

}
