package com.realfinance.sofa.cg.core.model;

import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

public class CgGradeSupDto extends BaseDto {
    private Integer id;

    /**
     * 大项名称
     */
    private String name;

    /**
     * 细项名称
     */
    private String subName;

    protected String subCode;

    /**
     * 推荐供应商
     */
    private Integer supplier;

    /**
     * 评审会专家
     */
    private Integer expert;

    /**
     * 评分标准
     */
    protected String note;

    /**
     * 得分
     */
    private Integer score;

    /**
     * 权重
     */
    private BigDecimal weight;

    /**
     * 方案Id
     */
    private Integer projId;

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

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    public void setSubName(String subName) {
        this.subName = subName;
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

    public Integer getProjId() {
        return projId;
    }

    public void setProjId(Integer projId) {
        this.projId = projId;
    }
}
