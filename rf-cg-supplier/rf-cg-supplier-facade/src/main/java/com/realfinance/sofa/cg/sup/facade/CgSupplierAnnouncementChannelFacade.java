package com.realfinance.sofa.cg.sup.facade;


import com.realfinance.sofa.cg.sup.model.ChannelQueryCriteriaRequest;
import com.realfinance.sofa.cg.sup.model.SupplierAnnouncementChannelDto;
import com.realfinance.sofa.cg.sup.model.SupplierAnnouncementChannelSaveDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.util.Set;

public interface CgSupplierAnnouncementChannelFacade {


    /**
     * 频道列表
     * @param  pageable
     */
    Page<SupplierAnnouncementChannelDto> list(Pageable pageable, ChannelQueryCriteriaRequest request);

    /**
     * 登录：通用公告频道
     * 未登录：所有公告频道
     * @param pageable
     * @param flag
     */
    Page<SupplierAnnouncementChannelDto> listquery(Pageable pageable,Boolean flag);


    /**
     * 查询
     * @param id
     */
    SupplierAnnouncementChannelDto getOne(@NotNull Integer id);


    /**
     * 删除
     * @param ids
     */
    void  deleate(@NotNull Set<Integer> ids);


    /**
     * 保存更新
     * @param ChannelSaveDto
     */
    Integer save(SupplierAnnouncementChannelSaveDto ChannelSaveDto);




}
