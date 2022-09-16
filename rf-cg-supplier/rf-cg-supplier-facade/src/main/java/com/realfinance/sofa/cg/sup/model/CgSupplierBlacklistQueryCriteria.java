package com.realfinance.sofa.cg.sup.model;

public class CgSupplierBlacklistQueryCriteria {

    /**
     * 供应商ID
     */
    private Integer supplierId;

    /**
     * 标题模糊
     */
    private String titleLike;

    /**
     * 处理状态
     */
    private String status;

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getTitleLike() {
        return titleLike;
    }

    public void setTitleLike(String titleLike) {
        this.titleLike = titleLike;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
