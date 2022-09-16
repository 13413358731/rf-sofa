package com.realfinance.sofa.system.domain;

import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 部门
 */
@Entity
@Table(name = "SYSTEM_DEPARTMENT",
        indexes = {
                @Index(columnList = "type"),
                @Index(columnList = "parent_id"),
                @Index(columnList = "tenant_id,code", unique = true)
        })
@NamedEntityGraph(name = "Department{parent,tenant}",
        attributeNodes = {
                @NamedAttributeNode("parent"),
                @NamedAttributeNode("tenant"),
        })
public class Department extends BaseEntity implements IEntity<Integer> {

    public enum Type {
        // 一级部门
        FIRST_LEVEL,
        // 子部门
        SUB,
    }

    public Department() {
        this.users = new HashSet<>();
        this.children = new HashSet<>();
    }

    @Version
    private Long v;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 父节点
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Department parent;

    /**
     * 子节点
     */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id", updatable = false)
    private Set<Department> children;

    /**
     * 租户
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false, updatable = false)
    private Tenant tenant;

    /**
     * 编码
     */
    @Column(nullable = false, length = 20)
    private String code;

    /**
     * 编码路径
     */
    private String codePath;

    /**
     * 名称
     */
    @Column(nullable = false)
    private String name;

    /**
     * 名称路径
     */
    private String namePath;

    /**
     * 部门类型（一级部门、子部门）
     */
    @Column(nullable = false)
    @Enumerated
    private Type type;

    /**
     * 子节点数
     */
    @Column(nullable = false)
    private Integer leafCount;

    /**
     * 排序
     */
    @Column(nullable = false)
    private Integer sort;

    /**
     * 部门类别
     */
    private String category;

    /**
     * 是否可用
     */
    @Column(nullable = false)
    private Boolean enabled;

    /**
     * 部门下的用户
     */
    @OneToMany(mappedBy = "department")
    private Set<User> users;

    /**
     * 重新生成路径，返回Path是否有变化
     * @return 是否变化
     */
    public boolean resetPath() {
        boolean changed = false;
        Department parent = this.getParent();

        String parentCodePath = parent == null ? "" : parent.getCodePath();
        String codePath = parentCodePath + "/" + getCode();
        changed |= codePath.equals(getCodePath());
        setCodePath(codePath);

        String parentNamePath = parent == null ? "" : parent.getNamePath();
        String namePath = parentNamePath + "/" + getName();
        changed |= namePath.equals(getNamePath());
        setNamePath(namePath);

        return changed;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Department getParent() {
        return parent;
    }

    public void setParent(Department parent) {
        this.parent = parent;
    }

    public Set<Department> getChildren() {
        return children;
    }

    public void setChildren(Set<Department> children) {
        this.children = children;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodePath() {
        return codePath;
    }

    public void setCodePath(String codePath) {
        this.codePath = codePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNamePath() {
        return namePath;
    }

    public void setNamePath(String namePath) {
        this.namePath = namePath;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Integer getLeafCount() {
        return leafCount;
    }

    public void setLeafCount(Integer leafCount) {
        this.leafCount = leafCount;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Long getV() {
        return v;
    }

    public void setV(Long v) {
        this.v = v;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
