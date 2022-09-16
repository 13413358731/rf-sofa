package com.realfinance.sofa.cg.sup.model;

import javax.validation.constraints.NotNull;

public class CgSupplierAccountCreateDto {
    @NotNull
    private String username;
    @NotNull
    private String password;
    private String type;
    private String mobile;
    private Boolean enabled = true;

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

    @Override
    public String toString() {
        return "CgSupplierAccountCreateDto{" +
                "username='" + username + '\'' +
                ", type='" + type + '\'' +
                ", mobile='" + mobile + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
