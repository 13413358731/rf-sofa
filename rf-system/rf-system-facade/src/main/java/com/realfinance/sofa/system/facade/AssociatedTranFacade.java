package com.realfinance.sofa.system.facade;

import com.realfinance.sofa.system.model.AssociatedTranDto;

import java.util.List;

/**
 * 关联交易
 */
public interface AssociatedTranFacade {
    //数据同步
    void syncAssociatedTran();

    /**
     * 关联交易查询
     * @return
     */
    List<AssociatedTranDto> selectIds(List<AssociatedTranDto> dtos);
}
