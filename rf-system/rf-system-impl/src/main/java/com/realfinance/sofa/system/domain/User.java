package com.realfinance.sofa.system.domain;

import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户
 */
@Entity
@Table(name = "SYSTEM_USER",
        indexes = {
                @Index(columnList = "tenant_id,username", unique = true),
                @Index(columnList = "email"),
                @Index(columnList = "mobile")
        })
@NamedEntityGraph(name = "User{tenant,department}",
        attributeNodes = {
                @NamedAttributeNode("tenant"),
                @NamedAttributeNode("department")
        })
@NamedEntityGraph(name = "User{tenant,department,roles,roleGroups{roles}}",
        attributeNodes = {
                @NamedAttributeNode("tenant"),
                @NamedAttributeNode("department"),
                @NamedAttributeNode("roles"),
                @NamedAttributeNode("roleGroups")
        },
        subgraphs = {
                @NamedSubgraph(name = "roleGroups",
                        attributeNodes = {
                                @NamedAttributeNode("roles"),
                        })
        })
@NamedEntityGraph(name = "User{roles,roleGroups{roles}}",
        attributeNodes = {
                @NamedAttributeNode("roles"),
                @NamedAttributeNode("roleGroups")
        },
        subgraphs = {
                @NamedSubgraph(name = "roleGroups",
                        attributeNodes = {
                                @NamedAttributeNode("roles"),
                        })
        })
public class User extends BaseEntity implements IEntity<Integer> {

    public User() {
        this.roles = new HashSet<>();
        this.roleGroups = new HashSet<>();
    }

    @Version
    private Long v;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户名
     */
    @Column(nullable = false)
    private String username;

    @NotNull
    @Column(nullable = false)
    // TODO 可新增一个密码最后修改时间来提示用户定期修改密码
    private String password;

    /**
     * 真实姓名
     */
    private String realname;

    /**
     * 邮箱
     */
    @Email
    private String email;

    /**
     * 手机号码
     */
//    @Pattern(regexp = "(?:0|86|\\+86)?1[3456789]\\d{9}")
    @Column(length = 15)
    private String mobile;

    /**
     * 分类 （暂时没用）
     */
    private String classification;

    /**
     * 是否可用
     */
    @Column(nullable = false)
    private Boolean enabled;

    /**
     * 所属租户
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false, updatable = false)
    private Tenant tenant;

    /**
     * 部门
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    /**
     * 角色
     */
    @ManyToMany
    @JoinTable(name = "SYSTEM_USER_ROLE",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private Set<Role> roles;

    /**
     * 角色组
     */
    @ManyToMany
    @JoinTable(name = "SYSTEM_USER_ROLE_GROUP",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_group_id", referencedColumnName = "id")})
    private Set<RoleGroup> roleGroups;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<RoleGroup> getRoleGroups() {
        return roleGroups;
    }

    public void setRoleGroups(Set<RoleGroup> roleGroups) {
        this.roleGroups = roleGroups;
    }

    public Long getV() {
        return v;
    }

    public void setV(Long v) {
        this.v = v;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
