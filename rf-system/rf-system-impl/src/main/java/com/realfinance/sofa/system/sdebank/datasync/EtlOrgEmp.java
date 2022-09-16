package com.realfinance.sofa.system.sdebank.datasync;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 统一人员机构表(中间表)
 */
@Entity
public class EtlOrgEmp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //机构id
    private BigDecimal orgid;

    //员工id
    private BigDecimal empid;

    //是否主机构(y是、n否)
    @Column(length = 1)
    private String ismain;

    //租户id
    @Column(length = 64)
    private String tenantid;

    //机构编码
    @Column(length = 32)
    private String orgcode;

    //员工编码
    @Column(length = 30)
    private String empcode;

    //用户id
    @Column(length = 32)
    private String userid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getOrgid() {
        return orgid;
    }

    public void setOrgid(BigDecimal orgid) {
        this.orgid = orgid;
    }

    public BigDecimal getEmpid() {
        return empid;
    }

    public void setEmpid(BigDecimal empid) {
        this.empid = empid;
    }

    public String getIsmain() {
        return ismain;
    }

    public void setIsmain(String ismain) {
        this.ismain = ismain;
    }

    public String getTenantid() {
        return tenantid;
    }

    public void setTenantid(String tenantid) {
        this.tenantid = tenantid;
    }

    public String getOrgcode() {
        return orgcode;
    }

    public void setOrgcode(String orgcode) {
        this.orgcode = orgcode;
    }

    public String getEmpcode() {
        return empcode;
    }

    public void setEmpcode(String empcode) {
        this.empcode = empcode;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
