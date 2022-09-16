package com.realfinance.sofa.cg.sup.service.mapstruct;

import com.realfinance.sofa.cg.sup.domain.SolicitationEnroll;
import com.realfinance.sofa.cg.sup.model.SolicitationEnrollDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface SolicitationEnrollMapper extends  ToDtoMapper<SolicitationEnroll, SolicitationEnrollDto>{
}
