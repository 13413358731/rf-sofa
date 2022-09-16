package com.realfinance.sofa.cg.sup.domain;

import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * 标签类型
 */
@Entity
@Table(name = "CG_SUP_SUPPLIER_LABEL_TYPE")
public class SupplierLabelType extends BaseEntity implements IEntity<Integer> {
    /**
     * 法人（租户）
     */
    @Column(nullable = false, updatable = false)
    private String tenantId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 显示名称
     */
    @Column(nullable = false, length = 20)
    private String name;

    @OneToMany(mappedBy = "type")
    private Set<SupplierLabel> labels;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<SupplierLabel> getLabels() {
        return labels;
    }

    public void setLabels(Set<SupplierLabel> labels) {
        this.labels = labels;
    }
}
