package com.realfinance.sofa.cg.core.model;

import java.util.List;
import java.util.Set;

public class CgDrawExpertDetailsDto extends CgDrawExpertDto {
    protected List<CgDrawExpertListDto> drawExpertLists;

    protected List<CgDrawExpertRuleDto> drawExpertRules;

    public List<CgDrawExpertListDto> getDrawExpertLists() {
        return drawExpertLists;
    }

    public void setDrawExpertLists(List<CgDrawExpertListDto> drawExpertLists) {
        this.drawExpertLists = drawExpertLists;
    }

    public List<CgDrawExpertRuleDto> getDrawExpertRules() {
        return drawExpertRules;
    }

    public void setDrawExpertRules(List<CgDrawExpertRuleDto> drawExpertRules) {
        this.drawExpertRules = drawExpertRules;
    }
}
