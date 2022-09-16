package com.realfinance.sofa.system.model;

public class RoleQueryCriteria {
    /**
     * 编码
     */
    private String code;

    /**
     * 编码模糊
     */
    private String codeLike;
    /**
     * 名字模糊
     */
    private String nameLike;

    /**
     * 角色编码或名字模糊
     */
    private String codeLikeOrNameLike;
    /**
     * 是否可用
     */
    private Boolean enabled;
    /**
     * 租户ID
     */
    private String tenantId;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeLike() {
        return codeLike;
    }

    public void setCodeLike(String codeLike) {
        this.codeLike = codeLike;
    }

    public String getNameLike() {
        return nameLike;
    }

    public void setNameLike(String nameLike) {
        this.nameLike = nameLike;
    }

    public String getCodeLikeOrNameLike() {
        return codeLikeOrNameLike;
    }

    public void setCodeLikeOrNameLike(String codeLikeOrNameLike) {
        this.codeLikeOrNameLike = codeLikeOrNameLike;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
