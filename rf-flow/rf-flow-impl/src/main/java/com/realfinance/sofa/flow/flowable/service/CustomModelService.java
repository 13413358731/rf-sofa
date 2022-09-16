package com.realfinance.sofa.flow.flowable.service;

import com.realfinance.sofa.flow.flowable.converter.CustomBpmnJsonConverter;
import org.flowable.ui.modeler.service.ModelServiceImpl;

public class CustomModelService extends ModelServiceImpl {

    {
        this.bpmnJsonConverter = new CustomBpmnJsonConverter();
    }
}
