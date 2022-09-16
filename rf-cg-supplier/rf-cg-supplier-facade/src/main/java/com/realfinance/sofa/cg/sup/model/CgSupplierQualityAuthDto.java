package com.realfinance.sofa.cg.sup.model;

import java.time.LocalDate;

public class CgSupplierQualityAuthDto {

    private Integer id;

    /**
     * 资质名称
     */
    private String name;

    /**
     * 生效日期
     */
    private LocalDate validTime;

    /**
     * 失效日期
     */
    private LocalDate invalidTime;

    /**
     * 发证单位
     */
    private String authorizeUnit;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getValidTime() {
        return validTime;
    }

    public void setValidTime(LocalDate validTime) {
        this.validTime = validTime;
    }

    public LocalDate getInvalidTime() {
        return invalidTime;
    }

    public void setInvalidTime(LocalDate invalidTime) {
        this.invalidTime = invalidTime;
    }

    public String getAuthorizeUnit() {
        return authorizeUnit;
    }

    public void setAuthorizeUnit(String authorizeUnit) {
        this.authorizeUnit = authorizeUnit;
    }
}
