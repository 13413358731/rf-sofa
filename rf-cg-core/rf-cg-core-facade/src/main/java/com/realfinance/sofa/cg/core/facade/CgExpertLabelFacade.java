package com.realfinance.sofa.cg.core.facade;

import com.realfinance.sofa.cg.core.model.CgExpertLabelDto;
import com.realfinance.sofa.cg.core.model.CgExpertLabelQueryCriteria;
import com.realfinance.sofa.cg.core.model.CgExpertLabelSaveDto;
import com.realfinance.sofa.cg.core.model.CgExpertLabelSmallDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

public interface CgExpertLabelFacade {

    /**
     * 查询
     * @param queryCriteria
     * @return
     */
    List<CgExpertLabelDto> list(CgExpertLabelQueryCriteria queryCriteria);

    /**
     * 查询供应商参照
     * 该方法可以使用数据权限
     * @param queryCriteria
     * @param pageable
     * @return
     */
    Page<CgExpertLabelDto> queryRefer(CgExpertLabelQueryCriteria queryCriteria, @NotNull Pageable pageable);

    /**
     * 保存
     * @param saveDto
     * @return
     */
    Integer save(@NotNull CgExpertLabelSaveDto saveDto);

    /**
     * 删除
     * @param ids 供应商标签ID集合
     */
    void delete(@NotNull Set<Integer> ids);

    /**
     * 根据ID查询
     * @param expertLabelId 专家标签ID
     * @return
     */
    CgExpertLabelSmallDto queryExpertLabelSmallDto(@NotNull Integer expertLabelId);
}
