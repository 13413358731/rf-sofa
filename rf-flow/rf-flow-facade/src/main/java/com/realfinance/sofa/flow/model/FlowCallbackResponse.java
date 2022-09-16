package com.realfinance.sofa.flow.model;

import java.util.Objects;

public class FlowCallbackResponse {
    private Boolean success;
    private String message;
    private Object data;

    public FlowCallbackResponse() {
        this(Boolean.TRUE);
    }

    public FlowCallbackResponse(Boolean success) {
        this(success,"");
    }

    public FlowCallbackResponse(Boolean success, String message) {
        this(success,message,null);
    }

    public FlowCallbackResponse(Boolean success, String message, Object data) {
        this.success = Objects.requireNonNull(success);
        this.message = message;
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static FlowCallbackResponse ok() {
        return new FlowCallbackResponse(Boolean.TRUE);
    }

    public static FlowCallbackResponse ok(Object data) {
        return new FlowCallbackResponse(Boolean.TRUE, "", data);
    }

    public static FlowCallbackResponse fail(String message) {
        return new FlowCallbackResponse(Boolean.FALSE, message);
    }
}
