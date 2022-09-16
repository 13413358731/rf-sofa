package com.realfinance.sofa.cg.model.cg;

import com.realfinance.sofa.cg.model.BaseVo;
import com.realfinance.sofa.common.model.IdentityObject;
import io.swagger.v3.oas.annotations.media.Schema;

public class CgSupplierCreditVo extends BaseVo implements IdentityObject<Integer> {

    @Schema(description = "ID")
    protected Integer id;
    //决定文书号
    @Schema(description = "决定文书号")
    private String documentNumbers;
    //处罚项名称
    @Schema(description = "处罚项名称")
    private String penaltyItemNames;
    //处罚原因
    @Schema(description = "处罚原因")
    private String penaltyCauses;
    //处罚日期
    @Schema(description = "处罚日期")
    private String penaltyDates;
    //处罚类型(严重违法,海关失信,政府采购违法,电子商务失信,涉金融黑名单,环保违规处罚,税务行政处罚,假冒专利行为,工商行政处罚,工商行政处罚,安全生产黑名单,进出口行政处罚,环保行政,重大税收违法)
    @Schema(description = "处罚类型")
    private String penaltyType;

    @Override
    public Integer getId() {
        return null;
    }

    @Override
    public void setId(Integer integer) {

    }

    public String getDocumentNumbers() {
        return documentNumbers;
    }

    public void setDocumentNumbers(String documentNumbers) {
        this.documentNumbers = documentNumbers;
    }

    public String getPenaltyItemNames() {
        return penaltyItemNames;
    }

    public void setPenaltyItemNames(String penaltyItemNames) {
        this.penaltyItemNames = penaltyItemNames;
    }

    public String getPenaltyCauses() {
        return penaltyCauses;
    }

    public void setPenaltyCauses(String penaltyCauses) {
        this.penaltyCauses = penaltyCauses;
    }

    public String getPenaltyDates() {
        return penaltyDates;
    }

    public void setPenaltyDates(String penaltyDates) {
        this.penaltyDates = penaltyDates;
    }

    public String getPenaltyType() {
        return penaltyType;
    }

    public void setPenaltyType(String penaltyType) {
        this.penaltyType = penaltyType;
    }
}
