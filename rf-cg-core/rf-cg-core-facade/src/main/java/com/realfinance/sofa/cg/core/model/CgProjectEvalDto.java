package com.realfinance.sofa.cg.core.model;

import java.math.BigDecimal;
import java.util.Set;

public class CgProjectEvalDto {

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
    protected Set<CgProjectEvalRuleDto> projectEvalRules;

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

    public Set<CgProjectEvalRuleDto> getProjectEvalRules() {
        return projectEvalRules;
    }

    public void setProjectEvalRules(Set<CgProjectEvalRuleDto> projectEvalRules) {
        this.projectEvalRules = projectEvalRules;
    }
}
