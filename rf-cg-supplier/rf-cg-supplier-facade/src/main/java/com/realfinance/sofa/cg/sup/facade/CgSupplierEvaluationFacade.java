package com.realfinance.sofa.cg.sup.facade;

import com.realfinance.sofa.cg.sup.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

public interface CgSupplierEvaluationFacade {

    /**
     * 查询
     * 该方法可以使用数据权限
     * @param queryCriteria
     * @param pageable
     * @return
     */
    Page<CgSupplierEvaluationMainDto> list(CgSupplierEvaluationMainQueryCriteria queryCriteria, @NotNull Pageable pageable);

    /**
     * 获取详情，包括子表数据
     * 该方法可以使用数据权限
     * @param id 供应商ID
     * @return
     */
    CgSupplierEvaluationMainDetailsDto getDetailsById(@NotNull Integer id);

    /**
     * 发起评估
     * @param id
     * @return
     */
    Integer startevaluate(@NotNull List<CgSupplierEvaluationSheetSubDto> cgSupplierEvaluationSubDtos, @NotNull Integer id);

    /**
     * 保存
     * @param saveDto
     * @return
     */
    Integer save(@NotNull CgSupplierEvaluationMainDetailsSaveDto saveDto);

    /**
     * 删除
     * @param ids 黑名单ID集合
     */
    void delete(@NotNull Set<Integer> ids);

}
