package com.realfinance.sofa.cg.service.mapstruct;

import com.realfinance.sofa.cg.core.model.CgPurchaseCatalogDto;
import com.realfinance.sofa.cg.core.model.CgPurchaseCatalogSaveDto;
import com.realfinance.sofa.cg.model.cg.CgPurchaseCatalogTreeVo;
import com.realfinance.sofa.cg.model.cg.CgPurchaseCatalogVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface CgPurchaseCatalogMapper {

    @Mapping(target = "createdUser", source = "createdUserId")
    @Mapping(target = "modifiedUser", source = "modifiedUserId")
    CgPurchaseCatalogVo toVo(CgPurchaseCatalogDto cgPurchaseCatalogDto);

    @Mapping(target = "createdUser", source = "createdUserId")
    @Mapping(target = "modifiedUser", source = "modifiedUserId")
    CgPurchaseCatalogTreeVo toTreeVo(CgPurchaseCatalogDto cgPurchaseCatalogDto);

    CgPurchaseCatalogSaveDto toSaveDto(CgPurchaseCatalogVo vo);

    /**
     *
     * 没有createdUser、createdTime、modifiedUser、modifiedTime、type字段
     * @param all 根节点集合
     * @return 根节点集合
     */
    default List<CgPurchaseCatalogTreeVo> buildTree(List<CgPurchaseCatalogDto> all) {
        List<CgPurchaseCatalogTreeVo> root = new ArrayList<>();
        Map<Integer, CgPurchaseCatalogTreeVo> idMap = all.stream()
                .map(CgPurchaseCatalogMapper.this::toTreeVo)
                .collect(Collectors.toMap(CgPurchaseCatalogTreeVo::getId, e -> e));
        idMap.values().stream()
                .sorted(Comparator.comparing(CgPurchaseCatalogTreeVo::getCode))
                .forEach(e -> {
                    Integer parentId = e.getParent() == null ? null : e.getParent().getId();
                    if (!idMap.containsKey(parentId)) {
                        root.add(e);
                    }
                    CgPurchaseCatalogTreeVo parent = idMap.get(parentId);
                    if (parent != null) {
                        if (parent.getChildren() == null) {
                            parent.setChildren(new ArrayList<>());
                        }
                        parent.getChildren().add(e);
                    }
                });
        return root;
    }
}
