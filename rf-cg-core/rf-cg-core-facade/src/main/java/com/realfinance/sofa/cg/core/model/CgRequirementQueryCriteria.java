package com.realfinance.sofa.cg.core.model;

import java.math.BigDecimal;

public class CgRequirementQueryCriteria {

    /**
     * 采购申请名称
     */
    private String name;

    /**
     * 申请采购金额
     */
    private BigDecimal reqTotalAmount;

    /**
     * 市场参考价/市场控制总价
     */
    private BigDecimal marketPrice;

    /**
     * 申请部门
     */
    private Integer departmentId;

    /**
     * 申请人
     */
    private Integer createdUserId;

    /**
     *采购部门
     */
    private Integer purDepartmentId;

    /**
     * 受理状态
     */
    private String acceptStatus;

    /**
     * 单据状态
     */
    private String status;

    /**
     * 经办人为null
     */
    private Boolean operatorUserIdIsNull;



    /**
     * 是否计划内
     */
    private String planStatus;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getReqTotalAmount() {
        return reqTotalAmount;
    }

    public void setReqTotalAmount(BigDecimal reqTotalAmount) {
        this.reqTotalAmount = reqTotalAmount;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(Integer createdUserId) {
        this.createdUserId = createdUserId;
    }

    public Integer getPurDepartmentId() {
        return purDepartmentId;
    }

    public void setPurDepartmentId(Integer purDepartmentId) {
        this.purDepartmentId = purDepartmentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAcceptStatus() {
        return acceptStatus;
    }

    public void setAcceptStatus(String acceptStatus) {
        this.acceptStatus = acceptStatus;
    }

    public Boolean getOperatorUserIdIsNull() {
        return operatorUserIdIsNull;
    }

    public void setOperatorUserIdIsNull(Boolean operatorUserIdIsNull) {
        this.operatorUserIdIsNull = operatorUserIdIsNull;
    }

    public String getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(String planStatus) {
        this.planStatus = planStatus;
    }
}
