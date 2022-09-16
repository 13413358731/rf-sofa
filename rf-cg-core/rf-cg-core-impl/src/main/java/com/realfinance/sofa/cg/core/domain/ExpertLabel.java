package com.realfinance.sofa.cg.core.domain;

import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * 专家标签库
 */
@Entity
@Table(name = "CG_CORE_EXPERT_LABEL")
public class ExpertLabel extends BaseEntity implements IEntity<Integer> {

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
    private ExpertLabel parent;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    private Set<ExpertLabel> children;

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
    @JoinColumn(name = "expert_label_type_id", nullable = false)
    private ExpertLabelType type;

    /**
     * 子页数
     */
    @Column(nullable = false)
    private Integer leafCount;

    @ManyToMany
    @JoinTable(name = "CG_CORE_EXPERT_EXPERT_LABEL",
            joinColumns = {@JoinColumn(name = "expert_label_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "expert_id", referencedColumnName = "id")})
    private Set<Expert> experts;

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

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Expert> getExperts() {
        return experts;
    }

    public void setExperts(Set<Expert> experts) {
        this.experts = experts;
    }

    public ExpertLabel getParent() {
        return parent;
    }

    public void setParent(ExpertLabel parent) {
        this.parent = parent;
    }

    public Set<ExpertLabel> getChildren() {
        return children;
    }

    public void setChildren(Set<ExpertLabel> children) {
        this.children = children;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ExpertLabelType getType() {
        return type;
    }

    public void setType(ExpertLabelType type) {
        this.type = type;
    }

    public Integer getLeafCount() {
        return leafCount;
    }

    public void setLeafCount(Integer leafCount) {
        this.leafCount = leafCount;
    }
}
