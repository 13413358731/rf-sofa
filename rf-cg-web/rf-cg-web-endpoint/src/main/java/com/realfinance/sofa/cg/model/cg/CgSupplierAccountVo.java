package com.realfinance.sofa.cg.model.cg;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.realfinance.sofa.cg.model.BaseVo;
import com.realfinance.sofa.common.model.IdentityObject;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Schema(description = "供应商账号对象")
public class CgSupplierAccountVo extends BaseVo implements IdentityObject<Integer> {

    /**
     * 新建账号
     */
    public interface Create {
        String getUsername();
        String getPassword();
        Boolean getEnabled();
    }

    /**
     * 在采购系统内修改密码
     */
    public interface ResetPassword {
        String getUsername();
        String getPassword();
    }

    /**
     * 更新可用状态
     */
    public interface UpdateEnabled {
        String getUsername();
        Boolean getEnabled();
    }

    @Schema(description = "ID")
    protected Integer id;

    @Pattern(regexp = "[a-zA-Z0-9_]{6,20}", groups = {Create.class, ResetPassword.class, UpdateEnabled.class})
    @NotNull(groups = {Create.class, ResetPassword.class, UpdateEnabled.class})
    @Schema(description = "用户名")
    protected String username;

    @Size(min = 8, max = 20, groups = {ResetPassword.class})
    @NotNull(groups = {Create.class, ResetPassword.class})
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(description = "密码", accessMode = Schema.AccessMode.WRITE_ONLY)
    protected String password;

    @Schema(description = "账号类型（自主注册，邀请注册）")
    protected String type;

    @Schema(description = "账号绑定的手机")
    protected String mobile;

    @NotNull(groups = {UpdateEnabled.class})
    @Schema(description = "是否启用")
    protected Boolean enabled;

    @Schema(description = "最近一次密码修改时间")
    protected LocalDateTime passwordModifiedTime;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

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

    public LocalDateTime getPasswordModifiedTime() {
        return passwordModifiedTime;
    }

    public void setPasswordModifiedTime(LocalDateTime passwordModifiedTime) {
        this.passwordModifiedTime = passwordModifiedTime;
    }
}
