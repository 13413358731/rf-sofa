package com.realfinance.sofa.cg.sup.facade;

import com.realfinance.sofa.cg.sup.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

public interface CgSupplierSolicitationFacade {

    /**
     * 保存报名记录
     *
     * @param enrollSave
     */
    void saveEnroll(EnrollSaveDto enrollSave);


    /**
     * 保存报名信息
     *
     * @param information
     * @param solicitationId
     */
    void saveinformation(registrationInformation information, Integer solicitationId, Integer supplierId);


    /**
     * 获取已报名的供应商
     *
     * @param SolicitationId
     */
    Page<SolicitationEnrollDto> findById(Integer SolicitationId, Pageable pageable);


    /**
     * 发布意向征集至门户
     *
     * @param id
     */
    void release(@NotNull Integer id,@NotNull String releaseStatus);

    void stops(List<Integer> ids);
    /**
     * 检查是否已报名
     *
     * @param solicitationId
     * @param supplierId
     */
    boolean isEnlist(Integer solicitationId, Integer supplierId);


    /**
     * 采购系统列表
     *
     * @param pageable
     */
    Page<CgSupplierSolicitationDto> list(Pageable pageable,CgSupplierSolicitationQueryCriteria queryCriteria);


    /**
     * 门户列表
     * @param pageable
     * @return
     */
    Page<CgSupplierSolicitationDto>  listrefer(Pageable pageable);



    /**
     * 保存更新
     *
     * @param saveDto
     */
    Integer save(CgSupplierSolicitationSaveDto saveDto);


    /**
     * 删除
     *
     * @param ids
     */
    void delete(@NotNull Set<Integer> ids);

    /**
     * 供应商意向征集详情
     *
     * @param id
     */
    CgSupplierSolicitationDetailsDto getdetail(@NotNull Integer id);


    /**
     * 更新处理状态
     *
     * @param id
     * @param status
     */
    void updateStatus(@NotNull Integer id, @NotNull String status);


    /**
     * 查询供应商联系人参照
     * @param pageable
     * @return
     */
    Page<SupplierContactsDto>  listrefer(Pageable pageable,@NotNull Integer id);

}
