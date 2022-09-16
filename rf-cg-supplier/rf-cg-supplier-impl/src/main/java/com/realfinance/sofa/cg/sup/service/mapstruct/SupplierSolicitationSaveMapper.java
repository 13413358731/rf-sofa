package com.realfinance.sofa.cg.sup.service.mapstruct;

import com.realfinance.sofa.cg.sup.domain.SupplierSolicitation;
import com.realfinance.sofa.cg.sup.model.CgSupplierSolicitationSaveDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface SupplierSolicitationSaveMapper extends  ToEntityMapper<SupplierSolicitation, CgSupplierSolicitationSaveDto>{
}
