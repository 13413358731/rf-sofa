package com.realfinance.sofa.system.domain;

import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.util.List;

/**
 * 文本模板
 */
@Entity
@Table(name = "SYSTEM_TEXT_TEMP")
public class TextTemplate extends BaseEntity implements IEntity<Integer> {

    /**
     * 法人ID
     */
    @Column(nullable = false)
    private String tenantId;

    /**
     * 部门ID
     */
    @Column(nullable = false)
    private Integer departmentId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 类型
     */
    @Column(nullable = false, length = 20)
    private String type;

    /**
     * 模板名称
     */
    @Column(nullable = false)
    private String name;

    /**
     * 可以使用的部门
     */
    @Column
    @ElementCollection
    @CollectionTable(name="SYSTEM_TEXT_TEMP_DEPT_SCOPE")
    private List<Integer> departmentScope;

    /**
     * 可以使用的用户
     */
    @Column
    @ElementCollection
    @CollectionTable(name="SYSTEM_TEXT_TEMP_USER_SCOPE")
    private List<Integer> userScope;

    /**
     * 可以使用的角色
     */
    @Column
    @ElementCollection
    @CollectionTable(name="SYSTEM_TEXT_TEMP_ROLE_SCOPE")
    private List<Integer> roleScope;

    /**
     * 模板内容
     */
    @Lob
    @Column
    @Basic(fetch = FetchType.LAZY)
    private String text;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
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
