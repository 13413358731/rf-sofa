package com.realfinance.sofa.system.model;

import java.util.List;

/**
 * 文本模板
 */
public class TextTemplateSaveDto {

    private Integer id;

    /**
     * 类型
     */
    private String type;

    /**
     * 名称
     */
    private String name;

    /**
     * 可以使用的部门
     */
    private List<Integer> departmentScope;

    /**
     * 可以使用的用户
     */
    private List<Integer> userScope;

    /**
     * 可以使用的角色
     */
    private List<Integer> roleScope;

    private String text;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
