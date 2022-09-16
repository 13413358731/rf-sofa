package com.realfinance.sofa.flow.flowable.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.realfinance.sofa.flow.flowable.FlowConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.flowable.bpmn.model.*;
import org.flowable.editor.language.json.converter.BaseBpmnJsonConverter;
import org.flowable.editor.language.json.converter.BpmnJsonConverterContext;
import org.flowable.editor.language.json.converter.BpmnJsonConverterUtil;
import org.flowable.editor.language.json.converter.UserTaskJsonConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomUserTaskJsonConverter extends UserTaskJsonConverter {

    static void customFillTypes(Map<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap, Map<Class<? extends BaseElement>, Class<? extends BaseBpmnJsonConverter>> convertersToJsonMap) {
        fillJsonTypes(convertersToBpmnMap);
        fillBpmnTypes(convertersToJsonMap);
    }

    public static void fillJsonTypes(Map<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap) {
        convertersToBpmnMap.put(STENCIL_TASK_USER, CustomUserTaskJsonConverter.class);
    }

    public static void fillBpmnTypes(Map<Class<? extends BaseElement>, Class<? extends BaseBpmnJsonConverter>> convertersToJsonMap) {
        convertersToJsonMap.put(UserTask.class, CustomUserTaskJsonConverter.class);
    }

    @Override
    protected FlowElement convertJsonToElement(JsonNode elementNode, JsonNode modelNode, Map<String, JsonNode> shapeMap, BpmnJsonConverterContext converterContext) {
        UserTask result = (UserTask) super.convertJsonToElement(elementNode, modelNode, shapeMap, converterContext);
        List<CustomProperty> customProperties = new ArrayList<>();

        CustomProperty businessDataEditable = new CustomProperty();
        businessDataEditable.setId(FlowConstants.BUSINESS_DATA_EDITABLE_CUSTOM_PROPERTY_ID);
        businessDataEditable.setName(FlowConstants.BUSINESS_DATA_EDITABLE_CUSTOM_PROPERTY_ID);
        businessDataEditable.setSimpleValue(getPropertyValueAsString(FlowConstants.BUSINESS_DATA_EDITABLE_CUSTOM_PROPERTY_ID, elementNode));

        customProperties.add(businessDataEditable);
        result.setCustomProperties(customProperties);
        return result;
    }

    @Override
    protected void convertJsonToFormProperties(JsonNode objectNode, BaseElement element) {
        JsonNode formPropertiesNode = getProperty(PROPERTY_FORM_PROPERTIES, objectNode);
        if (formPropertiesNode != null) {
            formPropertiesNode = BpmnJsonConverterUtil.validateIfNodeIsTextual(formPropertiesNode);
            JsonNode propertiesArray = formPropertiesNode.get("formProperties");
            if (propertiesArray != null) {
                for (JsonNode formNode : propertiesArray) {
                    JsonNode formIdNode = formNode.get(PROPERTY_FORM_ID);
                    if (formIdNode != null && StringUtils.isNotEmpty(formIdNode.asText())) {

                        FormProperty formProperty = new FormProperty();
                        formProperty.setId(formIdNode.asText());
                        formProperty.setName(getValueAsString(PROPERTY_FORM_NAME, formNode));
                        formProperty.setType(getValueAsString(PROPERTY_FORM_TYPE, formNode));
                        formProperty.setExpression(getValueAsString(PROPERTY_FORM_EXPRESSION, formNode));
                        formProperty.setVariable(getValueAsString(PROPERTY_FORM_VARIABLE, formNode));
                        formProperty.setDefaultExpression(getValueAsString(PROPERTY_FORM_DEFAULT, formNode));
                        if ("date".equalsIgnoreCase(formProperty.getType())) {
                            formProperty.setDatePattern(getValueAsString(PROPERTY_FORM_DATE_PATTERN, formNode));

                        } else if ("enum".equalsIgnoreCase(formProperty.getType())) {
                            JsonNode enumValuesNode = formNode.get(PROPERTY_FORM_ENUM_VALUES);
                            if (enumValuesNode != null) {
                                List<FormValue> formValueList = new ArrayList<>();
                                for (JsonNode enumNode : enumValuesNode) {
                                    if (enumNode.get(PROPERTY_FORM_ENUM_VALUES_ID) != null && !enumNode.get(PROPERTY_FORM_ENUM_VALUES_ID).isNull() && enumNode.get(PROPERTY_FORM_ENUM_VALUES_NAME) != null
                                            && !enumNode.get(PROPERTY_FORM_ENUM_VALUES_NAME).isNull()) {

                                        FormValue formValue = new FormValue();
                                        formValue.setId(enumNode.get(PROPERTY_FORM_ENUM_VALUES_ID).asText());
                                        formValue.setName(enumNode.get(PROPERTY_FORM_ENUM_VALUES_NAME).asText());
                                        formValueList.add(formValue);

                                    } else if (enumNode.get("value") != null && !enumNode.get("value").isNull()) {
                                        FormValue formValue = new FormValue();
                                        formValue.setId(enumNode.get("value").asText());
                                        formValue.setName(enumNode.get("value").asText());
                                        formValueList.add(formValue);
                                    }
                                }
                                formProperty.setFormValues(formValueList);
                            }
                        } else if ("users".equalsIgnoreCase(formProperty.getType())) { // 自定义类型
                            // TODO: 2020/12/27 自定义属性暂时先写进formValues，不然的话需要重写 org.flowable.bpmn.converter.BaseBpmnXMLConverter.writeFormProperties 才能将自定义属性取出来
                            JsonNode userScope = formNode.get("userScope");
                            List<FormValue> formValues = new ArrayList<>();
                            if (userScope != null && !userScope.isNull()) {
                                String userScopeStr = userScope.asText();
                                if (StringUtils.isNotBlank(userScopeStr)) {
                                    FormValue formValue = new FormValue();
                                    formValue.setId("userScope");
                                    formValue.setName(userScopeStr);
                                    formValues.add(formValue);
                                }
                            }
                            JsonNode groupScope = formNode.get("groupScope");
                            if (groupScope != null && !groupScope.isNull()) {
                                String groupScopeStr = groupScope.asText();
                                if (StringUtils.isNotBlank(groupScopeStr)) {
                                    FormValue formValue = new FormValue();
                                    formValue.setId("groupScope");
                                    formValue.setName(groupScopeStr);
                                    formValues.add(formValue);
                                }
                            }
                            JsonNode maxSize = formNode.get("maxSize");
                            if (maxSize != null && !maxSize.isNull()) {
                                String maxSizeStr = maxSize.asText();
                                if (NumberUtils.isDigits(maxSizeStr)) {
                                    FormValue formValue = new FormValue();
                                    formValue.setId("maxSize");
                                    formValue.setName(maxSizeStr);
                                    formValues.add(formValue);
                                }
                            }
                            JsonNode minSize = formNode.get("minSize");
                            if (minSize != null && !minSize.isNull()) {
                                String minSizeStr = minSize.asText();
                                if (NumberUtils.isDigits(minSizeStr)) {
                                    FormValue formValue = new FormValue();
                                    formValue.setId("minSize");
                                    formValue.setName(minSizeStr);
                                    formValues.add(formValue);
                                }
                            }
                            if (!formValues.isEmpty()) {
                                formProperty.setFormValues(formValues);
                            }
                        }

                        formProperty.setRequired(getValueAsBoolean(PROPERTY_FORM_REQUIRED, formNode));
                        formProperty.setReadable(getValueAsBoolean(PROPERTY_FORM_READABLE, formNode));
                        formProperty.setWriteable(getValueAsBoolean(PROPERTY_FORM_WRITABLE, formNode));

                        if (element instanceof StartEvent) {
                            ((StartEvent) element).getFormProperties().add(formProperty);
                        } else if (element instanceof UserTask) {
                            ((UserTask) element).getFormProperties().add(formProperty);
                        }
                    }
                }
            }
        }
    }

    // TODO: 2020/12/16 ？
    @Override
    protected void addFormProperties(List<FormProperty> formProperties, ObjectNode propertiesNode) {
        super.addFormProperties(formProperties, propertiesNode);
    }
}
