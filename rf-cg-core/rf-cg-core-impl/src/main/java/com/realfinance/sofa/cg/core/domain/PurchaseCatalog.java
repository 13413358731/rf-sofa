package com.realfinance.sofa.cg.core.domain;

import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

/**
 * 采购目录
 */
@Entity
@Table(name = "CG_CORE_PURCHASE_CATALOG", indexes = {
        @Index(columnList = "tenantId,code", unique = true)
})
public class PurchaseCatalog extends BaseEntity implements IEntity<Integer> {

    @Version
    private Long v;

    /**
     * 法人ID
     */
    @Column(nullable = false)
    private String tenantId;

    @Column(nullable = false)
    private Integer departmentId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private PurchaseCatalog parent;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    private Set<PurchaseCatalog> children;

    /**
     * 编码
     */
    @Column(nullable = false)
    private String code;

    /**
     * 名称
     */
    @Column(nullable = false)
    private String name;

    /**
     * 子页数
     */
    @Column(nullable = false)
    private Integer leafCount;

    /**
     * 集中采购限额
     */
    @Column
    private BigDecimal centralizedPurchasingLimit;

    @Column(nullable = false)
    private Boolean enabled;

    /**
     * 项目分类
     */
    @Column
    private ProjectCategory projectCategory;

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

    public PurchaseCatalog getParent() {
        return parent;
    }

    public void setParent(PurchaseCatalog parent) {
        this.parent = parent;
    }

    public Set<PurchaseCatalog> getChildren() {
        return children;
    }

    public void setChildren(Set<PurchaseCatalog> children) {
        this.children = children;
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

    public Integer getLeafCount() {
        return leafCount;
    }

    public void setLeafCount(Integer leafCount) {
        this.leafCount = leafCount;
    }

    public BigDecimal getCentralizedPurchasingLimit() {
        return centralizedPurchasingLimit;
    }

    public void setCentralizedPurchasingLimit(BigDecimal centralizedPurchasingLimit) {
        this.centralizedPurchasingLimit = centralizedPurchasingLimit;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public ProjectCategory getProjectCategory() {
        return projectCategory;
    }

    public void setProjectCategory(ProjectCategory projectCategory) {
        this.projectCategory = projectCategory;
    }
}
