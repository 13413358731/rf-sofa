package com.realfinance.sofa.cg.service.mapstruct;

import com.realfinance.sofa.cg.model.system.MenuDataRuleSaveRequest;
import com.realfinance.sofa.cg.model.system.MenuSaveRequest;
import com.realfinance.sofa.cg.model.system.MenuTreeVo;
import com.realfinance.sofa.cg.model.system.MenuVo;
import com.realfinance.sofa.system.model.MenuDataRuleSaveDto;
import com.realfinance.sofa.system.model.MenuDto;
import com.realfinance.sofa.system.model.MenuSaveDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface MenuMapper {

    @Mapping(target = "createdUser", source = "createdUserId")
    @Mapping(target = "modifiedUser", source = "modifiedUserId")
    MenuVo menuDto2MenuVo(MenuDto menuDto);

    @Mapping(target = "createdUser", source = "createdUserId")
    @Mapping(target = "modifiedUser", source = "modifiedUserId")
    MenuTreeVo menuDto2MenuTreeVo(MenuDto menuDto);

    MenuSaveDto menuSaveRequest2MenuSaveDto(MenuSaveRequest menuSaveRequest);

    MenuDataRuleSaveDto menuDataRuleSaveRequest2MenuDataRuleSaveDto(MenuDataRuleSaveRequest menuDataRuleSaveRequest);

    /**
     * 构建菜单树
     * @param allMenus 根节点集合
     * @return 根节点集合
     */
    default List<MenuTreeVo> buildTree(List<MenuDto> allMenus) {
        List<MenuTreeVo> root = new ArrayList<>();
        Map<Integer, MenuTreeVo> idMap = allMenus.stream()
                .map(MenuMapper.this::menuDto2MenuTreeVo)
                .collect(Collectors.toMap(MenuTreeVo::getId, e -> e));
        idMap.values().forEach(e -> {
            if (e.getParent() != null) {
                MenuTreeVo parent = idMap.get(e.getParent().getId());
                if (parent != null) {
                    parent.getChildren().add(e);
                }
            }
            if ("FIRST_LEVEL".equals(e.getType())) {
                root.add(e);
            }
        });
        return root;
    }
}
