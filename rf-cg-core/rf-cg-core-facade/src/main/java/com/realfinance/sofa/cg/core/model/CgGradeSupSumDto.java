package com.realfinance.sofa.cg.core.model;

public class CgGradeSupSumDto extends BaseDto {
    public CgGradeSupSumDto(Integer id) {
        this.id = id;
    }

    private Integer id;

    /**
     * 供应商名称
     */
    private Integer supplier;

    /**
     * 综合得分
     */
    private Double sumScore;

    /**
     * 名次
     */
    private Integer ranking;

    /**
     * 方案Id
     */
    private Integer projId;

    /**
     * 是否汇总
     */
    private Boolean IsSum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSupplier() {
        return supplier;
    }

    public void setSupplier(Integer supplier) {
        this.supplier = supplier;
    }

    public Double getSumScore() {
        return sumScore;
    }

    public void setSumScore(Double sumScore) {
        this.sumScore = sumScore;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public Integer getProjId() {
        return projId;
    }

    public void setProjId(Integer projId) {
        this.projId = projId;
    }

    public Boolean getSum() {
        return IsSum;
    }

    public void setSum(Boolean sum) {
        IsSum = sum;
    }
}
