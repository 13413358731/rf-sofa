package com.realfinance.sofa.system.facade;

import com.realfinance.sofa.system.model.DepartmentDto;
import com.realfinance.sofa.system.model.DepartmentSaveDto;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

/**
 * 部门管理
 * TODO 还需要一些条件查询的方法
 */
public interface DepartmentMngFacade {

    /**
     * 查询一级部门
     * 结果会根据sort排序
     * @return
     */
    List<DepartmentDto> listFirstLevel();

    /**
     * 根据父ID查询
     * 结果会根据sort排序
     * @param parentId 菜单父ID，可以为{@code null}
     * @return
     */
    List<DepartmentDto> listByParentId(Integer parentId);

    /**
     * 保存部门
     * @param saveDto
     * @return ID
     */
    Integer save(@NotNull DepartmentSaveDto saveDto);

    /**
     * 删除部门
     * @param ids 部门ID集合
     */
    void delete(@NotNull Set<Integer> ids);
}
