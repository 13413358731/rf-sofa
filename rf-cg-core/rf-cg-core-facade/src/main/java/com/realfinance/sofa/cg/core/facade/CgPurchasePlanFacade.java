package com.realfinance.sofa.cg.core.facade;

import com.realfinance.sofa.cg.core.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

public interface CgPurchasePlanFacade {



    /**
     * 年度计划列表
     *
     */
    Page<AnnualPlanDto> list(Pageable pageable);

    List<AnnualPlanDto> list();

    CgPurchasePlanDto getById(@NotNull Integer id);



    /**
     * 采购计划列表
     *
     */
    Page<CgPurchasePlanDto> list(Pageable pageable,Integer id,CgPurchasePlanQueryCriteria queryCriteria);

    Page<CgPurchasePlanDto> list(CgPurchasePlanQueryCriteria queryCriteria,Pageable pageable);

    /**
     * 保存主表
     *
     */
    Integer save(AnnualPlanSaveDto saveDto);

    /**
     * 保存采购计划
     *
     */
    Integer save(CgPurchasePlanSaveDto saveDto);


    /**
     * 导入采购计划
     * @param list
     * @param id
     * @return
     */
    Integer saveList(List<CgPurchasePlanImportDto> list, Integer id);

    List<CgPurchasePlanDto> getList(CgPurchasePlanQueryCriteria queryCriteria);

    /**
     * 删除
     *
     */
    void delete(Set<Integer> ids);


    /**
      * 详情
      *
      */
    AnnualPlanDto getDetailsById(@NotNull Integer id);




    /**
     * 更新处理状态
     *
     * @param id
     * @param status
     */
    void updateStatus(@NotNull Integer id, @NotNull String status);


}
