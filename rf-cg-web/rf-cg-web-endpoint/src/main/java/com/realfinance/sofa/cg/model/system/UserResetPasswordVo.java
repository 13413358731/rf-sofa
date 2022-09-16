package com.realfinance.sofa.cg.model.system;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

public class UserResetPasswordVo {
    /**
     * 管理员修改
     */
    public interface UserMng {

    }

    /**
     * 用户修改
     */
    public interface Myself {

    }

    @NotNull(groups = UserMng.class)
    @Null(groups = Myself.class)
    @Schema(description = "用户ID", type = "integer")
    private Integer userId;
    @Null(groups = UserMng.class)
    @NotBlank(groups = Myself.class)
    @Schema(description = "原密码", type = "string")
    private String password;
    @NotBlank
    @Size(min = 6, max = 20)
    @Schema(description = "新密码", type = "string")
    private String newPassword;
    @Null(groups = UserMng.class)
    @NotBlank(groups = Myself.class)
    @Size(min = 6, max = 20)
    @Schema(description = "确认新密码", type = "string")
    private String newPassword2;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

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
