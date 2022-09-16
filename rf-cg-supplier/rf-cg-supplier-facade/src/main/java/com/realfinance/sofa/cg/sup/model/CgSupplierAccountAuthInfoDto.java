package com.realfinance.sofa.cg.sup.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

public class CgSupplierAccountAuthInfoDto implements Serializable {

    protected String username;

    protected String password;

    protected Integer supplierId;

    protected String mobile;

    protected Boolean enabled;

    protected LocalDateTime passwordModifiedTime;

    protected Collection<String> authorities;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
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

    public Collection<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<String> authorities) {
        this.authorities = authorities;
    }
}
