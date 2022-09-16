package com.realfinance.sofa.system.facade;

import com.realfinance.sofa.system.model.UserAuthInfoDto;

import javax.validation.constraints.NotNull;

/**
 * 用户授权信息
 */
public interface UserAuthInfoFacade {


    /**
     * 查询用户授权信息
     * @param tenantId 租户ID
     * @param username 用户名
     * @return
     */
    UserAuthInfoDto getUserAuthInfo(@NotNull String tenantId, @NotNull String username);

    /**
     * 检查密码
     * @param tenantId 租户ID
     * @param username 用户名
     * @param password 密码
     * @return
     */
    Boolean checkPassword(@NotNull String tenantId, @NotNull String username, @NotNull String password);

}
