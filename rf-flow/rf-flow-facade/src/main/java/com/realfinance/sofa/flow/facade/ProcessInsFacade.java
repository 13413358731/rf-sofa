package com.realfinance.sofa.flow.facade;

import com.realfinance.sofa.flow.model.HistoricProcessInstanceQueryCriteria;
import com.realfinance.sofa.flow.model.ProcessInstanceDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 流程实例
 */
public interface ProcessInsFacade {

    /**
     * 查询历史流程实例
     * @param queryCriteria
     * @param pageable
     * @return
     */
    Page<ProcessInstanceDto> listHistory(HistoricProcessInstanceQueryCriteria queryCriteria, Pageable pageable);
}
