package com.realfinance.sofa.cg.service.mapstruct;


import com.realfinance.sofa.cg.model.cg.CgSupplierBadBehaviorSaveRequest;
import com.realfinance.sofa.cg.model.cg.CgSupplierBadBehaviorVo;
import com.realfinance.sofa.cg.sup.model.CgSupplierBadBehaviorDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierBadBehaviorSaveDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface CgSupplierBadBehaviorMapper {

    @Mapping(target = "createdUser", source = "createdUserId")
    @Mapping(target = "modifiedUser", source = "modifiedUserId")
    CgSupplierBadBehaviorVo toVo(CgSupplierBadBehaviorDto dto);

    CgSupplierBadBehaviorSaveDto toSaveDto(CgSupplierBadBehaviorSaveRequest saveRequest);

}
