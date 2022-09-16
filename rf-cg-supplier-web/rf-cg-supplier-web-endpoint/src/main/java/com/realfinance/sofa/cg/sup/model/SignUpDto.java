package com.realfinance.sofa.cg.sup.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class SignUpDto {
    @NotBlank
    @Pattern(regexp = "[a-zA-Z0-9_]{6,20}")
    private String username;
    @NotBlank
    @Size(min = 8, max = 20)
    // TODO: 2021/2/11 强度正则
    private String password;
    @NotBlank
    private String password2;
    @NotBlank
    private String mobile;
    @NotBlank
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "SignUpDto{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", password2='" + password2 + '\'' +
                ", mobile='" + mobile + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
