package com.realfinance.sofa.cg.service.mapstruct;

import com.realfinance.sofa.cg.core.model.CgProjectRelationshipDto;
import com.realfinance.sofa.cg.core.model.CgPurchaseResultRelationshipDto;
import com.realfinance.sofa.cg.core.model.CgRequirementRelationshipDto;
import com.realfinance.sofa.cg.model.cg.CgProjectRelationshipVo;
import com.realfinance.sofa.cg.model.cg.CgPurchaseResultRelationshipVo;
import com.realfinance.sofa.cg.model.cg.CgRequirementRelationshipVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class, CgProjectMapper.class, CgSupplierMapper.class})
public interface CgRelationshipMapper {

    @Mapping(target = "createdUser", source = "createdUserId")
    @Mapping(target = "modifiedUser", source = "modifiedUserId")
    CgRequirementRelationshipVo toVo(CgRequirementRelationshipDto cgRequirementRelationshipDto);

    @Mapping(target = "createdUser", source = "createdUserId")
    @Mapping(target = "modifiedUser", source = "modifiedUserId")
    CgProjectRelationshipVo toVo(CgProjectRelationshipDto cgProjectRelationshipDto);

    @Mapping(target = "createdUser", source = "createdUserId")
    @Mapping(target = "modifiedUser", source = "modifiedUserId")
    CgPurchaseResultRelationshipVo toVo(CgPurchaseResultRelationshipDto cgPurchaseResultRelationshipDto);

}
