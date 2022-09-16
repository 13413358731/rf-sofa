package com.realfinance.sofa.cg.sup.facade;

import com.realfinance.sofa.cg.sup.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

public interface CgSupplierAnnouncementFacade {

    /**
     * 公告列表
     *
     * @param pageable
     */
    Page<SupplierAnnouncementDto> list(Pageable pageable, AnnouncementQueryCriteria queryCriteria);

    /**
     * 删除
     *
     * @param ids
     */
    void deleate(@NotNull Set<Integer> ids);

    /**
     * 保存更新
     *
     * @param SaveDto
     */
    Integer save(SupplierAnnouncementSaveDto SaveDto);


    /**
     * 发布公告
     *
     * @param id
     */
    void release(@NotNull Integer id);


    /**
     * 停用公告
     *
     * @param id
     */
    void stop(@NotNull Integer id, String name);

    /**
     * 恢复公告
     *
     * @param id
     */
    void recovery(@NotNull Integer id);

    /**
     * 公告详情
     *
     * @param id
     */
    SupplierAnnouncementDetailsDto getdetails(@NotNull Integer id);


    SupplierAnnouncementDto getdetail(@NotNull Integer id);


    /**
     * 条件查询
     *
     * @param queryCriteria
     */
    Page<SupplierAnnouncementDto> querylist(CgSupplierAnnouncementQueryCriteria queryCriteria, Pageable pageable);


    /**
     * 更新处理状态
     *
     * @param id
     * @param status
     */
    void updateStatus(@NotNull Integer id, @NotNull String status);


}
