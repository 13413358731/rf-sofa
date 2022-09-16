package com.realfinance.sofa.system.domain;

import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 角色组
 */
@Entity
@Table(name = "SYSTEM_ROLE_GROUP",
        indexes = {
                @Index(columnList = "tenant_id,code", unique = true)
        })
@NamedEntityGraph(name = "RoleGroup{tenant}",
        attributeNodes = {
                @NamedAttributeNode("tenant")
        })
public class RoleGroup extends BaseEntity implements IEntity<Integer> {

    public RoleGroup() {
        this.roles = new HashSet<>();
        this.users = new HashSet<>();
    }

    @Version
    private Long v;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 角色组编码
     */
    @Column(nullable = false)
    private String code;

    /**
     * 角色组名称
     */
    @Column(nullable = false)
    private String name;

    /**
     * 描述
     */
    private String note;

    @Column(nullable = false)
    private Boolean enabled;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false, updatable = false)
    private Tenant tenant;

    @ManyToMany
    @JoinTable(name = "SYSTEM_ROLE_ROLE_GROUP",
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
            joinColumns = {@JoinColumn(name = "role_group_id", referencedColumnName = "id")})
    private Set<Role> roles;

    @ManyToMany
    @JoinTable(name = "SYSTEM_USER_ROLE_GROUP",
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            joinColumns = {@JoinColumn(name = "role_group_id", referencedColumnName = "id")})
    private Set<User> users;

    public Long getV() {
        return v;
    }

    public void setV(Long v) {
        this.v = v;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
