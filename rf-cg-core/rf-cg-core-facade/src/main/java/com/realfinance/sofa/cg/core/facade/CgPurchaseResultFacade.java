package com.realfinance.sofa.cg.core.facade;

import com.realfinance.sofa.cg.core.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 采购方案执行
 */
public interface CgPurchaseResultFacade {

    Page<CgPurchaseResultDto> list(CgPurchaseResultQueryCriteria queryCriteria, @NotNull Pageable pageable);

    //CgProjectExecutionDto getById(@NotNull Integer id);

    CgPurchaseResultDetailsDto getById(@NotNull Integer id);

    CgPurchaseResultDetailsDto getDetailsById(@NotNull Integer id);

    Integer generateResult(Integer projectExeId,CgProjectDetailsDto projectDetails, List<CgMeetingConfereeDto> conferees, List<CgProjectExecutionSupDto> projectExecutionSups);

    Integer save(CgPurchaseResultDetailsDto saveDto);

    /**
     * @param id 专家Id
     * @param status 处理状态
     * @param reqUserName 申请人真实名称
     * @param list 供应商名称集合
     */
    void updateStatus(@NotNull Integer id, @NotNull String status,String reqUserName,List<String> list);

    /**
     * 查询且保存供应商关联关系
     * @param id 方案id
     * @param list 多个供应商信息
     */
    List<CgPurchaseResultRelationshipDto> relationship(@NotNull Integer id, @NotNull List<CgPurchaseResultRelationshipDto> list);

    /**
     * 更新子表关联状态字段
     * @param dtos
     */
    List<CgPurResultSupDto> updateProjectSupRelatedStatus(List<CgPurResultSupDto> dtos);

    /**
     * 更新子表 信用状态/信用查询时间 字段
     */
    List<CgPurResultSupDto> updateProjectSupCreditStatus(List<CgPurResultSupDto> dtos);




//    Integer save(CgPurchaseResultDetailsDto saveDto);


//    /**
//     * 查询供应商看板所有供应商
//     * @param id
//     * @return
//     */
//    List<CgProjectExecutionSupDto> getSuppliersById(Integer id);
//
//    Integer save(CgProjectExecutionDetailsSaveDto saveDto);
//
//    /**
//     * 查询方案价格部分的评分细则
//     * @param id
//     * @return
//     */
//    List<String> listPriceEvalRuleProductName(Integer id);
//
//    /**
//     * 开始方案执行环节
//     * @param projectExecutionStepId
//     */
//    void startStep(Integer projectExecutionStepId);
//
//    /**
//     * 结束方案执行环节
//     * @param projectExecutionStepId
//     */
//    void endStep(Integer projectExecutionStepId);
//
//    /**
//     * 废标
//     * @param id 方案执行ID
//     * @param reason 原因
//     */
//    void invalidBid(Integer id, String reason);
//
//    /**
//     * 保存供应商看板信息
//     * @param id
//     * @param dto
//     * @return
//     */
//    Integer saveSupplier(Integer id, CgProjectExecutionSupDto dto);
//
//    /**
//     * 作废供应商
//     * @param id
//     * @param projectExecutionSupId
//     */
//    void invalidSupplier(Integer id, Integer projectExecutionSupId);

}
