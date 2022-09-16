package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.DrawExpertRule;
import com.realfinance.sofa.cg.core.model.CgDrawExpertRuleDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface DrawExpertRuleSaveMapper extends ToEntityMapper<DrawExpertRule, CgDrawExpertRuleDto> {
}
