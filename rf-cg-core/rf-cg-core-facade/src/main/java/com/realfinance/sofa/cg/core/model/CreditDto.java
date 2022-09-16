package com.realfinance.sofa.cg.core.model;

import java.util.List;

/**
 * 2.48 融安E信_企业风险明细接口
 * 信用信息
 */
public class CreditDto {
    //供应商库id
    private Integer SupplierId;
    //决定文书号
    private List<String> documentNumbers;
    //处罚项名称
    private List<String> penaltyItemNames;
    //处罚原因
    private List<String> penaltyCauses;
    //处罚日期
    private List<String> penaltyDates;
    //处罚类型(严重违法,海关失信,政府采购违法,电子商务失信,涉金融黑名单,环保违规处罚,税务行政处罚,假冒专利行为,工商行政处罚,工商行政处罚,安全生产黑名单,进出口行政处罚,环保行政,重大税收违法)
    private String penaltyType;

    public Integer getSupplierId() {
        return SupplierId;
    }

    public void setSupplierId(Integer supplierId) {
        SupplierId = supplierId;
    }

    public List<String> getDocumentNumbers() {
        return documentNumbers;
    }

    public void setDocumentNumbers(List<String> documentNumbers) {
        this.documentNumbers = documentNumbers;
    }

    public List<String> getPenaltyItemNames() {
        return penaltyItemNames;
    }

    public void setPenaltyItemNames(List<String> penaltyItemNames) {
        this.penaltyItemNames = penaltyItemNames;
    }

    public List<String> getPenaltyCauses() {
        return penaltyCauses;
    }

    public void setPenaltyCauses(List<String> penaltyCauses) {
        this.penaltyCauses = penaltyCauses;
    }

    public List<String> getPenaltyDates() {
        return penaltyDates;
    }

    public void setPenaltyDates(List<String> penaltyDates) {
        this.penaltyDates = penaltyDates;
    }

    public String getPenaltyType() {
        return penaltyType;
    }

    public void setPenaltyType(String penaltyType) {
        this.penaltyType = penaltyType;
    }
}
