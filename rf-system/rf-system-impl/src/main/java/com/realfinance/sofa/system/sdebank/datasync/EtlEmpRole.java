package com.realfinance.sofa.system.sdebank.datasync;

import javax.persistence.*;

/**
 * 统一人员角色表(中间表)
 */
@Entity
public class EtlEmpRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //人员代码
    @Column(length = 30)
    private String empcode;

    //角色代码
    @Column(length = 64)
    private String rolecode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmpcode() {
        return empcode;
    }

    public void setEmpcode(String empcode) {
        this.empcode = empcode;
    }

    public String getRolecode() {
        return rolecode;
    }

    public void setRolecode(String rolecode) {
        this.rolecode = rolecode;
    }
}
