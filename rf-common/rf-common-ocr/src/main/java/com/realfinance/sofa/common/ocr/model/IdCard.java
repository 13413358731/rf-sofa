package com.realfinance.sofa.common.ocr.model;

import java.io.Serializable;

/**
 * 身份证
 */
public class IdCard implements Serializable {

    /**
     * 证件类型
     */
    private String type;
    /**
     * 姓名
     */
    private String name;
    /**
     * 身份证号
     */
    private String idNumber;
    /**
     * 出生日期
     */
    private String birthday;
    /**
     * 性别
     */
    private String sex;
    /**
     * 名族
     */
    private String people;
    /**
     * 住址
     */
    private String address;
    /**
     * 签发机关
     */
    private String issueAuthority;
    /**
     * 有效日期
     */
    private String validity;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIssueAuthority() {
        return issueAuthority;
    }

    public void setIssueAuthority(String issueAuthority) {
        this.issueAuthority = issueAuthority;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }
}
