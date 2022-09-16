package com.realfinance.sofa.cg.sup.domain;

import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

/**
 * 供应商评估主表
 */
@Entity
@Table(name = "CG_SUP_SUPPLIER_EVALUATION_MAIN")
public class SupplierEvaluationMain extends BaseEntity implements IEntity<Integer> {

    @Version
    private Long v;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 法人ID
     */
    @Column(nullable = false)
    private String tenantId;

    /**
     * 制单人部门
     */
    @Column(nullable = false)
    private Integer departmentId;

    /**
     *评估发起编号
     */
    @Column(nullable = false)
    private String evaluationStartNo;

    /**
     *评估发起名称
     */
    @Column(nullable = false)
    private String evaluationStartName;

    /**
     *评估发起主键
     */
//    @Column(nullable = false)
//    private Integer supplierEvaluationProcessMng;

    /**
     *评估表编号
     */
    @Column(nullable = false)
    private Integer evaluationSheetNo;

    /**
     *评估表名称
     */
    @Column(nullable = false)
    private String evaluationSheetName;

    /**
     *评估开始日期
     */
    @Column(nullable = false)
    private LocalDate evaluationStartDate;

    /**
     *评估结束日期
     */
    @Column(nullable = false)
    private LocalDate evaluationEndDate;

    /**
     * 评估部门
     */
    @Column(nullable = false)
    private Integer supplierEvaluationDepartment;

    /**
     *评估人
     */
    @Column(nullable = false)
    private Integer evaluator;

    /**
     *发布日期
     */
    @Column(nullable = false)
    private LocalDate releaseDate;

    /**
     *完成状态
     */
    @Column(nullable = false)
    private Integer status;

    /**
     *完成日期
     */
    @Column(nullable = false)
    private LocalDate finishDate;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "supplierEvaluationMain_id")
    private List<SupplierEvaluationSub> supplierEvaluationSubs;

    public Long getV() {
        return v;
    }

    public void setV(Long v) {
        this.v = v;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
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

    public String getEvaluationStartNo() {
        return evaluationStartNo;
    }

    public void setEvaluationStartNo(String evaluationStartNo) {
        this.evaluationStartNo = evaluationStartNo;
    }

    public String getEvaluationStartName() {
        return evaluationStartName;
    }

    public void setEvaluationStartName(String evaluationStartName) {
        this.evaluationStartName = evaluationStartName;
    }

    public Integer getEvaluationSheetNo() {
        return evaluationSheetNo;
    }

    public void setEvaluationSheetNo(Integer evaluationSheetNo) {
        this.evaluationSheetNo = evaluationSheetNo;
    }

    public String getEvaluationSheetName() {
        return evaluationSheetName;
    }

    public void setEvaluationSheetName(String evaluationSheetName) {
        this.evaluationSheetName = evaluationSheetName;
    }

    public LocalDate getEvaluationStartDate() {
        return evaluationStartDate;
    }

    public void setEvaluationStartDate(LocalDate evaluationStartDate) {
        this.evaluationStartDate = evaluationStartDate;
    }

    public LocalDate getEvaluationEndDate() {
        return evaluationEndDate;
    }

    public void setEvaluationEndDate(LocalDate evaluationEndDate) {
        this.evaluationEndDate = evaluationEndDate;
    }

    public Integer getSupplierEvaluationDepartment() {
        return supplierEvaluationDepartment;
    }

    public void setSupplierEvaluationDepartment(Integer supplierEvaluationDepartment) {
        this.supplierEvaluationDepartment = supplierEvaluationDepartment;
    }

    public Integer getEvaluator() {
        return evaluator;
    }

    public void setEvaluator(Integer evaluator) {
        this.evaluator = evaluator;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }

    public List<SupplierEvaluationSub> getSupplierEvaluationSubs() {
        return supplierEvaluationSubs;
    }

    public void setSupplierEvaluationSubs(List<SupplierEvaluationSub> supplierEvaluationSubs) {
        this.supplierEvaluationSubs = supplierEvaluationSubs;
    }
}

