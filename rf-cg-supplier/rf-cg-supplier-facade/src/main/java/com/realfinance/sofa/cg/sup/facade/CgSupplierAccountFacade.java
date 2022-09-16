package com.realfinance.sofa.cg.sup.facade;

import com.realfinance.sofa.cg.sup.model.CgSupplierAccountAuthInfoDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierAccountCreateDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierAccountDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierAccountQueryCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;

public interface CgSupplierAccountFacade {

    /**
     * 分页查询
     * 该方法可以使用数据权限
     * @param queryCriteria
     * @param pageable
     * @return
     */
    Page<CgSupplierAccountDto> list(CgSupplierAccountQueryCriteria queryCriteria,
                                    @NotNull Pageable pageable);

    /**
     * 判断用户名是否存在
     * @param username
     * @return
     */
    Boolean existsByUsername(@NotNull String username);

    /**
     * 根据用户名查询
     * @param username
     * @return
     */
    CgSupplierAccountDto getByUsername(@NotNull String username);

    /**
     * 根据用户名查询登录授权信息
     * @param tenantId
     * @param username
     * @return
     */
    CgSupplierAccountAuthInfoDto getAuthInfoDto(@NotNull String tenantId, @NotNull String username);

    /**
     * 创建账号
     * @param createDto
     */
    Integer createAccount(@NotNull CgSupplierAccountCreateDto createDto);

    /**
     * 修改密码（无需校验）
     * @param username 用户名
     * @param password 密码
     */
    void resetPassword(@NotNull String username, @NotNull String password);

    /**
     * 修改密码
     * @param username
     * @param password
     * @param newPassword
     * @param newPassword2
     */
    void resetPassword(@NotNull String username, @NotNull String password, @NotNull String newPassword, @NotNull String newPassword2);

    /**
     * 更新是否可用
     * @param username 用户
     * @param enabled 是否可用
     */
    void updateEnabled(@NotNull String username, @NotNull Boolean enabled);
}
