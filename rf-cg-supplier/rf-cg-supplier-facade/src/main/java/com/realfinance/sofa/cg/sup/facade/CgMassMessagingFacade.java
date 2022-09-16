package com.realfinance.sofa.cg.sup.facade;

import com.realfinance.sofa.cg.sup.model.CgMassMessagingQueryCriteria;
import com.realfinance.sofa.cg.sup.model.MassMessagingDto;
import com.realfinance.sofa.cg.sup.model.MassMessagingSaveDto;
import com.realfinance.sofa.cg.sup.model.SupplierContactsDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import javax.validation.constraints.NotNull;
import java.util.Set;


public interface CgMassMessagingFacade {

    /** 列表
     * @param pageable
     * @param queryCriteria
     * @return
     */
    Page<MassMessagingDto> list(Pageable pageable, CgMassMessagingQueryCriteria queryCriteria);

    /**  保存
     * @param saveDto
     * @return
     */
    Integer save(MassMessagingSaveDto saveDto);

    /** 删除
     * @param ids
     */
    void delete(@NotNull Set<Integer> ids);

    /** 发送
     * @param id
     */
    void send(@NotNull Integer id);


    /**
     * 查询供应商联系人参照
     * @param pageable
     * @return
     */
    Page<SupplierContactsDto>  list(Pageable pageable);



    MassMessagingDto  findById(@NotNull Integer id);

}
