package com.realfinance.sofa.cg.model.cg;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.realfinance.sofa.cg.core.model.CgProjectEvalRuleDto;
import com.realfinance.sofa.common.model.IEntity;
import com.realfinance.sofa.common.model.IdentityObject;

import java.math.BigDecimal;
import java.util.Set;

//@JsonInclude(JsonInclude.Include.NON_NULL)
public class CgProjectEvalVo {

    protected Integer id;

    /**
     * 评分分类
     */
    protected String category;

    /**
     * 权重
     */
    protected BigDecimal weight;

    /**
     * 评分细则
     */
    protected Set<CgProjectEvalRuleVo> projectEvalRules;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public Set<CgProjectEvalRuleVo> getProjectEvalRules() {
        return projectEvalRules;
    }

    public void setProjectEvalRules(Set<CgProjectEvalRuleVo> projectEvalRules) {
        this.projectEvalRules = projectEvalRules;
    }
}
