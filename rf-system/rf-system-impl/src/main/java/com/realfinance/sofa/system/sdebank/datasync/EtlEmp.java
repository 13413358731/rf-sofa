package com.realfinance.sofa.system.sdebank.datasync;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 统一人员表(中间表)
 */
@Entity
public class EtlEmp implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //人员编码
    @Column(length = 30)
    private String empcode;

    //用户ID
    @Column(length = 30)
    private String userid;

    //人员姓名
    @Column(length = 50)
    private String empname;

    //性别(f/女, m/男)
    @Column(length = 10)
    private String gender;

    //出生日期
    private Date birthdate;

    //基本岗位
    private BigDecimal position;

    //状态(leave离职、off退休、on在岗、wait待岗)
    private String empstatus;

    //证件类型(id身份证、junguan军官证、passport护照、student学生证、zhanzhu暂住证)
    private String cardtype;

    //证件号码
    @Column(length = 20)
    private String cardno;

    //入职日期
    private Date indate;

    //办公电话
    @Column(length = 12)
    private String otel;

    //办公地址
    private String oaddress;

    //办公邮编
    @Column(length = 10)
    private String ozipcode;

    //办公邮件
    @Column(length = 128)
    private String oemail;

    //传真号码
    @Column(length = 14)
    private String faxno;

    //手机号码
    @Column(length = 14)
    private String mobileno;

    //家庭电话
    @Column(length = 12)
    private String htel;

    //家庭地址
    @Column(length = 128)
    private String haddress;

    //家庭邮编
    @Column(length = 10)
    private String hzipcode;

    //私人电子邮箱
    @Column(length = 128)
    private String pemail;

    //政治面貌(crowd：群众comsomol：团员partymember：党员)
    private String party;

    //职级
    private String degree;

    //直接主管
    private BigDecimal major;

    //注册日期
    private Date regdate;

    //所属机构
    private BigDecimal orgid;

    //备注
    @Column(length = 512)
    private String remark;

    //排列顺序
    private String sortno;

    //机构编码
    @Column(length = 32)
    private String orgcode;

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

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public BigDecimal getPosition() {
        return position;
    }

    public void setPosition(BigDecimal position) {
        this.position = position;
    }

    public String getEmpstatus() {
        return empstatus;
    }

    public void setEmpstatus(String empstatus) {
        this.empstatus = empstatus;
    }

    public String getCardtype() {
        return cardtype;
    }

    public void setCardtype(String cardtype) {
        this.cardtype = cardtype;
    }

    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

    public Date getIndate() {
        return indate;
    }

    public void setIndate(Date indate) {
        this.indate = indate;
    }

    public String getOtel() {
        return otel;
    }

    public void setOtel(String otel) {
        this.otel = otel;
    }

    public String getOaddress() {
        return oaddress;
    }

    public void setOaddress(String oaddress) {
        this.oaddress = oaddress;
    }

    public String getOzipcode() {
        return ozipcode;
    }

    public void setOzipcode(String ozipcode) {
        this.ozipcode = ozipcode;
    }

    public String getOemail() {
        return oemail;
    }

    public void setOemail(String oemail) {
        this.oemail = oemail;
    }

    public String getFaxno() {
        return faxno;
    }

    public void setFaxno(String faxno) {
        this.faxno = faxno;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getHtel() {
        return htel;
    }

    public void setHtel(String htel) {
        this.htel = htel;
    }

    public String getHaddress() {
        return haddress;
    }

    public void setHaddress(String haddress) {
        this.haddress = haddress;
    }

    public String getHzipcode() {
        return hzipcode;
    }

    public void setHzipcode(String hzipcode) {
        this.hzipcode = hzipcode;
    }

    public String getPemail() {
        return pemail;
    }

    public void setPemail(String pemail) {
        this.pemail = pemail;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public BigDecimal getMajor() {
        return major;
    }

    public void setMajor(BigDecimal major) {
        this.major = major;
    }

    public Date getRegdate() {
        return regdate;
    }

    public void setRegdate(Date regdate) {
        this.regdate = regdate;
    }

    public BigDecimal getOrgid() {
        return orgid;
    }

    public void setOrgid(BigDecimal orgid) {
        this.orgid = orgid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSortno() {
        return sortno;
    }

    public void setSortno(String sortno) {
        this.sortno = sortno;
    }

    public String getOrgcode() {
        return orgcode;
    }

    public void setOrgcode(String orgcode) {
        this.orgcode = orgcode;
    }
}
