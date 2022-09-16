package com.realfinance.sofa.cg.core.facade;

import com.realfinance.sofa.cg.core.model.CgExpertLabelTypeDto;
import com.realfinance.sofa.cg.core.model.CgExpertLabelTypeSaveDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.util.Set;

public interface CgExpertLabelTypeFacade {

    /**
     * 查询
     * @param filter 过滤条件
     * @param pageable
     * @return
     */
    Page<CgExpertLabelTypeDto> list(String filter, @NotNull Pageable pageable);

   /**
     * 保存
     * @param saveDto
     * @return
     */
    Integer save(@NotNull CgExpertLabelTypeSaveDto saveDto);

    /**
     * 删除
     * @param ids 标签类型ID集合
     */
    void delete(@NotNull Set<Integer> ids);
}
