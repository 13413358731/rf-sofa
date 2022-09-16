package com.realfinance.sofa.system.model;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class MenuDataRuleSaveDto implements Serializable {
    private Integer id;

    @NotNull
    private Integer menu;

    @NotNull
    private String ruleName;

    private String ruleAttribute;
    @NotNull
    private String ruleConditions;
    @NotNull
    private String ruleValue;
    @NotNull
    private Boolean enabled;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMenu() {
        return menu;
    }

    public void setMenu(Integer menu) {
        this.menu = menu;
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

    @Override
    public String toString() {
        return "MenuDataRuleSaveDto{" +
                "id=" + id +
                ", menu=" + menu +
                ", ruleName='" + ruleName + '\'' +
                ", ruleAttribute='" + ruleAttribute + '\'' +
                ", ruleConditions='" + ruleConditions + '\'' +
                ", ruleValue='" + ruleValue + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
