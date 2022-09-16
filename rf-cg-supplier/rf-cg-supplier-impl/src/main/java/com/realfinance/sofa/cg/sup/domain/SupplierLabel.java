package com.realfinance.sofa.cg.sup.domain;

import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * 标签库
 */
@Entity
@Table(name = "CG_SUP_SUPPLIER_LABEL")
public class SupplierLabel extends BaseEntity implements IEntity<Integer> {

    @Version
    private Long v;

    /**
     * 法人（租户）
     */
    @Column(nullable = false, updatable = false)
    private String tenantId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private SupplierLabel parent;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    private Set<SupplierLabel> children;

    /**
     * 名称
     */
    @Column(nullable = false)
    private String name;

    /**
     * 值
     */
    @Column(nullable = false, length = 32)
    private String value;

    /**
     * 类型
     */
    @ManyToOne
    @JoinColumn(name = "supplier_label_type_id", nullable = false)
    private SupplierLabelType type;


    /**
     * 子页数
     */
    @Column(nullable = false)
    private Integer leafCount;

    @ManyToMany
    @JoinTable(name = "CG_SUP_SUPPLIER_SUPPLIER_LABEL",
            joinColumns = {@JoinColumn(name = "supplier_label_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "supplier_id", referencedColumnName = "id")})
    private Set<Supplier> suppliers;

    public Long getV() {
        return v;
    }

    public void setV(Long v) {
        this.v = v;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SupplierLabel getParent() {
        return parent;
    }

    public void setParent(SupplierLabel parent) {
        this.parent = parent;
    }

    public Set<SupplierLabel> getChildren() {
        return children;
    }

    public void setChildren(Set<SupplierLabel> children) {
        this.children = children;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public SupplierLabelType getType() {
        return type;
    }

    public void setType(SupplierLabelType type) {
        this.type = type;
    }

    public Integer getLeafCount() {
        return leafCount;
    }

    public void setLeafCount(Integer leafCount) {
        this.leafCount = leafCount;
    }

    public Set<Supplier> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(Set<Supplier> suppliers) {
        this.suppliers = suppliers;
    }
}
