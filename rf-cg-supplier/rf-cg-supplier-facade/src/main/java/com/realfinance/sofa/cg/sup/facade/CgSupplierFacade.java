package com.realfinance.sofa.cg.sup.facade;

import com.realfinance.sofa.cg.sup.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface CgSupplierFacade {

    /**
     * 查询
     * 该方法可以使用数据权限
     * @param queryCriteria
     * @param pageable
     * @return
     */
    Page<CgSupplierDto> list(CgSupplierQueryCriteria queryCriteria, @NotNull Pageable pageable);

    /**
     * 根据ID查询
     * 该方法可以使用数据权限
     * @param id 供应商ID
     * @return
     */
    CgSupplierDto getById(@NotNull Integer id);

    /**
     * 获取详情，包括子表数据
     * 该方法可以使用数据权限
     * @param id 供应商ID
     * @return
     */
    CgSupplierDetailsDto getDetailsById(@NotNull Integer id);

    /**
     * 根据用户名查询供应商详情
     * 该方法没有做数据权限控制
     * @param username 供应商账号用户名
     * @return
     */
    CgSupplierDetailsDto getDetailsByUsername(@NotNull String username);

    /**
     * 查询供应商参照
     * 该方法可以使用数据权限
     * @param queryCriteria
     * @param pageable
     * @return
     */
    Page<CgSupplierSmallDto> queryRefer(CgSupplierQueryCriteria queryCriteria, @NotNull Pageable pageable);

    /**
     * 添加标签关联
     * @param id 供应商ID
     * @param supplierLabelIds 供应商标签ID集合
     */
    void addSupplierLabels(@NotNull Integer id, @NotNull Set<Integer> supplierLabelIds);

    /**
     * 删除标签关联
     * @param id 供应商ID
     * @param supplierLabelIds 供应商标签ID集合
     */
    void removeSupplierLabels(@NotNull Integer id, @NotNull Set<Integer> supplierLabelIds);

    /**
     * 供应商是否都含有该标签
     * @param supplierIds 供应商ID集合
     * @param supplierLabelId 供应商标签
     * @return
     */
    boolean matchesSupplierLabel(@NotNull Collection<Integer> supplierIds, @NotNull Integer supplierLabelId);

    /**
     * 查询供应商是否有处罚记录更新供应商库子表-信用信息表
     */
    List<CgCreditDto> updateSupplierCredit(List<CgCreditDto> dtos);
}
