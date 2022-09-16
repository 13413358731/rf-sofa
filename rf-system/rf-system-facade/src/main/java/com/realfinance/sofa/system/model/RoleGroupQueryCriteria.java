package com.realfinance.sofa.system.model;

public class RoleGroupQueryCriteria {
    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 角色组编码
     */
    private String code;
    /**
     * 名称
     */
    private String name;

    /**
     * 角色组编码或角色组名称模糊
     */
    private String codeLikeOrNameLike;

    /**
     * 是否可用
     */
    private Boolean enabled;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
