package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.proj.ProjectRelationship;
import com.realfinance.sofa.cg.core.domain.purresult.PurResultRelationship;
import com.realfinance.sofa.cg.core.model.CgProjectRelationshipDto;
import com.realfinance.sofa.cg.core.model.CgPurchaseResultRelationshipDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface PurchaseResultRelationshipMapper extends ToDtoMapper<PurResultRelationship, CgPurchaseResultRelationshipDto>{
}
