package com.realfinance.sofa.cg.sup.model;

import java.time.LocalDateTime;

public class CgSupplierAccountDto extends BaseDto {

    protected Integer id;

    /**
     * 用户名
     */
    protected String username;

    /**
     * 账号类型（自主注册，邀请注册）
     */
    protected String type;

    /**
     * 账号绑定的手机
     */
    protected String mobile;

    /**
     * 是否启用
     */
    protected Boolean enabled;

    /**
     * 密码修改时间
     */
    protected LocalDateTime passwordModifiedTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public LocalDateTime getPasswordModifiedTime() {
        return passwordModifiedTime;
    }

    public void setPasswordModifiedTime(LocalDateTime passwordModifiedTime) {
        this.passwordModifiedTime = passwordModifiedTime;
    }

}
