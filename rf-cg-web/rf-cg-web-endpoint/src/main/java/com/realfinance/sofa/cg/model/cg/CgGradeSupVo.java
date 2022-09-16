package com.realfinance.sofa.cg.model.cg;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.realfinance.sofa.cg.model.system.UserVo;
import com.realfinance.sofa.common.model.IdentityObject;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "采购方案执行-供应商对象")
public class CgGradeSupVo implements IdentityObject<Integer> {
    protected Integer id;

    @Schema(description = "供应商")
    protected CgSupplierVo supplier;

    @Schema(description = "大项名称")
    private String name;

    @Schema(description = "细项名称")
    private String subName;

    protected String subCode;

    @Schema(description = "评审会专家")
    private UserVo expert;

    @Schema(description = "评分标准")
    protected String note;

    @Schema(description = "得分")
    private Integer score;

    @Schema(description = "权重")
    private BigDecimal weight;

    @Schema(description = "评分细则Id")
    private Integer ProjEvalRuleId;

    @Schema(description = "方案Id")
    private Integer projId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CgSupplierVo getSupplier() {
        return supplier;
    }

    public void setSupplier(CgSupplierVo supplier) {
        this.supplier = supplier;
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

    public UserVo getExpert() {
        return expert;
    }

    public void setExpert(UserVo expert) {
        this.expert = expert;
    }

    //    public CgExpertVo getExpert() {
//        return expert;
//    }
//
//    public void setExpert(CgExpertVo expert) {
//        this.expert = expert;
//    }

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
}
