package com.realfinance.sofa.cg.core.model;

import java.time.LocalDate;

public class HoliDaySaveDto {

    private Integer id;

    /**
     * 日期
     */
    private String holidayDate;

    /**
     * 标志
     */
    private Integer  holidayFlag;

    /**
     * 名称
     */
    private  String  holidayName;

    /**
     * 备注
     */
    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHolidayDate() {
        return holidayDate;
    }

    public void setHolidayDate(String holidayDate) {
        this.holidayDate = holidayDate;
    }

    public Integer getHolidayFlag() {
        return holidayFlag;
    }

    public void setHolidayFlag(Integer holidayFlag) {
        this.holidayFlag = holidayFlag;
    }

    public String getHolidayName() {
        return holidayName;
    }

    public void setHolidayName(String holidayName) {
        this.holidayName = holidayName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
