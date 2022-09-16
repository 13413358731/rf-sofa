package com.realfinance.sofa.cg.sup.domain;

import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;

/**
 * 供应商评估部门
 */
@Entity
@Table(name = "CG_SUP_SUPPLIER_EVALUATOR")
public class SupplierEvaluator extends BaseEntity implements IEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 姓名
     */
    @Column(nullable = false)
    private String realname;

    /**
     * 人员编码
     */
    @Column(nullable = false)
    private String username;

    /**
     * 部门
     */
    @Column(nullable = false)
    private Integer department;

    /**
     * 联系电话
     */
    @Column(nullable = false)
    private String mobile;

    @ManyToOne
    @JoinColumn(name = "supplierEvaluationDepartment_id", updatable = false)
    private SupplierEvaluationDepartment supplierEvaluationDepartment;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getDepartment() {
        return department;
    }

    public void setDepartment(Integer department) {
        this.department = department;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public SupplierEvaluationDepartment getSupplierEvaluationDepartment() {
        return supplierEvaluationDepartment;
    }

    public void setSupplierEvaluationDepartment(SupplierEvaluationDepartment supplierEvaluationDepartment) {
        this.supplierEvaluationDepartment = supplierEvaluationDepartment;
    }
}

