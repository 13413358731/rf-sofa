package com.realfinance.sofa.flow.flowable.form;

import org.flowable.bpmn.model.FormProperty;
import org.flowable.bpmn.model.FormValue;
import org.flowable.engine.form.AbstractFormType;
import org.flowable.engine.impl.form.FormTypes;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CustomFormTypes extends FormTypes {

    @Override
    public AbstractFormType parseFormPropertyType(FormProperty formProperty) {
        if ("users".equals(formProperty.getType())) {
            List<FormValue> formValues = formProperty.getFormValues();
            List<String> userScope = null;
            List<String> groupScope = null;
            int maxSize = 1;
            int minSize = 1;
            for (FormValue formValue : formValues) {
                if ("userScope".equals(formValue.getId())) {
                    userScope = toList(formValue);
                } else if ("groupScope".equals(formValue.getId())) {
                    groupScope = toList(formValue);
                } else if ("maxSize".equals(formValue.getId())) {
                    maxSize = Integer.parseInt(formValue.getName());
                } else if ("minSize".equals(formValue.getId())) {
                    minSize = Integer.parseInt(formValue.getName());
                }
            }
            return new UsersFormType(userScope,groupScope,maxSize,minSize);
        }

        return super.parseFormPropertyType(formProperty);
    }

    private List<String> toList(FormValue formValue) {
        if (formValue != null) {
            String[] split = formValue.getName().split(",");
            return Arrays.stream(split).collect(Collectors.toList());
        }
        return null;
    }
}
