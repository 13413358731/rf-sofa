package com.realfinance.sofa.cg.core.facade;

import com.realfinance.sofa.cg.core.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

public interface CgDrawExpertFacade {

    /**
     * 查询
     * 该方法可以使用数据权限
     * @param queryCriteria
     * @param pageable
     * @return
     */
    Page<CgDrawExpertDto> list(CgDrawExpertQueryCriteria queryCriteria, @NotNull Pageable pageable);

    /**
     * 获取详情，包括子表数据
     * 该方法可以使用数据权限
     * @param id 供应商ID
     * @return
     */
    CgDrawExpertDetailsDto getDetailsById(@NotNull Integer id);

    /**
     * 根据方案id查询专家抽取详情
     * @param id
     * @return
     */
    CgDrawExpertDetailsDto getDetailsByProjectId(@NotNull Integer id);

    /**
     * 根据采购方案id查询专家抽取id
     * @param projectId
     * @return
     */
    Integer getIdByProjectId(@NotNull Integer projectId);

    /**
     * 保存
     * @param saveDto
     * @return
     */
    Integer save(@NotNull CgDrawExpertSaveDto saveDto);

    /**
     * 更新
     * @param drawexpId
     * @return
     */
    Integer update(@NotNull Integer drawexpId);

    /**
     * 删除
     * @param ids 专家抽取ID集合
     */
    void delete(@NotNull Set<Integer> ids);

    /**
     * 保存专家抽取规则
     * @param saveDto
     * @return
     */
    Integer save(@NotNull List<CgDrawExpertRuleDto> saveDto, @NotNull Integer drawExpertId);

}
