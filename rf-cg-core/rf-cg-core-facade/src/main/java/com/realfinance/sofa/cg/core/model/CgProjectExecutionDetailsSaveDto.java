package com.realfinance.sofa.cg.core.model;

import java.util.List;

public class CgProjectExecutionDetailsSaveDto {

    private Integer id;

    private String quoteType;

    private List<CgProjectExecutionAttDto> projectExecutionAtts;

    private Boolean returnReq;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuoteType() {
        return quoteType;
    }

    public void setQuoteType(String quoteType) {
        this.quoteType = quoteType;
    }

    public List<CgProjectExecutionAttDto> getProjectExecutionAtts() {
        return projectExecutionAtts;
    }

    public void setProjectExecutionAtts(List<CgProjectExecutionAttDto> projectExecutionAtts) {
        this.projectExecutionAtts = projectExecutionAtts;
    }

    public Boolean getReturnReq() {
        return returnReq;
    }

    public void setReturnReq(Boolean returnReq) {
        this.returnReq = returnReq;
    }
}
