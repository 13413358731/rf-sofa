package com.realfinance.sofa.cg.core.model;

import java.time.LocalDate;

public class CgContractManageQueryCriteria {

    /**
     * 方案名称模糊
     */
    private String contractNameLike;

    /**
     * 项目编码模糊
     */
    private String projectNoLike;

    private LocalDate startDateBefore;

    private LocalDate startDateAfter;

    private LocalDate expireDateBefore;

    private LocalDate expireDateAfter;

    public String getContractNameLike() {
        return contractNameLike;
    }

    public void setContractNameLike(String contractNameLike) {
        this.contractNameLike = contractNameLike;
    }

    public String getProjectNoLike() {
        return projectNoLike;
    }

    public void setProjectNoLike(String projectNoLike) {
        this.projectNoLike = projectNoLike;
    }

    public LocalDate getStartDateBefore() {
        return startDateBefore;
    }

    public void setStartDateBefore(LocalDate startDateBefore) {
        this.startDateBefore = startDateBefore;
    }

    public LocalDate getStartDateAfter() {
        return startDateAfter;
    }

    public void setStartDateAfter(LocalDate startDateAfter) {
        this.startDateAfter = startDateAfter;
    }

    public LocalDate getExpireDateBefore() {
        return expireDateBefore;
    }

    public void setExpireDateBefore(LocalDate expireDateBefore) {
        this.expireDateBefore = expireDateBefore;
    }

    public LocalDate getExpireDateAfter() {
        return expireDateAfter;
    }

    public void setExpireDateAfter(LocalDate expireDateAfter) {
        this.expireDateAfter = expireDateAfter;
    }
}
