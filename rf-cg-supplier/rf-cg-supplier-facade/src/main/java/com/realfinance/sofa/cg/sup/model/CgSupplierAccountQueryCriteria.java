package com.realfinance.sofa.cg.sup.model;

public class CgSupplierAccountQueryCriteria {

    /**
     * 用户名模糊
     */
    private String usernameLike;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 账号类型（自主注册，邀请注册）
     */
    private String type;

    /**
     * 是否启用
     */
    private Boolean enabled;

    public String getUsernameLike() {
        return usernameLike;
    }

    public void setUsernameLike(String usernameLike) {
        this.usernameLike = usernameLike;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
