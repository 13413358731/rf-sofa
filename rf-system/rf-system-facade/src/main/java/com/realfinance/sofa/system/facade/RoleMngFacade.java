package com.realfinance.sofa.system.facade;

import com.realfinance.sofa.system.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

/**
 * 角色管理
 */
public interface RoleMngFacade {

    /**
     * 查询角色列表
     * @param queryCriteria 查询条件
     * @param pageable
     * @return
     */
    Page<RoleDto> list(RoleQueryCriteria queryCriteria, @NotNull Pageable pageable);

    /**
     * 分页查询与角色关联的用户
     * @param id 角色ID
     * @param filter 搜索过滤，用户名或姓名模糊条件
     * @param pageable
     * @return
     */
    Page<UserDto> listUsersById(@NotNull Integer id, String filter, @NotNull Pageable pageable);

    /**
     * 添加角色用户关联
     * @param id 角色ID
     * @param userIds 用户ID集合
     */
    void addUsers(@NotNull Integer id, @NotNull Set<Integer> userIds);

    /**
     * 删除用户角色关联
     * @param id 角色ID
     * @param userIds 用户ID集合
     */
    void removeUsers(@NotNull Integer id, @NotNull Set<Integer> userIds);

    /**
     * 查询角色关联的菜单ID集合
     * @param id 角色ID
     * @return
     */
    Set<Integer> listMenuIdsById(@NotNull Integer id);

    /**
     * 更新角色菜单关联
     * todo 是否需要判断当前登陆人是否含有此菜单才能授权？（暂时觉得不需要在此接口判断，如有需要可以在web端进行判断）
     * @param id 角色ID
     * @param menuIds 菜单ID集合
     */
    void updateMenus(@NotNull Integer id, @NotNull Set<Integer> menuIds);

    /**
     * 查询菜单下的所有数据规则
     * @param menuId
     * @return
     */
    List<MenuDataRuleDto> listMenuDataRule(@NotNull Integer menuId);

    /**
     * 查询角色在菜单下关联的数据规则ID集合
     * @param id 角色ID
     * @param menuId 菜单ID
     * @return
     */
    Set<Integer> listMenuDataRuleIdsByIdAndMenuId(@NotNull Integer id, @NotNull Integer menuId);

    /**
     * 更新角色菜单数据规则关联
     * @param id 角色ID
     * @param menuId 菜单ID
     * @param menuDataRuleIds 数据规则ID集合
     */
    void updateMenuDataRules(@NotNull Integer id, @NotNull Integer menuId, @NotNull Set<Integer> menuDataRuleIds);

    /**
     * 保存角色
     * @param saveDto
     * @return ID
     */
    Integer save(@NotNull RoleSaveDto saveDto);

    /**
     * 删除角色
     * @param ids 角色ID集合
     */
    void delete(@NotNull Set<Integer> ids);

}
