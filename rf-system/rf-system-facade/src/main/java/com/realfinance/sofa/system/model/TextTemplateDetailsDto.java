package com.realfinance.sofa.system.model;

import java.util.List;

public class TextTemplateDetailsDto extends TextTemplateDto {

    private String text;

    private List<Integer> departmentScope;

    private List<Integer> userScope;

    private List<Integer> roleScope;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Integer> getDepartmentScope() {
        return departmentScope;
    }

    public void setDepartmentScope(List<Integer> departmentScope) {
        this.departmentScope = departmentScope;
    }

    public List<Integer> getUserScope() {
        return userScope;
    }

    public void setUserScope(List<Integer> userScope) {
        this.userScope = userScope;
    }

    public List<Integer> getRoleScope() {
        return roleScope;
    }

    public void setRoleScope(List<Integer> roleScope) {
        this.roleScope = roleScope;
    }
}
