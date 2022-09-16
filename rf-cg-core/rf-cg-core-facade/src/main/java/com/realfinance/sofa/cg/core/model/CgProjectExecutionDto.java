package com.realfinance.sofa.cg.core.model;

public class CgProjectExecutionDto extends BaseDto {

    public CgProjectExecutionDto(Integer id) {
        this.id = id;
    }

    private Integer id;

    protected String quoteType;

    private String invalidReason;

    private String modifyMode;

    /**
     * 是否废弃
     */
    private Boolean invalid;

    private String reason;

    private Boolean returnReq;

    private CgProjectForExecDto project;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

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

    public String getInvalidReason() {
        return invalidReason;
    }

    public void setInvalidReason(String invalidReason) {
        this.invalidReason = invalidReason;
    }

    public Boolean getInvalid() {
        return invalid;
    }

    public void setInvalid(Boolean invalid) {
        this.invalid = invalid;
    }

    public CgProjectForExecDto getProject() {
        return project;
    }

    public void setProject(CgProjectForExecDto project) {
        this.project = project;
    }

    public String getModifyMode() {
        return modifyMode;
    }

    public void setModifyMode(String modifyMode) {
        this.modifyMode = modifyMode;
    }

    public Boolean getReturnReq() {
        return returnReq;
    }

    public void setReturnReq(Boolean returnReq) {
        this.returnReq = returnReq;
    }
}
