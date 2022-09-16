package com.realfinance.sofa.cg.core.facade;

import com.realfinance.sofa.cg.core.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

public interface CgExpertFacade {

    /**
     * 查询
     * 该方法可以使用数据权限
     * @param queryCriteria
     * @param pageable
     * @return
     */
    Page<CgExpertDto> list(CgExpertQueryCriteria queryCriteria, @NotNull Pageable pageable);


    /**
     * 根据ID查询
     * 该方法可以使用数据权限
     * @param id 供应商ID
     * @return
     */
    CgExpertDto getById(@NotNull Integer id);

    /**
     * 获取详情，包括子表数据
     * 该方法可以使用数据权限
     * @param id 供应商ID
     * @return
     */
    CgExpertDetailsDto getDetailsById(@NotNull Integer id);

    /**
     * 根据用户名查询供应商详情
     * 该方法没有做数据权限控制
     * @param username 供应商账号用户名
     * @return
     */
    CgExpertDetailsDto getDetailsByUsername(@NotNull String username);

    /**
     * 保存
     * @param saveDto
     * @return
     */
    Integer save(@NotNull CgExpertSaveDto saveDto);

    /**
     * 更新状态
     * @param id 专家Id
     * @param status 处理状态
     */
    void updateStatus(@NotNull Integer id, @NotNull String status);

    /**
     * 删除
     * @param ids 黑名单ID集合
     */
    void delete(@NotNull Set<Integer> ids);

    /**
     * 查询供应商参照
     * 该方法可以使用数据权限
     * @param queryCriteria
     * @param pageable
     * @return
     */
    Page<CgExpertDto> queryRefer(CgExpertQueryCriteria queryCriteria, @NotNull Pageable pageable);

    /**
     * 添加标签关联
     * @param id 供应商ID
     * @param ExpertLabelIds 供应商标签ID集合
     */
    void addExpertLabels(@NotNull Integer id, @NotNull Set<Integer> ExpertLabelIds);

    /**
     * 删除标签关联
     * @param id 供应商ID
     * @param ExpertLabelIds 供应商标签ID集合
     */
    void removeExpertLabels(@NotNull Integer id, @NotNull Set<Integer> ExpertLabelIds);
}
