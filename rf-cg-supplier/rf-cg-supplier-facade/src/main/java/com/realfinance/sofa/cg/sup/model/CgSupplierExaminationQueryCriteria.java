package com.realfinance.sofa.cg.sup.model;

import java.util.Set;

public class CgSupplierExaminationQueryCriteria {

    /**
     * 统一社会信用代码
     */
    private String unifiedSocialCreditCode;

    /**
     * 业务类型（新注册，门户信息修改，系统内信息修改）
     */
    private String category;

    /**
     * 供应商名称模糊
     */
    private String nameLike;

    /**
     * 供应商账号ID
     */
    private Integer accountId;

    /**
     * 供应商ID
     */
    private Integer supplierId;

    /**
     * 供应商用户名
     */
    private String username;

    /**
     * 类型In
     */
    private Set<String> categoryIn;

    /**
     * 处理状态
     */
    private String status;

    public String getUnifiedSocialCreditCode() {
        return unifiedSocialCreditCode;
    }

    public void setUnifiedSocialCreditCode(String unifiedSocialCreditCode) {
        this.unifiedSocialCreditCode = unifiedSocialCreditCode;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNameLike() {
        return nameLike;
    }

    public void setNameLike(String nameLike) {
        this.nameLike = nameLike;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<String> getCategoryIn() {
        return categoryIn;
    }

    public void setCategoryIn(Set<String> categoryIn) {
        this.categoryIn = categoryIn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
