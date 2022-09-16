package com.realfinance.sofa.sdebank.model;

public class EquityPenetrationDto {

    /**
     * 供应商库id
     */
    private Integer supplierId;

    /**
     * 公司名称(企业名称)
     */
    private String name;

    /**
     * 统一社会信用代码
     */
    private String unifiedSocialCreditCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnifiedSocialCreditCode() {
        return unifiedSocialCreditCode;
    }

    public void setUnifiedSocialCreditCode(String unifiedSocialCreditCode) {
        this.unifiedSocialCreditCode = unifiedSocialCreditCode;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }
}
