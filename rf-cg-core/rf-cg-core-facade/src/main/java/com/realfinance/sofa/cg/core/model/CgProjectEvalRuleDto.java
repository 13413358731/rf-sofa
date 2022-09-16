package com.realfinance.sofa.cg.core.model;

import java.math.BigDecimal;
import java.util.List;

public class CgProjectEvalRuleDto {
    protected Integer id;

    /**
     * 名字
     */
    protected String name;

    /**
     * 子名字
     */
    protected String subName;

    protected String subCode;

    /**
     * 分值
     */
    protected BigDecimal weight;

    /**
     * 基准价
     */
    protected BigDecimal standardPrice;

    /**
     * 计算方法
     */
    private String evalFormula;

    /**
     * 计算方法名称
     */
    private String evalFormulaName;

    /**
     * 备注
     */
    protected String note;

    private List<CgProjectEvalDto> projectEvals;

    public List<CgProjectEvalDto> getProjectEvals() {
        return projectEvals;
    }

    public void setProjectEvals(List<CgProjectEvalDto> projectEvals) {
        this.projectEvals = projectEvals;
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

    public String getEvalFormula() {
        return evalFormula;
    }

    public void setEvalFormula(String evalFormula) {
        this.evalFormula = evalFormula;
    }

    public String getEvalFormulaName() {
        return evalFormulaName;
    }

    public void setEvalFormulaName(String evalFormulaName) {
        this.evalFormulaName = evalFormulaName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
