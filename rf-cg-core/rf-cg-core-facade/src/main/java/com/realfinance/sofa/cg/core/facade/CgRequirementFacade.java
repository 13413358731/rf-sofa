package com.realfinance.sofa.cg.core.facade;

import com.realfinance.sofa.cg.core.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

/**
 * 采购需求
 */
public interface CgRequirementFacade {

    /**
     * 查询
     *
     * @param queryCriteria
     * @param pageable
     * @return
     */
    Page<CgRequirementDto> list(CgRequirementQueryCriteria queryCriteria, @NotNull Pageable pageable);

    CgRequirementDto getById(@NotNull Integer id);

    CgRequirementDetailsDto getDetailsById(@NotNull Integer id);

    /**
     * 列出修改历史
     * index为0的是当前采购申请的数据，后面的是历史数据
     * @param id 采购需求申请ID
     * @param all 如果true查询所有历史，如果false查询最新一次历史
     * @return
     */
    List<CgRequirementDetailsDto> listHistoricDetailsById(@NotNull Integer id, Boolean all);

    Integer save(CgRequirementDetailsSaveDto saveDto);

    /**
     * 分配采购经办人
     * @param id 采购需求ID
     * @param userId 分配经办人用户ID
     * @param comment 描述
     */
    void assignOperator(@NotNull Integer id, @NotNull Integer userId, String comment);

    /**
     * 更新受理状态
     * @param id 采购需求ID
     * @param acceptStatus 受理状态
     * @param reason 原因
     */
    void updateAcceptStatus(@NotNull Integer id, @NotNull String acceptStatus, String reason);

    /**
     * 更新审批状态
     * @param id 采购需求ID
     * @param status 流程状态
     */
    void updateStatus(@NotNull Integer id, @NotNull String status);

    void delete(Set<Integer> ids);

    /**
     * 查询且保存供应商关联关系
     * @param id 需求id
     * @param list 多个供应商信息
     */
    List<CgRequirementRelationshipDto> relationship(@NotNull Integer id, @NotNull List<CgRequirementRelationshipDto> list);



    /**
     * 更新子表关联状态字段
     * @param dtos
     */
    List<CgRequirementSupDto> updateRequirementSupRelatedStatus(List<CgRequirementSupDto> dtos);

    /**
     * 更新子表 信用状态/信用查询时间 字段
     */
    List<CgRequirementSupDto> updateRequirementSupCreditStatus(List<CgRequirementSupDto> dtos);

    /**
     * 根据立项审批号查询所有的立项审批数据
     * @param approvalNoList
     * @return
     */
    List<CgRequirementOaDatumDto> findOaDatumList(List<String> approvalNoList);

    /**
     * 导入货物类采购清单
     * @param list
     * @param id 采购申请id
     * @return
     */
    Integer saveList(List<CgRequirementItemImportDto> list, Integer id);

    /**
     * 清空已占用和可使用金额数据
     * @param id 采购申请id
     * @return
     */
    Integer saveAmount(Integer id);
}
