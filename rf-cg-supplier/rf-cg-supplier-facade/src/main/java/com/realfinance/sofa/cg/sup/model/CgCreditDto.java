package com.realfinance.sofa.cg.sup.model;

import java.util.List;

/**
 * 供应商信用信息
 */
public class CgCreditDto {

    /**
     * 采购需求(采购申请),采购方案,采购结果子表-推荐供应商的主键id
     */
    protected Integer id;

    /**
     * 供应商库数据
     */
    protected CgSupplierDto supplier;

    /**
     * 供应商信用情况
     */
    protected String supplierCreditStatus;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CgSupplierDto getSupplier() {
        return supplier;
    }

    public void setSupplier(CgSupplierDto supplier) {
        this.supplier = supplier;
    }

    public String getSupplierCreditStatus() {
        return supplierCreditStatus;
    }

    public void setSupplierCreditStatus(String supplierCreditStatus) {
        this.supplierCreditStatus = supplierCreditStatus;
    }


}
