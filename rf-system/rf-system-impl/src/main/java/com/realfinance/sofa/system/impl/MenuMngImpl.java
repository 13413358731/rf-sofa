package com.realfinance.sofa.system.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.realfinance.sofa.system.domain.Menu;
import com.realfinance.sofa.system.domain.MenuDataRule;
import com.realfinance.sofa.system.exception.RfSystemException;
import com.realfinance.sofa.system.facade.MenuMngFacade;
import com.realfinance.sofa.system.model.MenuDataRuleDto;
import com.realfinance.sofa.system.model.MenuDataRuleSaveDto;
import com.realfinance.sofa.system.model.MenuDto;
import com.realfinance.sofa.system.model.MenuSaveDto;
import com.realfinance.sofa.system.repository.MenuDataRuleRepository;
import com.realfinance.sofa.system.repository.MenuRepository;
import com.realfinance.sofa.system.service.mapstruct.MenuDataRuleMapper;
import com.realfinance.sofa.system.service.mapstruct.MenuDataRuleSaveMapper;
import com.realfinance.sofa.system.service.mapstruct.MenuMapper;
import com.realfinance.sofa.system.service.mapstruct.MenuSaveMapper;
import com.realfinance.sofa.system.util.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.realfinance.sofa.system.util.ExceptionUtils.businessException;
import static com.realfinance.sofa.system.util.ExceptionUtils.entityNotFound;

@Service
@SofaService(interfaceType = MenuMngFacade.class, uniqueId = "${service.rf-system.id}",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "jvm")
        })
@Transactional(readOnly = true)
public class MenuMngImpl implements MenuMngFacade {

    private static final Logger log = LoggerFactory.getLogger(MenuMngImpl.class);

    // 菜单查询排序
    private static final Sort SORT = Sort.by("sort");

    private final MenuRepository menuRepository;
    private final MenuDataRuleRepository menuDataRuleRepository;
    private final MenuMapper menuMapper;
    private final MenuSaveMapper menuSaveMapper;
    private final MenuDataRuleMapper menuDataRuleMapper;
    private final MenuDataRuleSaveMapper menuDataRuleSaveMapper;


    public MenuMngImpl(MenuRepository menuRepository, MenuDataRuleRepository menuDataRuleRepository, MenuMapper menuMapper, MenuDataRuleMapper menuDataRuleMapper, MenuSaveMapper menuSaveMapper, MenuDataRuleSaveMapper menuDataRuleSaveMapper) {
        this.menuRepository = menuRepository;
        this.menuDataRuleRepository = menuDataRuleRepository;
        this.menuMapper = menuMapper;
        this.menuDataRuleMapper = menuDataRuleMapper;
        this.menuSaveMapper = menuSaveMapper;
        this.menuDataRuleSaveMapper = menuDataRuleSaveMapper;
    }

    @Override
    public List<MenuDto> listFirstLevel() {
        List<Menu> firstLevelMenus = menuRepository.findByType(Menu.Type.FIRST_LEVEL,SORT);
        return menuMapper.toDtoList(firstLevelMenus);
    }

    @Override
    public List<MenuDto> listByParentId(Integer parentId) {
        List<Menu> menus;
        if (parentId == null) {
            menus = menuRepository.findByParentIsNull(SORT);
        } else {
            menus = menuRepository.findByParent(menuRepository.getOne(parentId), SORT);
        }
        return menuMapper.toDtoList(menus);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer save(MenuSaveDto saveDto) {
        Objects.requireNonNull(saveDto);
        try {
            Menu saved;
            if (saveDto.getId() == null) { // 新增
                saved = handleNewMenu(saveDto);
            } else { // 修改
                saved = handleUpdateMenu(saveDto);
            }
            menuRepository.flush();
            return saved.getId();
        } catch (RfSystemException e) {
            throw e;
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("保存失败",e);
            }
            throw ExceptionUtils.businessException("保存失败：" + e.getMessage());
        }
    }

    /**
     * 处理新增菜单
     * @param saveDto
     * @return
     */
    protected Menu handleNewMenu(MenuSaveDto saveDto) {
        Menu menu = menuSaveMapper.toEntity(saveDto);
        menu.resetPath();
        setParentToNullIfTypeIsFirstLevel(menu);
        menu.setLeafCount(0);
        menu.setHasMenuDataRule(Boolean.FALSE);

        if (menuRepository.existsByCode(menu.getCode())) {
            throw businessException("菜单编码已存在");
        }
        // 检查子级类型的菜单的必须包含父ID
        if (menu.getType() != Menu.Type.FIRST_LEVEL && menu.getParent() == null) {
            throw businessException("子菜单的父级不能为null");
        }
        // 校验父节点不能为BUTTON
        if (menu.getParent() != null && menu.getParent().getType().equals(Menu.Type.BUTTON)) {
            throw businessException("不能添加子菜单");
        }
        Menu saved = menuRepository.save(menu);
        updateLeafCount(menu.getParent());
        return saved;
    }

