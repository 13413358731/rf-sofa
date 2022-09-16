package com.realfinance.sofa.system.sdebank.datasync;

import javax.persistence.*;

/**
 * 统一角色表(中间表)
 */
@Entity
public class EtlRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //角色代码
    @Column(length = 64)
    private String rolecode;

    //角色名称
    @Column(length = 64)
    private String rolename;
    //角色描述
    private String roledesc;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRolecode() {
        return rolecode;
    }

    public void setRolecode(String rolecode) {
        this.rolecode = rolecode;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String getRoledesc() {
        return roledesc;
    }

    public void setRoledesc(String roledesc) {
        this.roledesc = roledesc;
    }
}
