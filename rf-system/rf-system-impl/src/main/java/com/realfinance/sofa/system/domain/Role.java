package com.realfinance.sofa.system.domain;

import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 角色
 */
@Entity
@Table(name = "SYSTEM_ROLE",
        indexes = {
                @Index(columnList = "tenant_id,code", unique = true)
        })
@NamedEntityGraph(name = "Role{tenant}",
        attributeNodes = {
                @NamedAttributeNode("tenant")
        })
@NamedEntityGraph(name = "Role{menuDataRules}",
        attributeNodes = {
                @NamedAttributeNode("menuDataRules")
        })
public class Role extends BaseEntity implements IEntity<Integer> {

    public Role() {
        this.menuDataRules = new HashSet<>();
        this.users = new HashSet<>();
        this.menus = new HashSet<>();
        this.roleGroups = new HashSet<>();
    }

    @Version
    private Long v;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 编码
     */
    @Column(nullable = false, length = 20)
    private String code;

    /**
     * 名称
     */
    @Column(nullable = false)
    private String name;

    /**
     * 描述
     */
    private String note;
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
     * 菜单数据规则
     */
    @ManyToMany
    @JoinTable(name = "SYSTEM_ROLE_MENU_DATA_RULE",
            inverseJoinColumns = {@JoinColumn(name = "menu_data_rule_id", referencedColumnName = "id")},
            joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private Set<MenuDataRule> menuDataRules;

    /**
     * 拥有此角色的用户
     */
    @ManyToMany
    @JoinTable(name = "SYSTEM_USER_ROLE",
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private Set<User> users;

    /**
     * 此角色拥有的菜单
     */
    @ManyToMany
    @JoinTable(name = "SYSTEM_ROLE_MENU",
            joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "menu_id", referencedColumnName = "id")})
    private Set<Menu> menus;

    @ManyToMany
    @JoinTable(name = "SYSTEM_ROLE_ROLE_GROUP",
            joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_group_id", referencedColumnName = "id")})
    private Set<RoleGroup> roleGroups;

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

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Menu> getMenus() {
        return menus;
    }

    public void setMenus(Set<Menu> menus) {
        this.menus = menus;
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

    public Set<MenuDataRule> getMenuDataRules() {
        return menuDataRules;
    }

    public void setMenuDataRules(Set<MenuDataRule> menuDataRules) {
        this.menuDataRules = menuDataRules;
    }
}
