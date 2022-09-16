package com.realfinance.sofa.cg.sup.facade;

import com.realfinance.sofa.cg.sup.model.CgSupplierExaminationDetailsDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierExaminationDetailsSaveDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierExaminationDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierExaminationQueryCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.util.Set;

public interface CgSupplierExaminationFacade {

    /**
     * 分页查询
     * 该方法可以使用数据权限
     * @param queryCriteria
     * @param pageable
     * @return
     */
    Page<CgSupplierExaminationDto> list(CgSupplierExaminationQueryCriteria queryCriteria, @NotNull Pageable pageable);

    /**
     * 根据ID查询
     * 该方法可以使用数据权限
     * @param id 供应商审核ID
     * @return
     */
    CgSupplierExaminationDto getById(@NotNull Integer id);

    /**
     * 获取详情，包括子表数据
     * 该方法可以使用数据权限
     * @param id 供应商审核ID
     * @return
     */
    CgSupplierExaminationDetailsDto getDetailsById(@NotNull Integer id);

    /**
     * 根据供应商新增
     * @param supplierId 供应商
     * @return
     */
    Integer createFromInternal(@NotNull Integer supplierId);

    /**
     * 保存
     * @param saveDto
     * @return
     */
    Integer save(@NotNull CgSupplierExaminationDetailsSaveDto saveDto);

    /**
     * 设置状态
     * @param id 供应商审核ID
     * @param status 处理状态
     * @param reason 原因
     */
    void updateStatus(@NotNull Integer id, @NotNull String status, String reason);

    /**
     * 删除
     * @param ids 供应商审核ID集合
     */
    void delete(@NotNull Set<Integer> ids);

    /**
     * 查询供应商信用信息
     * @param id 供应商审核id
     */
    CgSupplierExaminationDetailsDto updateSupplierExaminationCredit(Integer id);
}
