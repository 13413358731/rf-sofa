package com.realfinance.sofa.cg.sup.model;

public class CgSupEvaluatorDto extends BaseDto {

    private Integer id;

    /**
     * 姓名
     */
    private String realname;

    /**
     * 人员编码
     */
    private String username;

    /**
     * 部门
     */
    private Integer department;

    /**
     * 联系电话
     */
    private String mobile;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getDepartment() {
        return department;
    }

    public void setDepartment(Integer department) {
        this.department = department;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}

