package com.realfinance.sofa.flow.flowable.service;

import com.realfinance.sofa.flow.flowable.converter.CustomBpmnJsonConverter;
import org.flowable.ui.modeler.service.FlowableModelQueryService;

public class CustomFlowableModelQueryService extends FlowableModelQueryService {
    {
        this.bpmnJsonConverter = new CustomBpmnJsonConverter();
    }
}
