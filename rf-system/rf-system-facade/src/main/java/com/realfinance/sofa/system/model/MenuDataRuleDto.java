package com.realfinance.sofa.system.model;

import java.io.Serializable;
import java.util.Objects;

public class MenuDataRuleDto extends BaseDto implements Serializable {
    private Integer id;

    private String ruleName;

    private String ruleAttribute;

    private String ruleConditions;

    private String ruleValue;

    private Boolean enabled;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuDataRuleDto that = (MenuDataRuleDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }


}
