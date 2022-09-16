package com.realfinance.sofa.system.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class MenuDataRules implements Serializable {
    private String menuCode; // 菜单编码
    private Boolean hasMenuDataRule; // 菜单下是否有数据规则
    private Collection<RoleRules> roleRulesCollection; // 角色规则集合

    public MenuDataRules() {
        roleRulesCollection = new ArrayList<>();
    }

    public MenuDataRules(String menuCode, Boolean hasMenuDataRule) {
        this();
        this.menuCode = Objects.requireNonNull(menuCode);
        this.hasMenuDataRule = Objects.requireNonNull(hasMenuDataRule);
    }

    /**
     * 添加规则
     * @param roleRules
     */
    public void addRoleRules(RoleRules roleRules) {
        this.roleRulesCollection.add(roleRules);
    }

    /**
     * 优化，删除重复规则数据
     */
    public MenuDataRules optimize() {
        ArrayList<RoleRules> temp = new ArrayList<>(roleRulesCollection);
        //ArrayList<RoleRules> temp = new ArrayList<>();
        for (RoleRules e : roleRulesCollection) {
            temp.removeIf(next -> e != next && e.getRules().containsAll(next.getRules()));
            /*if(e.getRules()!=null&&e.getRules().size()!=0){
                temp.add(e);
            }*/
        }
        setRoleRulesCollection(temp);
        return this;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public Boolean getHasMenuDataRule() {
        return hasMenuDataRule;
    }

    public void setHasMenuDataRule(Boolean hasMenuDataRule) {
        this.hasMenuDataRule = hasMenuDataRule;
    }

    public Collection<RoleRules> getRoleRulesCollection() {
        return roleRulesCollection;
    }

    public void setRoleRulesCollection(Collection<RoleRules> roleRulesCollection) {
        this.roleRulesCollection = roleRulesCollection;
    }

    @Override
    public String toString() {
        return "MenuDataRules{" +
                "menuCode='" + menuCode + '\'' +
                ", hasMenuDataRule=" + hasMenuDataRule +
                ", roleRules=" + roleRulesCollection +
                '}';
    }

    public static class RoleRules implements Serializable {
        private String roleCode;
        private Collection<MenuDataRuleSmallDto> rules;

        public RoleRules() {
        }

        public RoleRules(String roleCode, Collection<MenuDataRuleSmallDto> rules) {
            this.roleCode = Objects.requireNonNull(roleCode);
            this.rules = Objects.requireNonNull(rules);
        }

        public String getRoleCode() {
            return roleCode;
        }

        public void setRoleCode(String roleCode) {
            this.roleCode = roleCode;
        }

        public Collection<MenuDataRuleSmallDto> getRules() {
            return rules;
        }

        public void setRules(Collection<MenuDataRuleSmallDto> rules) {
            this.rules = rules;
        }

        @Override
        public String toString() {
            return "Rule{" +
                    "roleCode='" + roleCode + '\'' +
                    ", rules=" + rules +
                    '}';
        }
    }
}
