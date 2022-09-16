package com.realfinance.sofa.system.sdebank.datasync;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 统一机构表(中间表)
 */
@Entity
public class EtlOrg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //机构id
    private BigDecimal orgid;

    //机构编码
    @Column(length = 32)
    private String orgcode;

    //机构名称
    @Column(length = 64)
    private String orgname;

    //机构级别(1：一级机构2：二级机构3：三级机构4：四级机构5：五级机构6：六级机构7：七级机构8：八级机构9：九级机构10：十级机构)
    private BigDecimal orglevel;

    //机构等级(01-总行 02-分行 03-支行 04-网点)
    @Column(length = 3)
    private String orgdegree;

    //机构序列号
    @Column(length = 512)
    private String orgseq;

    //机构类型(1-营业机构 2-管理机构)
    @Column(length = 12)
    private String orgtype;

    //机构地址
    @Column(length = 256)
    private String orgaddr;

    //邮编
    @Column(length = 10)
    private String zipcode;

    //机构主管岗位
    private BigDecimal manaposition;

    //机构主管id
    private BigDecimal managerid;

    //机构主管人员
    @Column(length = 128)
    private String orgmanager;

    //联系人
    @Column(length = 30)
    private String linkman;

    //联系电话
    @Column(length = 20)
    private String linktel;

    //电子邮件
    @Column(length = 128)
    private String email;

    //网站地址
    @Column(length = 512)
    private String weburl;

    //生效日期
    private Date startdate;

    //失效日期
    private Date enddate;

    //状态(running：正常cancel：注销)
    private String status;

    //机构排序号
    private Integer sortno;

    //是否叶子节点(y是，n否)
    @Column(length = 1)
    private String isleaf;

    //叶子数
    private BigDecimal subcount;

    //备注
    @Column(length = 512)
    private String remark;

    //父机构id
    private BigDecimal parentorgid;

    //法人代码(01- 广东顺德农村商业银行股份有限公司02- 佛山高明顺银村镇银行股份有限公司03- 丰城顺银村镇银行股份有限公司04- 樟树顺银村镇银行股份有限公司)
    @Column(length = 2)
    private String bankno;

    //归属分行号
    @Column(length = 3)
    private String oucode;

    //机构类别
    @Column(length = 2)
    private String orgcategory;

    //父机构编码
    @Column(length = 32)
    private String parentorgcode;

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

    public String getOrgcode() {
        return orgcode;
    }

    public void setOrgcode(String orgcode) {
        this.orgcode = orgcode;
    }

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    public BigDecimal getOrglevel() {
        return orglevel;
    }

    public void setOrglevel(BigDecimal orglevel) {
        this.orglevel = orglevel;
    }

    public String getOrgdegree() {
        return orgdegree;
    }

    public void setOrgdegree(String orgdegree) {
        this.orgdegree = orgdegree;
    }

    public String getOrgseq() {
        return orgseq;
    }

    public void setOrgseq(String orgseq) {
        this.orgseq = orgseq;
    }

    public String getOrgtype() {
        return orgtype;
    }

    public void setOrgtype(String orgtype) {
        this.orgtype = orgtype;
    }

    public String getOrgaddr() {
        return orgaddr;
    }

    public void setOrgaddr(String orgaddr) {
        this.orgaddr = orgaddr;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public BigDecimal getManaposition() {
        return manaposition;
    }

    public void setManaposition(BigDecimal manaposition) {
        this.manaposition = manaposition;
    }

    public BigDecimal getManagerid() {
        return managerid;
    }

    public void setManagerid(BigDecimal managerid) {
        this.managerid = managerid;
    }

    public String getOrgmanager() {
        return orgmanager;
    }

    public void setOrgmanager(String orgmanager) {
        this.orgmanager = orgmanager;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getLinktel() {
        return linktel;
    }

    public void setLinktel(String linktel) {
        this.linktel = linktel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeburl() {
        return weburl;
    }

    public void setWeburl(String weburl) {
        this.weburl = weburl;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getSortno() {
        return sortno;
    }

    public void setSortno(Integer sortno) {
        this.sortno = sortno;
    }

    public String getIsleaf() {
        return isleaf;
    }

    public void setIsleaf(String isleaf) {
        this.isleaf = isleaf;
    }

    public BigDecimal getSubcount() {
        return subcount;
    }

    public void setSubcount(BigDecimal subcount) {
        this.subcount = subcount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getParentorgid() {
        return parentorgid;
    }

    public void setParentorgid(BigDecimal parentorgid) {
        this.parentorgid = parentorgid;
    }

    public String getBankno() {
        return bankno;
    }

    public void setBankno(String bankno) {
        this.bankno = bankno;
    }

    public String getOucode() {
        return oucode;
    }

    public void setOucode(String oucode) {
        this.oucode = oucode;
    }

    public String getOrgcategory() {
        return orgcategory;
    }

    public void setOrgcategory(String orgcategory) {
        this.orgcategory = orgcategory;
    }

    public String getParentorgcode() {
        return parentorgcode;
    }

    public void setParentorgcode(String parentorgcode) {
        this.parentorgcode = parentorgcode;
    }
}
