package com.realfinance.sofa.system.model;

public class AssociatedTranDto {
    /**
     * (采购需求,采购方案,采购结果)子表-供应商信息主键id
     */
    protected Integer supId;
    /**
     * 法人身份证号码
     */
    protected String idCardNumber;
    /**
     * 证件类型
     */
    protected String type;

    /**
     * 关联交易情况
     */
    protected String status;

    public Integer getSupId() {
        return supId;
    }

    public void setSupId(Integer supId) {
        this.supId = supId;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
