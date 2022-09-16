package com.realfinance.sofa.cg.core.facade;

import com.realfinance.sofa.cg.core.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

public interface CgContractManageFacade {

    Page<CgContractManageDto> list(CgContractManageQueryCriteria queryCriteria, @NotNull Pageable pageable);

    CgContractManageDetailsDto getDetailsById(@NotNull Integer id);

    Integer save(@NotNull CgContractManageSaveDto saveDto);

    void delete(@NotNull Set<Integer> ids);

    /**
     * 修改归档状态
     * @param id
     */
    void updateFileStatus(Integer id);

    /**
     * 获取需要发合同预警的数据
     * @param date 参数
     * @return
     */
    List<CgContractManageDto> listToTask(Long date);

    /**
     * 修改ExpireStatus 字段状态
     * @param ids
     * @param expireStatus
     */
    void updateExpireStatus(List<Integer> ids,Integer expireStatus);


    /**
     * 获取需要发供应商评估提醒的数据
     * @param date 参数
     * @return
     */
    List<CgContractManageDto> listToSupplierTask(Long date);
}
