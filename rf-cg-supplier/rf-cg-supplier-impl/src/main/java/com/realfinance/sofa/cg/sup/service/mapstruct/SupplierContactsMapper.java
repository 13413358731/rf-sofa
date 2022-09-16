package com.realfinance.sofa.cg.sup.service.mapstruct;

import com.realfinance.sofa.cg.sup.domain.SupplierContacts;
import com.realfinance.sofa.cg.sup.model.SupplierContactsDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface SupplierContactsMapper extends ToDtoMapper<SupplierContacts, SupplierContactsDto>{
}
