package com.realfinance.sofa.flow.facade;

import com.realfinance.sofa.flow.model.BizDto;
import com.realfinance.sofa.flow.model.BizModelDto;
import com.realfinance.sofa.flow.model.BizModelSaveDto;
import com.realfinance.sofa.flow.model.ProcessDefinitionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * 流程设计
 */
public interface ProcessDefFacade {

    /**
     * 查询业务参照
     * @param filter 搜索条件
     * @param pageable
     * @return
     */
    Page<BizDto> queryBizRefer(String filter, @NotNull Pageable pageable);

    /**
     * 根据部门ID查询业务模型
     * @param departmentId 部门ID
     * @param pageable
     * @return
     */
    Page<BizModelDto> listBizModelByDepartmentId(@NotNull Integer departmentId, @NotNull Pageable pageable);

    /**
     *
     * @return
     */
    Page<ProcessDefinitionDto> listProcessDefinitionByBizModelId(@NotNull Integer bizModelId, @NotNull Pageable pageable);


    /**
     * 保存业务模型绑定
     * @param saveDto
     * @return
     */
    Integer saveBizModel(@NotNull BizModelSaveDto saveDto);

    /**
     * 激活流程定义
     * @param processDefinitionId 流程定义ID
     * @param activateProcessInstances 是否激活实例
     */
    void activate(@NotNull String processDefinitionId, @NotNull Boolean activateProcessInstances);

    /**
     * 挂起流程定义
     * @param processDefinitionId 流程定义ID
     * @param suspendProcessInstances 是否挂起实例
     */
    void suspend(@NotNull String processDefinitionId, @NotNull Boolean suspendProcessInstances);

    /**
     * 删除最后一次部署
     * @param bizModelId
     */
    void deleteLastDeploy(@NotNull Integer bizModelId);

    /**
     * 删除业务模型
     * @param bizModelIds 业务模型ID集合
     */
    void deleteBizModel(@NotNull Set<Integer> bizModelIds);

    /**
     * 发布流程
     * @param bizModelId 业务模型ID
     */
    void deploy(@NotNull Integer bizModelId);

    /**
     * 获取流程定义的图
     * @param processDefinitionId
     * @return
     */
    byte[] getModelResourceByProcessDefinitionId(@NotNull String processDefinitionId);

    /**
     * 删除流程定义
     * @param processDefinitionId 流程定义ID
     * @param cascade 联级删除
     */
    void deleteProcessDefinition(String processDefinitionId, Boolean cascade);
}
