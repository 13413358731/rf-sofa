package com.realfinance.sofa.system.domain;

import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 菜单数据规则
 */
@Entity
@Table(name = "SYSTEM_MENU_DATA_RULE", indexes = {
        @Index(columnList = "menu_id,ruleName", unique = true)
})
@NamedEntityGraph(name = "MenuDataRule{menu}",
        attributeNodes = {
                @NamedAttributeNode("menu")
        })
public class MenuDataRule extends BaseEntity implements IEntity<Integer> {

    public MenuDataRule() {
        this.roles = new HashSet<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 菜单
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false, updatable = false)
    private Menu menu;

    /**
     * 规则名称
     */
    @Column(nullable = false)
    private String ruleName;

    /**
     * 规则目标属性
     */
    private String ruleAttribute;

    /**
     * 规则条件
     */
    @Column(nullable = false)
    private String ruleConditions;

    /**
     * 规则值
     */
    @Column(nullable = false)
    private String ruleValue;

    /**
     * 是否可用
     */
    @Column(nullable = false)
    private Boolean enabled;

    /**
     * 使用此数据规则的角色
     */
    @ManyToMany
    @JoinTable(name = "SYSTEM_ROLE_MENU_DATA_RULE",
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
            joinColumns = {@JoinColumn(name = "menu_data_rule_id", referencedColumnName = "id")})
    private Set<Role> roles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
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

    public void setRuleAttribute(String ruleColumn) {
        this.ruleAttribute = ruleColumn;
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
