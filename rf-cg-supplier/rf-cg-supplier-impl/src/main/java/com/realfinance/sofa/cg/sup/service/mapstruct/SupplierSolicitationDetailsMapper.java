package com.realfinance.sofa.cg.sup.service.mapstruct;

import com.realfinance.sofa.cg.sup.domain.SupplierSolicitation;
import com.realfinance.sofa.cg.sup.model.CgSupplierSolicitationDetailsDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface SupplierSolicitationDetailsMapper extends ToDtoMapper<SupplierSolicitation, CgSupplierSolicitationDetailsDto>{
}
