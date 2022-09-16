package com.realfinance.sofa.cg.service.mapstruct;

import com.realfinance.sofa.cg.core.model.*;
import com.realfinance.sofa.cg.model.cg.*;
import com.realfinance.sofa.cg.sup.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface CgExpertLabelMapper {

    CgExpertLabelVo toVo(CgExpertLabelSmallDto dto);
//    CgExpertLabelVo toVo(CgExpertLabelDto dto);

//    CgExpertLabelSaveDto toSaveDto(CgExpertLabelVo vo);

    CgExpertLabelSaveDto toSaveDto(CgExpertLabelVo vo);
    CgExpertLabelTypeSaveDto toSaveDto(CgExpertLabelTypeVo vo);

//    @Mapping(target = "createdUser", source = "createdUserId")
//    @Mapping(target = "modifiedUser", source = "modifiedUserId")
    CgExpertLabelTypeVo toVo(CgExpertLabelTypeDto cgExpertLabelTypeDto);

//    @Mapping(target = "createdUser", source = "createdUserId")
//    @Mapping(target = "modifiedUser", source = "modifiedUserId")
    CgExpertLabelVo toVo(CgExpertLabelDto cgExpertLabelDto);

//    CgExpertLabelVo toVo(CgExpertLabelSmallDto cgExpertLabelSmallDto);

    @Mapping(target = "createdUser", source = "createdUserId", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "modifiedUser", source = "modifiedUserId", ignore = true)
    @Mapping(target = "modifiedTime", ignore = true)
    @Mapping(target = "type", ignore = true)
    CgExpertLabelTreeVo toSmallTreeVo(CgExpertLabelDto cgExpertLabelDto);

    /**
     * 构建小的标签树
     * 没有createdUser、createdTime、modifiedUser、modifiedTime、type字段
     * @param all 根节点集合
     * @return 根节点集合
     */
    default List<CgExpertLabelTreeVo> buildSmallTree(List<CgExpertLabelDto> all) {
        List<CgExpertLabelTreeVo> root = new ArrayList<>();
        Map<Integer, CgExpertLabelTreeVo> idMap = all.stream()
                .map(CgExpertLabelMapper.this::toSmallTreeVo)
                .collect(Collectors.toMap(CgExpertLabelTreeVo::getId, e -> e));
        idMap.values().forEach(e -> {
            if (e.getParent() != null) {
                CgExpertLabelTreeVo parent = idMap.get(e.getParent().getId());
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
