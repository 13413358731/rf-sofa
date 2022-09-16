package com.realfinance.sofa.cg.sup.model;

public class CgSupplierQueryCriteria {

    /**
     * 统一社会信用代码
     */
    private String unifiedSocialCreditCode;

    /**
     * 供应商名称模糊
     */
    private String nameLike;

    /**
     * 供应商用户名
     */
    private String username;

    public String getUnifiedSocialCreditCode() {
        return unifiedSocialCreditCode;
    }

    public void setUnifiedSocialCreditCode(String unifiedSocialCreditCode) {
        this.unifiedSocialCreditCode = unifiedSocialCreditCode;
    }

    public String getNameLike() {
        return nameLike;
    }

    public void setNameLike(String nameLike) {
        this.nameLike = nameLike;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
