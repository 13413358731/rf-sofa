package com.realfinance.sofa.cg.core.domain;


import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * 节假日
 */
@Entity
@Table(name = "CG_CORE_HOLIDAY")
public class HoliDay extends BaseEntity implements IEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 法人ID
     */
    @Column(nullable = false)
    private String tenantId;


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

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
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
