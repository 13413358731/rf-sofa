package com.realfinance.sofa.flow.flowable.service;

import com.realfinance.sofa.flow.flowable.converter.CustomBpmnJsonConverter;
import org.flowable.ui.modeler.service.ModelImageService;

public class CustomModelImageService extends ModelImageService {
    {
        this.bpmnJsonConverter = new CustomBpmnJsonConverter();
    }
}
