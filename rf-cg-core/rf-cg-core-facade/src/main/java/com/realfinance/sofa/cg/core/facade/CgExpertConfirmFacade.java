package com.realfinance.sofa.cg.core.facade;

import com.realfinance.sofa.cg.core.model.CgExpertConfirmDto;
import com.realfinance.sofa.cg.core.model.CgExpertConfirmQueryCriteria;
import com.realfinance.sofa.cg.core.model.CgExpertConfirmSaveDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

public interface CgExpertConfirmFacade {

    /**
     * 查询
     * 该方法可以使用数据权限
     * @param queryCriteria
     * @param pageable
     * @return
     */
    Page<CgExpertConfirmDto> list(CgExpertConfirmQueryCriteria queryCriteria, @NotNull Pageable pageable);

    /**
     * 获取详情，包括子表数据
     * 该方法可以使用数据权限
     * @param id 供应商ID
     * @return
     */
    CgExpertConfirmDto getDetailsById(@NotNull Integer id);


    /**
     * 通知抽取出来的专家
     * @param id 专家抽取主表ID
     * @return map key为确认表id value为被通知人
     */
    List<CgExpertConfirmDto> notifyExperts(@NotNull Integer id, Set<Integer> ids);

    /**
     * 通知抽取出来的专家
     * @param id 专家抽取主表ID
     * @return
     */
//    void notifyExperts2(@NotNull Integer id);

    /**
     * 专家确认出席
     * @param saveDto 专家确认
     * @return
     */
    Integer confirm(@NotNull CgExpertConfirmSaveDto saveDto);
}
