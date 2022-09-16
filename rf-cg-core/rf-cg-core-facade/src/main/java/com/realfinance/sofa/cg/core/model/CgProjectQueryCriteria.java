package com.realfinance.sofa.cg.core.model;

import java.math.BigDecimal;

public class CgProjectQueryCriteria {

    /**
     * 项目编码模糊
     */
    private String projectNoLike;

    /**
     * 项目名称模糊
     */
    private String nameLike;

    /**
     * 采购类别
     */
    private String purCategory;

    /**
     * 采购方式
     * @return
     */
    private String purMode;

    /**
     * 评标方法
     * @return
     */
    private String evalMethod;

    /**
     * 申请金额(申请采购总金额)
     * @return
     */
    private BigDecimal reqTotalAmount;

    /**
     * 中选供应商数(拟成交供应商数)
     * @return
     */
    private Integer numberOfWinSup;

    /**
     * 单据状态
     * @return
     */
    private String status;


    public String getPurCategory() {
        return purCategory;
    }

    public void setPurCategory(String purCategory) {
        this.purCategory = purCategory;
    }

    public String getPurMode() {
        return purMode;
    }

    public void setPurMode(String purMode) {
        this.purMode = purMode;
    }

    public String getEvalMethod() {
        return evalMethod;
    }

    public void setEvalMethod(String evalMethod) {
        this.evalMethod = evalMethod;
    }

    public BigDecimal getReqTotalAmount() {
        return reqTotalAmount;
    }

    public void setReqTotalAmount(BigDecimal reqTotalAmount) {
        this.reqTotalAmount = reqTotalAmount;
    }

    public Integer getNumberOfWinSup() {
        return numberOfWinSup;
    }

    public void setNumberOfWinSup(Integer numberOfWinSup) {
        this.numberOfWinSup = numberOfWinSup;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNameLike() {
        return nameLike;
    }

    public void setNameLike(String nameLike) {
        this.nameLike = nameLike;
    }

    public String getProjectNoLike() {
        return projectNoLike;
    }

    public void setProjectNoLike(String projectNoLike) {
        this.projectNoLike = projectNoLike;
    }
}
