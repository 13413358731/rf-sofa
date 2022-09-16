package com.realfinance.sofa.cg.sup.model;

import javax.validation.constraints.NotBlank;

public class ResetPasswordDto {
    @NotBlank
    private String password;
    @NotBlank
    private String newPassword;
    @NotBlank
    private String newPassword2;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPassword2() {
        return newPassword2;
    }

    public void setNewPassword2(String newPassword2) {
        this.newPassword2 = newPassword2;
    }
}
