package com.realfinance.sofa.cg.model.cg;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.realfinance.sofa.cg.model.BaseVo;
import com.realfinance.sofa.cg.model.EditableVo;
import com.realfinance.sofa.common.model.IdentityObject;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "采购方案执行对象")
public class CgGradeSupSumVo extends BaseVo implements EditableVo, IdentityObject<Integer> {

    protected Integer id;

    private CgSupplierVo supplier;

    private Double sumScore;

    private Integer ranking;

    private Boolean IsSum;

    private List<CgGradeSupVo> gradeSups;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public CgSupplierVo getSupplier() {
        return supplier;
    }

    public void setSupplier(CgSupplierVo supplier) {
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

    public Boolean getSum() {
        return IsSum;
    }

    public void setSum(Boolean sum) {
        IsSum = sum;
    }

    public List<CgGradeSupVo> getGradeSups() {
        return gradeSups;
    }

    public void setGradeSups(List<CgGradeSupVo> gradeSups) {
        this.gradeSups = gradeSups;
    }
}
