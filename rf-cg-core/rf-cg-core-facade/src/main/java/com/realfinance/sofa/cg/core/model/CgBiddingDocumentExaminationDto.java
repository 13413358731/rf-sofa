package com.realfinance.sofa.cg.core.model;

public class CgBiddingDocumentExaminationDto {
    protected Integer id;

    /**
     * 大项编码
     */
    protected String code;

    /**
     * 大项名称
     */
    protected String name;

    /**
     * 细项编码
     */
    protected String subCode;

    /**
     * 细项名称
     */
    protected String subName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }
}
