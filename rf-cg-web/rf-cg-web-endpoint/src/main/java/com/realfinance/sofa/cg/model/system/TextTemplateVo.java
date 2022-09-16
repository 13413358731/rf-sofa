package com.realfinance.sofa.cg.model.system;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.realfinance.sofa.cg.model.BaseVo;
import com.realfinance.sofa.common.model.IdentityObject;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "文本模板")
public class TextTemplateVo extends BaseVo implements IdentityObject<Integer> {

    /**
     * 部门
     */
    private DepartmentVo department;

    private Integer id;

    /**
     * 类型
     */
    private String type;

    /**
     * 模板名称
     */
    private String name;

    /**
     * 可以使用的部门
     */
    private List<DepartmentVo> departmentScope;

    /**
     * 可以使用的用户
     */
    private List<UserVo> userScope;

    /**
     * 可以使用的角色
     */
    private List<RoleVo> roleScope;

    /**
     * 模板内容
     */
    private String text;

    public DepartmentVo getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentVo department) {
        this.department = department;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
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

    public List<DepartmentVo> getDepartmentScope() {
        return departmentScope;
    }

    public void setDepartmentScope(List<DepartmentVo> departmentScope) {
        this.departmentScope = departmentScope;
    }

    public List<UserVo> getUserScope() {
        return userScope;
    }

    public void setUserScope(List<UserVo> userScope) {
        this.userScope = userScope;
    }

    public List<RoleVo> getRoleScope() {
        return roleScope;
    }

    public void setRoleScope(List<RoleVo> roleScope) {
        this.roleScope = roleScope;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
