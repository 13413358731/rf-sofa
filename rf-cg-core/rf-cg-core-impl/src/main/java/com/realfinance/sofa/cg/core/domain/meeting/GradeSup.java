package com.realfinance.sofa.cg.core.domain.meeting;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 专家打分
 */
@Entity
@Table(name = "CG_CORE_GRADE_SUP")
public class GradeSup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 大项名称
     */
    @Column()
    private String name;

    /**
     * 细项名称
     */
    @Column()
    private String subName;

    /**
     * 子编码
     */
    @Column(nullable = false)
    protected String subCode;

    /**
     * 推荐供应商
     */
    @Column()
    private Integer supplier;

    /**
     * 评审会专家
     */
    @Column()
    private Integer expert;

    /**
     * 评分标准
     */
    @Column
    protected String note;

    /**
     * 得分
     */
    @Column
    private Integer score;

    /**
     * 权重
     */
    @Column
    private BigDecimal weight;

    /**
     * 评分细则Id
     */
    @Column
    private Integer ProjEvalRuleId;

    /**
     * 方案Id
     */
    @Column
    private Integer projId;

    @ManyToOne
    @JoinColumn(name = "grade_sup_sum_id", updatable = false)
    private GradeSupSum gradeSupSum;

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

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    public Integer getSupplier() {
        return supplier;
    }

    public void setSupplier(Integer supplier) {
        this.supplier = supplier;
    }

    public Integer getExpert() {
        return expert;
    }

    public void setExpert(Integer expert) {
        this.expert = expert;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public Integer getProjEvalRuleId() {
        return ProjEvalRuleId;
    }

    public void setProjEvalRuleId(Integer projEvalRuleId) {
        ProjEvalRuleId = projEvalRuleId;
    }

    public Integer getProjId() {
        return projId;
    }

    public void setProjId(Integer projId) {
        this.projId = projId;
    }

    public GradeSupSum getGradeSupSum() {
        return gradeSupSum;
    }

    public void setGradeSupSum(GradeSupSum gradeSupSum) {
        this.gradeSupSum = gradeSupSum;
    }

}
