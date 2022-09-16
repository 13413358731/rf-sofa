package com.realfinance.sofa.system.sdebank.associatedTran;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * 关联交易同步表
 */
@Entity
public class AssociatedTran {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //关联方类型
    @Column(length = 122)
    private String infoTypeName;

    //关联方姓名
    @Column(length = 200)
    private String custName;

    //证件类型
    @Column(length = 150)
    private String idtp;

    //证件号码
    @Column(length = 120)
    private String idNo;

    //客户号
    @Column(length = 44)
    private String custId;

    //岗位
    @Column(length = 200)
    private String post;

    //对应的内部人/主要自然人股东
    @Column(length = 200)
    private String innrPartner;

    //对应的我行关联法人或其他组织的名称
    @Column(length = 200)
    private String orgName;

    //控股自然人股东/董事/关键管理人员
    @Column(length = 200)
    private String manager;

    //是否我行股东
    @Column(length = 3)
    private String isPartner;

    //备注
    @Column(length = 240)
    private String remark;

    //归属单位
    @Column(length = 96)
    private String recOrg;

    //是否关联方
    @Column(length = 3)
    private String isInner;

    //在我行担任的岗位
    @Column(length = 200)
    private String post1;

    //准入时间
    private Timestamp enterTime;

    //退出时间
    private Timestamp quitTime;

    //是否本行董事高管或其近亲属
    @Column(length = 3)
    private String isHeader;

    //关联关系
    @Column(length = 120)
    private String infoType;

    //核心证件号码
    @Column(length = 135)
    private String absCertNo;

    //是否本行董事高管或其近亲属2
    @Column(length = 1)
    private String isHeader2;

    //对应的内部人或主要自然人股东的证件类型
    @Column(length = 150)
    private String innrPartnerIdtp;

    //对应的内部人或主要自然人股东的证件号码
    @Column(length = 90)
    private String innrPartnerIdno;

    //统计口径
    @Column(length = 24)
    private String innrStatCalib;

    //对应的内部人或主要自然人股东的归属机构
    @Column(length = 26)
    private String innrPartnerOrg;

    //控制方式
    @Column(length = 12)
    private String controlType;

    //持股比例
    @Column(length = 23)
    private String stockPec;

    //机构统计口径
    @Column(length = 24)
    private String statCalib;

    //状态
    @Column(length = 3)
    private String status;

    //员工号
    @Column(length = 18)
    private String empId;

    public AssociatedTran() {
    }

    public AssociatedTran(List<String> list) {
        this.infoTypeName = list.get(0);
        this.custName = list.get(1);
        this.idtp = list.get(2);
        this.idNo = list.get(3);
        this.custId = list.get(4);
        this.post = list.get(5);
        this.innrPartner = list.get(6);
        this.orgName = list.get(7);
        this.manager = list.get(8);
        this.isPartner = list.get(9);
        this.remark = list.get(10);
        this.recOrg = list.get(11);
        this.isInner = list.get(12);
        this.post1 = list.get(13);
        this.enterTime = list.get(14) == null ? null : Timestamp.valueOf(list.get(14));
        this.quitTime = list.get(15) == null ? null : Timestamp.valueOf(list.get(15));
        this.isHeader = list.get(16);
        this.infoType = list.get(17);
        this.absCertNo = list.get(18);
        this.isHeader2 = list.get(19);
        this.innrPartnerIdtp = list.get(20);
        this.innrPartnerIdno = list.get(21);
        this.innrStatCalib = list.get(22);
        this.innrPartnerOrg = list.get(23);
        this.controlType = list.get(24);
        this.stockPec = list.get(25);
        this.statCalib = list.get(26);
        this.status = list.get(27);
        this.empId = list.get(28);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInfoTypeName() {
        return infoTypeName;
    }

    public void setInfoTypeName(String infoTypeName) {
        this.infoTypeName = infoTypeName;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getIdtp() {
        return idtp;
    }

    public void setIdtp(String idtp) {
        this.idtp = idtp;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getInnrPartner() {
        return innrPartner;
    }

    public void setInnrPartner(String innrPartner) {
        this.innrPartner = innrPartner;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getIsPartner() {
        return isPartner;
    }

    public void setIsPartner(String isPartner) {
        this.isPartner = isPartner;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRecOrg() {
        return recOrg;
    }

    public void setRecOrg(String recOrg) {
        this.recOrg = recOrg;
    }

    public String getIsInner() {
        return isInner;
    }

    public void setIsInner(String isInner) {
        this.isInner = isInner;
    }

    public String getPost1() {
        return post1;
    }

    public void setPost1(String post1) {
        this.post1 = post1;
    }

    public Timestamp getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(Timestamp enterTime) {
        this.enterTime = enterTime;
    }

    public Timestamp getQuitTime() {
        return quitTime;
    }

    public void setQuitTime(Timestamp quitTime) {
        this.quitTime = quitTime;
    }

    public String getIsHeader() {
        return isHeader;
    }

    public void setIsHeader(String isHeader) {
        this.isHeader = isHeader;
    }

    public String getInfoType() {
        return infoType;
    }

    public void setInfoType(String infoType) {
        this.infoType = infoType;
    }

    public String getAbsCertNo() {
        return absCertNo;
    }

    public void setAbsCertNo(String absCertNo) {
        this.absCertNo = absCertNo;
    }

    public String getIsHeader2() {
        return isHeader2;
    }

    public void setIsHeader2(String isHeader2) {
        this.isHeader2 = isHeader2;
    }

    public String getInnrPartnerIdtp() {
        return innrPartnerIdtp;
    }

    public void setInnrPartnerIdtp(String innrPartnerIdtp) {
        this.innrPartnerIdtp = innrPartnerIdtp;
    }

    public String getInnrPartnerIdno() {
        return innrPartnerIdno;
    }

    public void setInnrPartnerIdno(String innrPartnerIdno) {
        this.innrPartnerIdno = innrPartnerIdno;
    }

    public String getInnrStatCalib() {
        return innrStatCalib;
    }

    public void setInnrStatCalib(String innrStatCalib) {
        this.innrStatCalib = innrStatCalib;
    }

    public String getInnrPartnerOrg() {
        return innrPartnerOrg;
    }

    public void setInnrPartnerOrg(String innrPartnerOrg) {
        this.innrPartnerOrg = innrPartnerOrg;
    }

    public String getControlType() {
        return controlType;
    }

    public void setControlType(String controlType) {
        this.controlType = controlType;
    }

    public String getStockPec() {
        return stockPec;
    }

    public void setStockPec(String stockPec) {
        this.stockPec = stockPec;
    }

    public String getStatCalib() {
        return statCalib;
    }

    public void setStatCalib(String statCalib) {
        this.statCalib = statCalib;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }
}
