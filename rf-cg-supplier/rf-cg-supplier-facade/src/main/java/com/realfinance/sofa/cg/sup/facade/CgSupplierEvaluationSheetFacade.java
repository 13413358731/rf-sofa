package com.realfinance.sofa.cg.sup.facade;

import com.realfinance.sofa.cg.sup.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.util.Set;

public interface CgSupplierEvaluationSheetFacade {

    /**
     * 查询
     * 该方法可以使用数据权限
     * @param queryCriteria
     * @param pageable
     * @return
     */
    Page<CgSupplierEvaluationSheetMainDto> list(CgSupplierEvaluationSheetMainQueryCriteria queryCriteria, @NotNull Pageable pageable);

    /**
     * 获取详情，包括子表数据
     * 该方法可以使用数据权限
     * @param id 供应商ID
     * @return
     */
    CgSupplierEvaluationSheetDetailsDto getDetailsById(@NotNull Integer id);

    /**
     * 获取详情，包括子表数据
     * 该方法可以使用数据权限
     * @param id 供应商ID
     * @return
     */
//    CgSupplierEvaluationSheetMainDto getById(@NotNull Integer id);
    CgSupplierEvaluationSheetDetailsSaveDto getById(@NotNull Integer id);

    /**
     * 保存
     * @param saveDto
     * @return
     */
    Integer save(@NotNull CgSupplierEvaluationSheetDetailsSaveDto saveDto);

    /**
     * 删除
     * @param ids 黑名单ID集合
     */
    void delete(@NotNull Set<Integer> ids);

    /**
     * 更新状态
     * @param id 供应商评估Id
     * @param status 处理状态
     */
    void updateStatus(@NotNull Integer id, @NotNull String status);

    /**
     *
     * @param queryCriteria
     * @param pageable
     * @return
     */
    Page<CgSupplierEvaluationSheetMainDto> queryRefer(CgSupplierEvaluationSheetMainQueryCriteria queryCriteria, @NotNull Pageable pageable);


}
