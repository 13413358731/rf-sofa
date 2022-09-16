package com.realfinance.sofa.cg.core.facade;

import com.realfinance.sofa.cg.core.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

public interface CgDrawExpertListFacade {

    /**
     * 查询
     * 该方法可以使用数据权限
     * @param queryCriteria
     * @param pageable
     * @return
     */
    Page<CgDrawExpertListDto> list(CgDrawExpertListQueryCriteria queryCriteria, @NotNull Pageable pageable);

    /**
     * 查询
     * 该方法可以使用数据权限
     * @param queryCriteria
     * @return
     */
    List<CgDrawExpertListDto> list(CgDrawExpertListQueryCriteria queryCriteria);

    /**
     * 保存抽取列表直接指定的专家
     * @param id 专家抽取主表ID
     * @param saveDto 专家抽取列表对象
     * @return
     */
    Integer save(@NotNull Integer id, @NotNull CgDrawExpertListSaveDto saveDto);

    /**
     * 保存抽取列表条件抽取的专家
     * @param id 专家抽取主表ID
     * @return
     */
    Integer saveRandomly(@NotNull Integer id);

    /**
     * 删除
     * @param ids 抽取的专家列表ID集合
     */
    void delete(@NotNull Set<Integer> ids);

}
