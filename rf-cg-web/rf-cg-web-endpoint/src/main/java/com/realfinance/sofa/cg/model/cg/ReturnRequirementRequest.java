package com.realfinance.sofa.cg.model.cg;

/**
 * 退回采购申请
 */
public class ReturnRequirementRequest {
    private Integer id;
    private String reason;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