    /**
     * 处理更新菜单
     * @param saveDto
     * @return
     */
    protected Menu handleUpdateMenu(MenuSaveDto saveDto) {
        Menu menu = menuRepository.findById(saveDto.getId())
                .orElseThrow(() -> entityNotFound(Menu.class, "id", saveDto.getId()));
        // 修改前的父节点
        Menu formerParent = menu.getParent();
        // 如果编码修改，则校验编码是否可用
        if (!Objects.equals(saveDto.getCode(),menu.getCode())
                && menuRepository.existsByCode(saveDto.getCode())) {
            throw businessException("菜单编码已存在");
        }

        menuSaveMapper.updateEntity(menu,saveDto);
        boolean pathChanged = menu.resetPath();
        setParentToNullIfTypeIsFirstLevel(menu);

        // 检查子级类型的菜单的必须包含父ID
        if (menu.getType() != Menu.Type.FIRST_LEVEL && menu.getParent() == null) {
            throw businessException("子菜单的父级不能为null");
        }
        // 校验父节点不能为BUTTON
        if (menu.getParent() != null && menu.getParent().getType().equals(Menu.Type.BUTTON)) {
            throw businessException("不能添加子菜单");
        }
        Menu saved = menuRepository.save(menu);

        updateLeafCount(formerParent);
        updateLeafCount(menu.getParent());
        // 如果path改变，修改子节点的path
        if (pathChanged) {
            resetChildrenPath(menu);
        }
        return saved;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Integer> ids) {
        Objects.requireNonNull(ids);
        List<Menu> toDelete = menuRepository.findAllById(ids);
        try {
            if (!toDelete.isEmpty()) {
                for (Menu menu : toDelete) {
                    Menu parent = menu.getParent();
                    menuRepository.delete(menu);
                    updateLeafCount(parent);
                }
                menuRepository.flush();
            }
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("删除失败",e);
            }
            throw ExceptionUtils.businessException("删除失败：" + e.getMessage());
        }
    }

    @Override
    public List<MenuDataRuleDto> listMenuDataRule(Integer id) {
        Objects.requireNonNull(id);
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> entityNotFound(Menu.class, "id", id));
        return menuDataRuleMapper.toDtoList(menuDataRuleRepository.findByMenu(menu));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    // TODO: 2020/10/27 可以对菜单类型做校验
    public Integer saveMenuDataRule(MenuDataRuleSaveDto menuDataRuleSaveDto) {
        Objects.requireNonNull(menuDataRuleSaveDto);
        MenuDataRule menuDataRule;
        if (menuDataRuleSaveDto.getId() ==  null) { // 新增
            menuDataRule = menuDataRuleSaveMapper.toEntity(menuDataRuleSaveDto);
        } else { // 修改
            menuDataRule = menuDataRuleRepository.findById(menuDataRuleSaveDto.getId())
                    .orElseThrow(() -> entityNotFound(MenuDataRule.class, "id", menuDataRuleSaveDto.getId()));
            menuDataRuleSaveMapper.updateEntity(menuDataRule,menuDataRuleSaveDto);
        }
        try {
            MenuDataRule saved = menuDataRuleRepository.saveAndFlush(menuDataRule);
            Menu menu = saved.getMenu();
            if (menu.resetHasMenuDataRule()) {
                menuRepository.saveAndFlush(menu);
            }
            return saved.getId();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("保存菜单数据规则失败",e);
            }
            throw ExceptionUtils.businessException("保存菜单数据规则失败：" + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMenuDataRule(Set<Integer> menuDataRuleIds) {
        Objects.requireNonNull(menuDataRuleIds);
        List<MenuDataRule> toDelete = menuDataRuleRepository.findAllById(menuDataRuleIds);
        try {
            for (MenuDataRule menuDataRule : toDelete) {
                Menu menu = menuDataRule.getMenu();
                menu.getMenuDataRules().remove(menuDataRule);
                menuDataRuleRepository.delete(menuDataRule);
                if (menu.resetHasMenuDataRule()) {
                    menuRepository.save(menu);
                }
            }
            menuDataRuleRepository.flush();
            menuRepository.flush();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("删除失败",e);
            }
            throw ExceptionUtils.businessException("删除失败：" + e.getMessage());
        }
    }

    /**
     * 更新子页数
     * @param menu
     */
    private void updateLeafCount(Menu menu) {
        if (menu == null) {
            return;
        }
        menu.setLeafCount(menuRepository.countByParent(menu));
        try {
            menuRepository.save(menu);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("更新子页数失败",e);
            }
            throw businessException("更新子页数失败");
        }
    }

    /**
     * 递归修改子节点path
     * @param menu
     */
    private void resetChildrenPath(Menu menu) {
        if (menu == null || menu.getLeafCount() <= 0) {
            return;
        }
        menuRepository.findByParent(menu).stream()
                .filter(Menu::resetPath)
                .forEach(e -> {
                    menuRepository.save(e);
                    resetChildrenPath(e);
                });
    }

    /**
     * 如果菜单类型为一级菜单，则设置父ID为{@code null}
     * @param menu
     */
    private void setParentToNullIfTypeIsFirstLevel(Menu menu) {
        if (menu != null && menu.getType() == Menu.Type.FIRST_LEVEL) {
            menu.setParent(null);
        }
    }
}
