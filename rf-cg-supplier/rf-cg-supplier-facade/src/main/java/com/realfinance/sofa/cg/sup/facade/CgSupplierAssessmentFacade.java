package com.realfinance.sofa.cg.sup.facade;

import com.realfinance.sofa.cg.sup.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.util.Set;

public interface CgSupplierAssessmentFacade {

    /**
     * 查询
     * 该方法可以使用数据权限
     * @param queryCriteria
     * @param pageable
     * @return
     */
    Page<CgSupplierAssessmentDto> list(CgSupplierAssessmentQueryCriteria queryCriteria, @NotNull Pageable pageable);

    /**
     * 获取详情，包括子表数据
     * 该方法可以使用数据权限
     * @param id 供应商ID
     * @return
     */
    CgSupplierAssessmentDetailsDto getDetailsById(@NotNull Integer id);

    /**
     * 查询供应商考核指标参照
     * @param queryCriteria
     * @param pageable
     * @return
     */
    Page<CgSupplierAssessmentDto> queryRefer(CgSupplierAssessmentQueryCriteria queryCriteria, @NotNull Pageable pageable);

    /**
     * 查询供应商考核指标参照
     * @param queryCriteria
     * @param pageable
     * @return
     */
    Page<CgSupplierAssessmentIndicatorDto> queryIndicatorRefer(CgSupplierAssessmentIndicatorQueryCriteria queryCriteria, @NotNull Pageable pageable);

    /**
     * 保存
     * @param saveDto
     * @return
     */
    Integer save(@NotNull CgSupplierAssessmentDetailsSaveDto saveDto);

    /**
     * 删除
     * @param ids 黑名单ID集合
     */
    void delete(@NotNull Set<Integer> ids);

}
