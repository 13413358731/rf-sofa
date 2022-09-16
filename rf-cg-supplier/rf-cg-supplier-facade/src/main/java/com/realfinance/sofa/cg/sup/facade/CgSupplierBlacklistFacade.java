package com.realfinance.sofa.cg.sup.facade;

import com.realfinance.sofa.cg.sup.model.CgSupplierBlacklistDetailsDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierBlacklistDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierBlacklistQueryCriteria;
import com.realfinance.sofa.cg.sup.model.CgSupplierBlacklistSaveDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.util.Set;

public interface CgSupplierBlacklistFacade {
    /**
     * 查询
     * 该方法可以使用数据权限
     * @param queryCriteria
     * @param pageable
     * @return
     */
    Page<CgSupplierBlacklistDto> list(CgSupplierBlacklistQueryCriteria queryCriteria, @NotNull Pageable pageable);

    /**
     * 根据ID查询
     * 该方法可以使用数据权限
     * @param id 供应商审核ID
     * @return
     */
    CgSupplierBlacklistDto getById(@NotNull Integer id);

    /**
     * 获取详情，包括子表数据
     * 该方法可以使用数据权限
     * @param id 供应商审核ID
     * @return
     */
    CgSupplierBlacklistDetailsDto getDetailsById(@NotNull Integer id);

    /**
     * 保存
     * @param saveDto
     * @return
     */
    Integer save(@NotNull CgSupplierBlacklistSaveDto saveDto);

    /**
     * 更新状态
     * @param id 黑名单ID
     * @param status 处理状态
     */
    void updateStatus(@NotNull Integer id, @NotNull String status);

    /**
     * 删除
     * @param ids 黑名单ID集合
     */
    void delete(@NotNull Set<Integer> ids);

    /**
     * 失效
     * @param id 黑名单ID
     */
    void invalid(@NotNull Integer id);
}
