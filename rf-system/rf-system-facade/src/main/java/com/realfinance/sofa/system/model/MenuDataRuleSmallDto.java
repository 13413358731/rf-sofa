package com.realfinance.sofa.system.model;

import java.io.Serializable;

public class MenuDataRuleSmallDto implements Serializable {
    private Integer id;
    private String ruleAttribute;
    private String ruleConditions;
    private String ruleValue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRuleAttribute() {
        return ruleAttribute;
    }

    public void setRuleAttribute(String ruleAttribute) {
        this.ruleAttribute = ruleAttribute;
    }

    public String getRuleConditions() {
        return ruleConditions;
    }

    public void setRuleConditions(String ruleConditions) {
        this.ruleConditions = ruleConditions;
    }

    public String getRuleValue() {
        return ruleValue;
    }

    public void setRuleValue(String ruleValue) {
        this.ruleValue = ruleValue;
    }

    @Override
    public String toString() {
        return "MenuDataRuleSmallDto{" +
                "id=" + id +
                ", ruleAttribute='" + ruleAttribute + '\'' +
                ", ruleConditions='" + ruleConditions + '\'' +
                ", ruleValue='" + ruleValue + '\'' +
                '}';
    }
}
