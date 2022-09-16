package com.realfinance.sofa.cg.core.facade;

import com.realfinance.sofa.cg.core.model.CgAttSaveDto;
import com.realfinance.sofa.cg.core.model.CgPurchaseResultNoticeDto;
import com.realfinance.sofa.cg.core.model.CgPurchaseResultNoticeQueryCriteria;
import com.realfinance.sofa.cg.core.model.CgPurchaseResultNoticeSaveDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface CgPurchaseResultNoticeFacade {

    /**
     * 列表
     * @param pageable
     * @param queryCriteria
     * @return
     */
    Page<CgPurchaseResultNoticeDto> list(Pageable pageable, CgPurchaseResultNoticeQueryCriteria queryCriteria);


    /**
     * 保存
     * @param saveDto
     * @return
     */
    Integer save(CgPurchaseResultNoticeSaveDto saveDto);

    /**
     * 详情
     * @param id
     * @return
     */
    CgPurchaseResultNoticeDto getDetailsById(@NotNull Integer id);



    /**
     * 更新审批状态
     * @param id 采购结果通知ID
     * @param status 流程状态
     */
    void updateStatus(@NotNull Integer id, @NotNull String status);

    /**
     * 更新电子签章附件
     * @param id 采购结果通知ID
     * @param dtoList 电子签章附件集合
     * @return
     */
    CgPurchaseResultNoticeDto updateAttachment(Integer id, List<CgAttSaveDto> dtoList);


    /**
     *
     * @param businessKey
     * @param projectId 采购方案id
     * @param projectexeId  采购方案执行id
     * @param projectNo 项目编号
     * @param name name
     * @param createdUserId
     * @param purResultSups
     * @return
     */
    //Integer release(String businessKey, Integer projectId, Integer projectexeId, String projectNo, String name, Integer createdUserId, String evalMethod, List<CgPurResultSupDto> purResultSups);


    /**
     * 根据方案id集合 查询 结果通知详情数据
     * @param projectIds
     * @return
     */
    List<CgPurchaseResultNoticeDto> listByProjectId(List<Integer> projectIds);

    void text(String note);

    Integer getIdByProjectId(@NotNull Integer projectId);

}
