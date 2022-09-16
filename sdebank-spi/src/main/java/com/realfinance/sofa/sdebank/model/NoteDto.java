package com.realfinance.sofa.sdebank.model;

import java.util.List;

//通知书模板参数
public class NoteDto {
    //项目名称
    private String projectName;

    //项目编号
    private String projectNo;

    //供应商公司名称
    private List<String> SupplierNames;

    //采购申请人真实名称
    private String realName;

    //采购申请人电话
    private String mobile;

    //生成时间
    private String time;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public List<String> getSupplierNames() {
        return SupplierNames;
    }

    public void setSupplierNames(List<String> supplierNames) {
        SupplierNames = supplierNames;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
