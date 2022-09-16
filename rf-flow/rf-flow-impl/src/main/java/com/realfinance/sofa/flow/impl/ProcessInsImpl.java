package com.realfinance.sofa.flow.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.realfinance.sofa.common.datascope.DataScopeUtils;
import com.realfinance.sofa.flow.facade.ProcessInsFacade;
import com.realfinance.sofa.flow.model.HistoricProcessInstanceQueryCriteria;
import com.realfinance.sofa.flow.model.ProcessInstanceDto;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.history.HistoricProcessInstanceQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.realfinance.sofa.flow.util.QueryCriteriaUtils.setQueryCriteria;
import static com.realfinance.sofa.flow.util.QueryCriteriaUtils.setSort;

@Service
@SofaService(interfaceType = ProcessInsFacade.class, uniqueId = "${service.rf-flow.id}",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "jvm")
        })
@Transactional(readOnly = true)
public class ProcessInsImpl implements ProcessInsFacade {

    public static final Logger log = LoggerFactory.getLogger(ProcessInsImpl.class);

    private final RuntimeService runtimeService;
    private final HistoryService historyService;;

    public ProcessInsImpl(RuntimeService runtimeService,
                          HistoryService historyService) {
        this.runtimeService = runtimeService;
        this.historyService = historyService;
    }

    @Override
    public Page<ProcessInstanceDto> listHistory(HistoricProcessInstanceQueryCriteria queryCriteria, Pageable pageable) {
        HistoricProcessInstanceQuery historicProcessInstanceQuery = setQueryCriteria(historyService.createHistoricProcessInstanceQuery(), queryCriteria);
        if (!DataScopeUtils.loadSkipValidateTenantId()) {
            historicProcessInstanceQuery.processInstanceTenantId(DataScopeUtils.loadTenantId());
        }
        long count = historicProcessInstanceQuery.count();
        if (count > 0) {
            setSort(historicProcessInstanceQuery,pageable.getSort());
            List<HistoricProcessInstance> historicProcessInstances = historicProcessInstanceQuery.listPage((int) pageable.getOffset(), pageable.getPageSize());
            List<ProcessInstanceDto> content = historicProcessInstances.stream().map(this::toProcessInstanceDto).collect(Collectors.toList());
            return new PageImpl<>(content, pageable, count);
        } else {
            return Page.empty(pageable);
        }
    }

    private ProcessInstanceDto toProcessInstanceDto(HistoricProcessInstance historicProcessInstance) {
        ProcessInstanceDto processInstanceDto = new ProcessInstanceDto();
        processInstanceDto.setId(historicProcessInstance.getId());
        processInstanceDto.setName(historicProcessInstance.getName());
        processInstanceDto.setBusinessKey(historicProcessInstance.getBusinessKey());
        if (historicProcessInstance.getStartUserId() != null) {
            processInstanceDto.setStartUserId(Integer.parseInt(historicProcessInstance.getStartUserId()));
        }
        processInstanceDto.setStartTime(historicProcessInstance.getStartTime());
        processInstanceDto.setEndTime(historicProcessInstance.getEndTime());
        processInstanceDto.setEndActivityId(historicProcessInstance.getEndActivityId());
        return processInstanceDto;
    }
}
