package com.realfinance.sofa.cg.service.mapstruct;

import com.realfinance.sofa.cg.core.model.CgDrawExpertRuleDto;
import com.realfinance.sofa.cg.model.cg.CgDrawExpertRuleVo;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface CgDrawExpertRuleMapper {

    CgDrawExpertRuleVo toVo(CgDrawExpertRuleDto dto);

    CgDrawExpertRuleDto toSaveDto(CgDrawExpertRuleVo vo);
}
