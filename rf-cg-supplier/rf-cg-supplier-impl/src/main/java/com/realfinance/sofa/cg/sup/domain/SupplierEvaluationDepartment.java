package com.realfinance.sofa.cg.sup.domain;

import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.util.List;

/**
 * 供应商评估部门
 */
@Entity
@Table(name = "CG_SUP_SUPPLIER_EVALUATION_DEPARTMENT")
public class SupplierEvaluationDepartment extends BaseEntity implements IEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 制单人部门
     */
    @Column(nullable = false)
    private Integer departmentIdSub;

    /**
     *权重
     */
    @Column(nullable = false)
    private Integer weight;

    @ManyToOne
    @JoinColumn(name = "supplierEvaluationProcessMng_id", updatable = false)
    private SupplierEvaluationProcessMng supplierEvaluationProcessMng;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "supplierEvaluationDepartment_id")
    private List<SupplierEvaluator> supplierEvaluators;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDepartmentIdSub() {
        return departmentIdSub;
    }

    public void setDepartmentIdSub(Integer departmentIdSub) {
        this.departmentIdSub = departmentIdSub;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public SupplierEvaluationProcessMng getSupplierEvaluationProcessMng() {
        return supplierEvaluationProcessMng;
    }

    public void setSupplierEvaluationProcessMng(SupplierEvaluationProcessMng supplierEvaluationProcessMng) {
        this.supplierEvaluationProcessMng = supplierEvaluationProcessMng;
    }

    public List<SupplierEvaluator> getSupplierEvaluators() {
        return supplierEvaluators;
    }

    public void setSupplierEvaluators(List<SupplierEvaluator> supplierEvaluators) {
        this.supplierEvaluators = supplierEvaluators;
    }
}

