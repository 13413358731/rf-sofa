package com.realfinance.sofa.flow.flowable;

public interface FlowConstants {

    // 引用业务单据
    String PROCESS_INSTANCE_REFERENCE_TYPE = "BIZ";

    String ASSIGNEES_VARIABLE_KEY = "__ASSIGNEES";
    String ASSIGNEE_VARIABLE_KEY = "__ASSIGNEE";
    String DUE_DATE_VARIABLE_KEY = "__DUE_DATE";
    String PRIORITY_VARIABLE_KEY = "__PRIORITY";

    // 业务数据是否可编辑
    String BUSINESS_DATA_EDITABLE_CUSTOM_PROPERTY_ID = "businessdataeditable";
}
