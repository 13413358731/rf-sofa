package com.realfinance.sofa.cg.core.facade;

import com.realfinance.sofa.cg.core.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface CgProjectFacade {

    Page<CgProjectDto> list(CgProjectQueryCriteria queryCriteria, @NotNull Pageable pageable);

    CgProjectDto getById(@NotNull Integer id);

    CgProjectDetailsDto getDetailsById(@NotNull Integer id);

    /**
     * 列出修改历史
     * index为0的是当前采购申请的数据，后面的是历史数据
     * @param id
     * @param all
     * @return
     */
    List<CgProjectDetailsDto> listHistoricDetailsById(@NotNull Integer id,Boolean all);

    Integer save(@NotNull CgProjectDetailsSaveDto saveDto);

    /**
     * 更新审批状态
     * @param id 采购方案ID
     * @param status 流程状态
     */
    void updateStatus(@NotNull Integer id, @NotNull String status);

    /**
     * 退回到采购需求申请
     * @param id 采购方案ID
     * @param reason 退回原因
     */
    void returnRequirement(@NotNull Integer id, String reason);

    /**
     * 根据方案ID查询采购需求申请
     * @param id 采购方案ID
     * @return
     */
    CgRequirementDetailsDto getRequirementDetailsById(@NotNull Integer id);

    /**
     * 价格部分计算公式
     */
    List<CgPriceEvalFormulaDto> listPriceEvalFormula();

    Page<CgProjectSmallDto> queryRefer(CgProjectQueryCriteria queryCriteria, @NotNull Pageable pageable);

    /**
     * 查询且保存供应商关联关系
     * @param id 方案id
     * @param list 多个供应商信息
     */
    List<CgProjectRelationshipDto> relationship(@NotNull Integer id, @NotNull List<CgProjectRelationshipDto> list);

    /**
     * 更新子表关联状态字段
     * @param dtos
     */
    List<CgProjectSupDto> updateProjectSupRelatedStatus(List<CgProjectSupDto> dtos);

    /**
     * 更新子表 信用状态/信用查询时间 字段
     */
    List<CgProjectSupDto> updateProjectSupCreditStatus(List<CgProjectSupDto> dtos);


}
