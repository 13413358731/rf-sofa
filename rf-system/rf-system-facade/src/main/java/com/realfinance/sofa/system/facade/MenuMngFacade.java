package com.realfinance.sofa.system.facade;

import com.realfinance.sofa.system.model.MenuDataRuleDto;
import com.realfinance.sofa.system.model.MenuDataRuleSaveDto;
import com.realfinance.sofa.system.model.MenuDto;
import com.realfinance.sofa.system.model.MenuSaveDto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

/**
 * 菜单管理
 */
public interface MenuMngFacade {


    /**
     * 查询一级菜单
     * 结果会根据sort排序
     * @return
     */
    List<MenuDto> listFirstLevel();

    /**
     * 根据父ID查询
     * 结果会根据sort排序
     * @param parentId 菜单父ID，可以为{@code null}
     * @return
     */
    List<MenuDto> listByParentId(Integer parentId);

    /**
     * 保存菜单
     * @param menuSaveDto
     * @return ID
     */
    Integer save(@Valid @NotNull MenuSaveDto menuSaveDto);

    /**
     * 删除菜单
     * @param ids 菜单ID集合
     */
    void delete(@NotNull Set<Integer> ids);

    /**
     * 查询菜单数据规则
     * @param id 菜单ID
     */
    List<MenuDataRuleDto> listMenuDataRule(@NotNull Integer id);

    /**
     * 保存数据规则
     * @param menuDataRuleSaveDto
     * @return ID
     */
    Integer saveMenuDataRule(@NotNull MenuDataRuleSaveDto menuDataRuleSaveDto);

    /**
     * 删除数据规则
     * @param menuDataRuleIds 数据规则ID集合
     */
    void deleteMenuDataRule(@NotNull Set<Integer> menuDataRuleIds);
}
