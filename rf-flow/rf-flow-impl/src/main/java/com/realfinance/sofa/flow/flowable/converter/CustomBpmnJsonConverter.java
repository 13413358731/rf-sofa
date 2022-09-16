package com.realfinance.sofa.flow.flowable.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.editor.language.json.converter.BpmnJsonConverter;
import org.flowable.editor.language.json.converter.BpmnJsonConverterContext;

public class CustomBpmnJsonConverter extends BpmnJsonConverter {
    static {
        CustomStartEventJsonConverter.fillTypes(convertersToBpmnMap, convertersToJsonMap);
        CustomUserTaskJsonConverter.customFillTypes(convertersToBpmnMap, convertersToJsonMap);
    }

    @Override
    public BpmnModel convertToBpmnModel(JsonNode modelNode, BpmnJsonConverterContext converterContext) {
        BpmnModel result = super.convertToBpmnModel(modelNode, converterContext);
        return customConvertToBpmnModel(result, modelNode);
    }

    @Override
    public ObjectNode convertToJson(BpmnModel model, BpmnJsonConverterContext converterContext) {
        ObjectNode result = super.convertToJson(model, converterContext);
        return customConvertToJson(model, result);
    }

    /**
     * 自定义数据对象表达式----json转xml
     *
     * @param bpmnModel
     * @param modelNode
     * @return
     */
    public BpmnModel customConvertToBpmnModel(BpmnModel bpmnModel, JsonNode modelNode) {
        // TODO: 2020/12/27
        return bpmnModel;
    }

    /**
     * 自定义数据对象表达式----xml转json
     *
     * @param bpmnModel
     * @param modelNode
     * @return
     */
    public ObjectNode customConvertToJson(BpmnModel bpmnModel, ObjectNode modelNode) {
        // TODO: 2020/12/27
        return modelNode;
    }
}
