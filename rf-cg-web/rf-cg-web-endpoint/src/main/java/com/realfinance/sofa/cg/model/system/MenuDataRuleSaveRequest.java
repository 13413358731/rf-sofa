package com.realfinance.sofa.cg.model.system;

import com.realfinance.sofa.common.model.ReferenceObject;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;

@Schema(description = "保存菜单数据规则请求对象")
public class MenuDataRuleSaveRequest {
    @Schema(description = "ID")
    private Integer id;
    @NotNull
    @Schema(description = "菜单")
    private ReferenceObject<Integer> menu;
    @NotNull
    @Schema(description = "规则名称")
    private String ruleName;
    @Schema(description = "规则属性")
    private String ruleAttribute;
    @NotNull
    @Schema(description = "规则条件")
    private String ruleConditions;
    @NotNull
    @Schema(description = "规则值")
    private String ruleValue;
    @NotNull
    @Schema(description = "是否启用")
    private Boolean enabled;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ReferenceObject<Integer> getMenu() {
        return menu;
    }

    public void setMenu(ReferenceObject<Integer> menu) {
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
}
