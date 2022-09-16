package com.realfinance.sofa.cg.service.mapstruct;

import com.realfinance.sofa.cg.model.cg.CgSupplierLabelTreeVo;
import com.realfinance.sofa.cg.model.cg.CgSupplierLabelTypeVo;
import com.realfinance.sofa.cg.model.cg.CgSupplierLabelVo;
import com.realfinance.sofa.cg.sup.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface CgSupplierLabelMapper {

    CgSupplierLabelSaveDto toSaveDto(CgSupplierLabelVo vo);
    CgSupplierLabelTypeSaveDto toSaveDto(CgSupplierLabelTypeVo vo);

    @Mapping(target = "createdUser", source = "createdUserId")
    @Mapping(target = "modifiedUser", source = "modifiedUserId")
    CgSupplierLabelTypeVo toVo(CgSupplierLabelTypeDto cgSupplierLabelTypeDto);

    @Mapping(target = "createdUser", source = "createdUserId")
    @Mapping(target = "modifiedUser", source = "modifiedUserId")
    CgSupplierLabelVo toVo(CgSupplierLabelDto cgSupplierLabelDto);

    CgSupplierLabelVo toVo(CgSupplierLabelSmallDto cgSupplierLabelSmallDto);

    @Mapping(target = "createdUser", source = "createdUserId", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "modifiedUser", source = "modifiedUserId", ignore = true)
    @Mapping(target = "modifiedTime", ignore = true)
    @Mapping(target = "type", ignore = true)
    CgSupplierLabelTreeVo toSmallTreeVo(CgSupplierLabelDto cgSupplierLabelDto);

    /**
     * 构建小的标签树
     * 没有createdUser、createdTime、modifiedUser、modifiedTime、type字段
     * @param all 根节点集合
     * @return 根节点集合
     */
    default List<CgSupplierLabelTreeVo> buildSmallTree(List<CgSupplierLabelDto> all) {
        List<CgSupplierLabelTreeVo> root = new ArrayList<>();
        Map<Integer, CgSupplierLabelTreeVo> idMap = all.stream()
                .map(CgSupplierLabelMapper.this::toSmallTreeVo)
                .collect(Collectors.toMap(CgSupplierLabelTreeVo::getId, e -> e));
        idMap.values().forEach(e -> {
            if (e.getParent() != null) {
                CgSupplierLabelTreeVo parent = idMap.get(e.getParent().getId());
                if (parent != null) {
                    parent.getChildren().add(e);
                }
            }
            if (e.getParent() == null) {
                root.add(e);
            }
        });
        return root;
    }
}
