package com.realfinance.sofa.cg.core.domain.proj;

import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 评分细则
 */
@MappedSuperclass
public abstract class BaseProjectEvalRule implements IEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    /**
     * 名字
     */
    @Column(nullable = false)
    protected String name;

    /**
     * 子名字
     */
    @Column(nullable = false)
    protected String subName;

    /**
     * 子编码
     */
    @Column()
    protected String subCode;

    /**
     * 分值
     */
    @Column(nullable = false)
    protected BigDecimal weight;

    /**
     * 基准价
     */
    protected BigDecimal standardPrice;

    protected String evalFormulaName;

    /**
     * 计算方法
     */
    protected String evalFormula;

    /**
     * 备注
     */
    @Column
    protected String note;

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

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getStandardPrice() {
        return standardPrice;
    }

    public void setStandardPrice(BigDecimal standardPrice) {
        this.standardPrice = standardPrice;
    }

    public String getEvalFormulaName() {
        return evalFormulaName;
    }

    public void setEvalFormulaName(String evalFormulaName) {
        this.evalFormulaName = evalFormulaName;
    }

    public String getEvalFormula() {
        return evalFormula;
    }

    public void setEvalFormula(String evalFormula) {
        this.evalFormula = evalFormula;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }
}
