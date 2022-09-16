package com.realfinance.sofa.system.facade;

import com.realfinance.sofa.system.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * 角色组管理
 */
public interface RoleGroupMngFacade {

    /**
     * 查询列表
     * @param queryCriteria
     * @param pageable
     * @return
     */
    Page<RoleGroupDto> list(RoleGroupQueryCriteria queryCriteria, @NotNull Pageable pageable);

    /**
     * 保存角色组
     * @param saveDto
     * @return ID
     */
    Integer save(@NotNull RoleGroupSaveDto saveDto);

    /**
     * 删除角色组
     * @param ids 角色组ID集合
     */
    void delete(@NotNull Set<Integer> ids);

    /**
     * 分页查询与角色组关联的用户
     * @param id 角色组ID
     * @param filter 搜索过滤，用户名或姓名模糊
     * @param pageable
     * @return
     */
    Page<UserDto> listUsersById(@NotNull Integer id, String filter, @NotNull Pageable pageable);

    /**
     * 添加角色组用户关联
     * @param id 角色组ID
     * @param userIds 用户ID集合
     */
    void addUsers(@NotNull Integer id, @NotNull Set<Integer> userIds);

    /**
     * 删除用户角色组关联
     * @param id 角色组ID
     * @param userIds 用户ID集合
     */
    void removeUsers(@NotNull Integer id, @NotNull Set<Integer> userIds);

    /**
     * 分页查询与角色组关联的角色
     * @param id 角色组ID
     * @param filter 搜索过滤，编码或名称模糊
     * @param pageable
     * @return
     */
    Page<RoleDto> listRolesById(@NotNull Integer id, String filter, @NotNull Pageable pageable);

    /**
     * 添加角色组与角色关联
     * @param id
     * @param roleIds
     */
    void addRoles(@NotNull Integer id, @NotNull Set<Integer> roleIds);

    /**
     * 删除角色组与角色关联
     * @param id
     * @param roleIds
     */
    void removeRoles(@NotNull Integer id, @NotNull Set<Integer> roleIds);
}
