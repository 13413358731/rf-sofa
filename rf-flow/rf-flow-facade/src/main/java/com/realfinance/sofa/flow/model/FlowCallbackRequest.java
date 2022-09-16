package com.realfinance.sofa.flow.model;

import java.util.HashMap;
import java.util.Map;

public class FlowCallbackRequest {

    public static final String TYPE_START = "START"; // 流程启动
    public static final String TYPE_END= "END"; // 流程结束
    public static final String TYPE_DELETE = "DELETE"; // 删除流程
    public static final String TYPE_LOAD_DATA = "LOAD_DATA"; // 加载数据

    private String tenantId;
    private String type;
    private String businessCode;
    private String businessKey;
    private String comment;
    private Map<String,String> parameters;

    public FlowCallbackRequest() {
        this.parameters = new HashMap<>();
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public void setParameter(String key, String value) {
        if (key != null) {
            if (value != null) {
                parameters.put(key,value);
            } else {
                parameters.remove(key);
            }
        }
    }

    public boolean typeIs(String type) {
        return type != null && type.equals(this.type);
    }

    @Override
    public String toString() {
        return "FlowCallbackRequest{" +
                "tenantId='" + tenantId + '\'' +
                ", type='" + type + '\'' +
                ", businessCode='" + businessCode + '\'' +
                ", businessKey='" + businessKey + '\'' +
                ", comment='" + comment + '\'' +
                ", parameters=" + parameters +
                '}';
    }
}
