package com.realfinance.sofa.flow.flowable.converter;

import org.flowable.bpmn.model.BaseElement;
import org.flowable.bpmn.model.StartEvent;
import org.flowable.editor.language.json.converter.BaseBpmnJsonConverter;
import org.flowable.editor.language.json.converter.StartEventJsonConverter;

import java.util.Map;

public class CustomStartEventJsonConverter extends StartEventJsonConverter {

    public static void fillTypes(Map<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap, Map<Class<? extends BaseElement>, Class<? extends BaseBpmnJsonConverter>> convertersToJsonMap) {
        fillJsonTypes(convertersToBpmnMap);
        fillBpmnTypes(convertersToJsonMap);
    }

    public static void fillJsonTypes(Map<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap) {
        convertersToBpmnMap.put(STENCIL_EVENT_START_NONE, CustomStartEventJsonConverter.class);
        convertersToBpmnMap.put(STENCIL_EVENT_START_TIMER, CustomStartEventJsonConverter.class);
        convertersToBpmnMap.put(STENCIL_EVENT_START_CONDITIONAL, CustomStartEventJsonConverter.class);
        convertersToBpmnMap.put(STENCIL_EVENT_START_ERROR, CustomStartEventJsonConverter.class);
        convertersToBpmnMap.put(STENCIL_EVENT_START_ESCALATION, CustomStartEventJsonConverter.class);
        convertersToBpmnMap.put(STENCIL_EVENT_START_MESSAGE, CustomStartEventJsonConverter.class);
        convertersToBpmnMap.put(STENCIL_EVENT_START_EVENT_REGISTRY, CustomStartEventJsonConverter.class);
        convertersToBpmnMap.put(STENCIL_EVENT_START_SIGNAL, CustomStartEventJsonConverter.class);
    }

    public static void fillBpmnTypes(Map<Class<? extends BaseElement>, Class<? extends BaseBpmnJsonConverter>> convertersToJsonMap) {
        convertersToJsonMap.put(StartEvent.class, CustomStartEventJsonConverter.class);
    }
}
