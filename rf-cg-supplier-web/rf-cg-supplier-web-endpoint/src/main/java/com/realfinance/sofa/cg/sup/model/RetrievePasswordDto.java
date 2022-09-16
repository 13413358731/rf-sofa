package com.realfinance.sofa.cg.sup.model;

import javax.validation.constraints.NotNull;

public class RetrievePasswordDto {
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String password2;
    @NotNull
    private String code;

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

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
