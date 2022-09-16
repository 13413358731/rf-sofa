package com.realfinance.sofa.cg.core.model;

import java.time.LocalDateTime;

public class CgAuditQualificationDto extends BaseDto {
    private Integer id;

    /**
     * 大项编码
     */
    private String code;

    /**
     * 大项名称
     */
    private String name;

    /**
     * 细项编码
     */
    private String subCode;

    /**
     * 细项名称
     */
    private String subName;

    /**
     * 推荐供应商
     */
    private Integer supplier;

    /**
     * 评审会专家
     */
    private Integer expert;

    /**
     * 是否通过
     */
    private Boolean pass;

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

    public Boolean getPass() {
        return pass;
    }

    public void setPass(Boolean pass) {
        this.pass = pass;
    }
}
