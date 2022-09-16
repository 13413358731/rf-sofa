package com.realfinance.sofa.system.facade;

import com.realfinance.sofa.system.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

/**
 * 通用查询接口
 */
public interface SystemQueryFacade {

    /**
     * 查询所有可用租户
     * @return
     */
    List<TenantDto> queryTenants();

    /**
     * 查询所有菜单
     * 结果根据sort正序排序
     * @return
     */
    List<MenuDto> queryMenus();

    /**
     * 查询部门ID路径集合
     * 比如路径为 /1/2/3 的部门 返回 List [1,2,3]
     * @param departmentId 部门ID
     * @return
     */
    List<Integer> queryDepartmentIdPath(@NotNull Integer departmentId);

    /**
     * 查询用户
     * 返回结果可为 {@code null}
     * @param userId 用户ID
     * @return
     */
    UserSmallDto queryUserSmallDto(@NotNull Integer userId);

    /**
     * 查询租户
     * 返回结果可为 {@code null}
     * @param tenantId 租户ID
     * @return
     */
    TenantSmallDto queryTenantSmallDto(@NotNull String tenantId);

    /**
     * 查询部门
     * 返回结果可为 {@code null}
     * @param departmentId 部门ID
     * @return
     */
    DepartmentSmallDto queryDepartmentSmallDto(@NotNull Integer departmentId);

    /**
     * 查询角色
     * 返回结果可为 {@code null}
     * @param roleId 角色ID
     * @return
     */
    RoleSmallDto queryRoleSmallDto(@NotNull Integer roleId);

    /**
     * 分页查询租户参照
     * @param queryCriteria
     * @param pageable
     * @return
     */
    Page<TenantSmallDto> queryTenantRefer(TenantQueryCriteria queryCriteria, @NotNull Pageable pageable);

    /**
     * 分页查询用户参照
     * @param queryCriteria
     * @param pageable
     * @return
     */
    Page<UserDto> queryUserRefer(UserQueryCriteria queryCriteria, @NotNull Pageable pageable);

    /**
     * 分页查询全部用户参照
     * @param queryCriteria
     * @param pageable
     * @return
     */
    Page<UserDto> queryAllUsersRefer(UserQueryCriteria queryCriteria, @NotNull Pageable pageable);

    /**
     * 分页查询角色参照
     * @param queryCriteria
     * @param pageable
     * @return
     */
    Page<RoleSmallDto> queryRoleRefer(RoleQueryCriteria queryCriteria, @NotNull Pageable pageable);

    /**
     * 查询部门参照
     * 返回根节点集合
     * @return
     */
    List<DepartmentSmallTreeDto> queryDepartmentRefer();

    /**
     * 查询部门参照
     * 返回根节点集合
     * @return
     */
    Page<DepartmentSmallDto> queryDepartmentRefer(DepartmentQueryCriteria queryCriteria, @NotNull Pageable pageable);

    /**
     * 查询菜单数据规则集合
     * @param tenantId
     * @param roleCodes
     * @param menuCode
     * @return
     */
    MenuDataRules queryMenuDataRules(@NotNull String tenantId, @NotNull Collection<String> roleCodes, @NotNull String menuCode);

    /**
     * 检查用户名是否已存在
     * @param tenantId 租户ID
     * @param username 用户名
     * @return
     */
    Boolean checkUsernameExist(@NotNull String tenantId, @NotNull String username);

    /**
     * 检查角色编码是否已存在
     * @param tenantId 租户ID
     * @param roleCode 角色编码
     * @return
     */
    Boolean checkRoleCodeExist(@NotNull String tenantId, @NotNull String roleCode);

    /**
     * 检查角色组编码是否已存在
     * @param tenantId 租户ID
     * @param roleGroupCode 角色组编码
     * @return
     */
    Boolean checkRoleGroupCodeExist(@NotNull String tenantId, @NotNull String roleGroupCode);

    /**
     * 检查租户ID是否已存在
     * @param tenantId 租户ID
     * @return
     */
    Boolean checkTenantIdExist(@NotNull String tenantId);

    /**
     * 检查部门编码是否已存在
     * @param tenantId 租户ID
     * @param departmentCode 部门编码
     * @return
     */
    Boolean checkDepartmentCodeExist(@NotNull String tenantId, @NotNull String departmentCode);

    /**
     * 检查菜单编码是否已存在
     * @param menuCode 菜单编码
     * @return
     */
    Boolean checkMenuCodeExist(String menuCode);

    /**
     * 检查用户是否在一个部门集合内
     * @param userId
     * @param departmentIds
     * @return
     */
    Boolean checkUserInDepartment(@NotNull Integer userId, @NotNull Collection<Integer> departmentIds);

    /**
     * 检查用户是否在一个角色集合内
     * @param userId
     * @param roleIds
     * @return
     */
    Boolean checkUserInRole(@NotNull Integer userId, @NotNull Collection<Integer> roleIds);

    /**
     * 根据传入的角色code集合筛选出用户id集合(审批流读取业务数据有用到,谨慎修改!)
     * @param roleCodeIn
     * @return
     */
    Collection<Integer> findRoleCodeInToUserIdIn(@NotNull Collection<String> roleCodeIn);

    /**
     * 根据传入的机构code集合筛选出用户id集合
     * @param departmentCodeIn
     * @return
     */
    Collection<Integer> findDepartmentCodeInToUserIdIn(@NotNull Collection<String> departmentCodeIn);


    String findDepartmentCodeToUserId(@NotNull Integer userId);
}
