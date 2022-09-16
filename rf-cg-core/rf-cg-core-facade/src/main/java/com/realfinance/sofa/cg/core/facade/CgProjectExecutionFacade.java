package com.realfinance.sofa.cg.core.facade;

import com.realfinance.sofa.cg.core.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * 采购方案执行
 */
public interface CgProjectExecutionFacade {

    Page<CgProjectExecutionDto> list(CgProjectExecutionQueryCriteria queryCriteria, @NotNull Pageable pageable);

    CgProjectExecutionDto getById(@NotNull Integer id);

    CgProjectExecutionDetailsDto getDetailsById(@NotNull Integer id);

    /**
     * 查询供应商看板所有供应商
     * @param id
     * @return
     */
    List<CgProjectExecutionSupDto> getSuppliersById(Integer id);

    /**
     * 查询采购执行步骤状态
     * @param id
     * @return
     */
    List<CgProjectExecutionStepDto> getStepsById(Integer id);

    /**
     * 查询供应商看板的有效供应商
     * @param id
     * @return
     */
    List<CgProjectExecutionSupDto> getValidSuppliers(Integer id);

    Integer save(CgProjectExecutionDetailsSaveDto saveDto);

    /**
     * 查询方案价格部分的评分细则
     * @param id
     * @return
     */
    List<String> listPriceEvalRuleProductName(Integer id);

    /**
     * 开始方案执行环节
     * @param projectExecutionStepId
     */
    void startStep(Integer projectExecutionStepId);

    /**
     * 结束方案执行环节
     * @param projectExecutionStepId
     */
    void endStep(Integer projectExecutionStepId);

    /**
     * 废标
     * @param id 方案执行ID
     * @param reason 原因
     */
    void invalidBid(Integer id, String reason);

    /**
     * 保存供应商看板信息
     * @param id
     * @param dto
     * @return
     */
    Integer saveSupplier(Integer id, CgProjectExecutionSupDto dto);

    /**
     * 作废供应商
     * @param id
     * @param projectExecutionSupId
     */
    void invalidSupplier(Integer id, Integer projectExecutionSupId);

    /**
     * 根据采购方案查询采购方案执行主键和采购方案创建人id以及采购项目名称
     * @param projectId 采购方案主键
     * @return
     */
    Map<String,String> listProjectExecutionId(Integer projectId);

    Integer saveEval(CgProjectEvalDto saveDto);

}
