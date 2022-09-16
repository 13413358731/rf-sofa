package com.realfinance.sofa.system.facade;

import com.realfinance.sofa.system.model.UserDetailsDto;
import com.realfinance.sofa.system.model.UserDto;
import com.realfinance.sofa.system.model.UserQueryCriteria;
import com.realfinance.sofa.system.model.UserSaveDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

/**
 * 用户管理
 */
public interface UserMngFacade {

    /**
     * 查询用户列表
     * @param queryCriteria 查询条件
     * @param pageable
     * @return
     */
    Page<UserDto> list(UserQueryCriteria queryCriteria, @NotNull Pageable pageable);

    /**
     * 查询用户详情
     * @param id 用户ID
     * @return
     */
    UserDetailsDto getDetailsById(@NotNull Integer id);

    /**
     * 查询用户详情集合
     * @param ids 用户ID集合
     * @return
     */
    List<UserDetailsDto> getDetailsByIds(@NotNull Set<Integer> ids);

    /**
     * 保存用户
     * @param userSaveDto
     * @return ID
     */
    Integer save(@NotNull UserSaveDto userSaveDto);

    /**
     * 重置密码
     * 如果原密码为{@code null}则不校验原密码
     * @param id 用户ID
     * @param password 原密码
     * @param newPassword 新密码
     * @param newPassword2 重复新密码
     */
    void resetPassword(@NotNull Integer id, String password,
                       @NotNull String newPassword, @NotNull String newPassword2);


    /**
     * 删除用户
     * @param ids 用户ID集合
     */
    void delete(@NotNull Set<Integer> ids);

}
