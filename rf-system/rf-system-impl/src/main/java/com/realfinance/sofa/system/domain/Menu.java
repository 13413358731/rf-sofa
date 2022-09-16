package com.realfinance.sofa.system.domain;

import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "SYSTEM_MENU", indexes = {
        //@Index(columnList = "type")
})
@NamedEntityGraph(name = "Menu{parent}",
        attributeNodes = {
                @NamedAttributeNode("parent"),
        })
public class Menu extends BaseEntity implements IEntity<Integer> {

    public enum Type {
        // 一级菜单
        FIRST_LEVEL,
        // 子菜单
        SUB,
        // 按钮
        BUTTON
    }

    public Menu() {
        this.menuDataRules = new HashSet<>();
        this.roles = new HashSet<>();
        this.children = new HashSet<>();
    }

    @Version
    private Long v;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Menu parent;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    private Set<Menu> children;

    /**
     * 编码
     */
    @Column(nullable = false, unique = true)
    private String code;

    /**
     * 编码路径
     */
    @Column
    private String codePath;


    /**
     * 菜单名称
     */
    @Column(nullable = false)
    private String name;

    /**
     * 名称路径
     */
    @Column(nullable = false)
    private String namePath;

    /**
     * 菜单类型
     */
    @Column(nullable = false)
    @Enumerated
    private Type type;

    /**
     * 子页数
     */
    @Column(nullable = false)
    private Integer leafCount;

    /**
     * 排序
     */
    @Column(nullable = false)
    private Integer sort;
    // 更多扩展属性前端使用

    /**
     * 是否隐藏
     */
    @Column(nullable = false)
    private Boolean hidden;

    private String icon;
    private String component;
    private String componentName;
    private String url;
    @Column(nullable = false)
    private Boolean keepAlive;
    //
    /**
     * 菜单下是否有数据规则
     * 当{@link com.realfinance.sofa.system.domain.Menu#menuDataRules}的size>0时为true
     */
    @Column(nullable = false)
    private Boolean hasMenuDataRule;

    /**
     * 数据规则
     */
    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MenuDataRule> menuDataRules;

    /**
     * 关联角色
     */
    @ManyToMany
    @JoinTable(name = "SYSTEM_ROLE_MENU",
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
            joinColumns = {@JoinColumn(name = "menu_id", referencedColumnName = "id")})
    private Set<Role> roles;

    /**
     * 重新生成路径，返回Path是否有变化
     * @return 是否变化
     */
    public boolean resetPath() {
        boolean changed = false;
        Menu parent = this.getParent();

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

    /**
     * 重置是否含有数据规则
     * {@link Menu#hasMenuDataRule}
     * @return 是否变化
     */
    public boolean resetHasMenuDataRule() {
        Boolean hasMenuDataRule = getHasMenuDataRule();
        Set<MenuDataRule> menuDataRules = getMenuDataRules();
        boolean empty = menuDataRules.isEmpty();
        setHasMenuDataRule(!empty);
        return hasMenuDataRule == empty;
    }

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

    public Menu getParent() {
        return parent;
    }

    public void setParent(Menu parent) {
        this.parent = parent;
    }

    public Set<Menu> getChildren() {
        return children;
    }

    public void setChildren(Set<Menu> children) {
        this.children = children;
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

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(Boolean keepAlive) {
        this.keepAlive = keepAlive;
    }

    public Boolean getHasMenuDataRule() {
        return hasMenuDataRule;
    }

    public void setHasMenuDataRule(Boolean hasMenuDataRule) {
        this.hasMenuDataRule = hasMenuDataRule;
    }

    public Set<MenuDataRule> getMenuDataRules() {
        return menuDataRules;
    }

    public void setMenuDataRules(Set<MenuDataRule> menuDataRules) {
        this.menuDataRules = menuDataRules;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
