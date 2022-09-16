package com.realfinance.sofa.system.model;

public class TenantQueryCriteria {

    /**
     * ID
     */
    private String Id;
    /**
     * 名字模糊
     */
    private String nameLike;
    /**
     * 是否可用
     */
    private Boolean enabled;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getNameLike() {
        return nameLike;
    }

    public void setNameLike(String nameLike) {
        this.nameLike = nameLike;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
