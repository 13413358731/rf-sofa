package com.realfinance.sofa.cg.sup.model;

import org.springframework.data.annotation.Version;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CgBusinessReplyDto {

    @Version
    private Long v;

    protected Integer id;

    /**
     * 法人ID
     */
    protected String tenantId;

    /**
     * 名称、标题
     */
    protected String name;

    /**
     * 发布ID
     */
    protected String releaseId;

    /**
     * 供应商ID
     */
    protected Integer supplierId;

    /**
     * 供应商名称
     */
    protected String supplierName;

    /**
     * 应答类型
     */
    protected String replyType;

    /**
     * 应答截止时间
     */
    protected LocalDateTime deadline;

    /**
     * 发布时间
     */
    protected LocalDateTime releaseTime;

    /**
     * 开启时间
     */
    protected LocalDateTime openTime;

    /**
     * 内容
     */
    protected String content;

    /**
     * 附件已下载
     */
    private LocalDateTime fileDownloadTime;

    /**
     * 已签名
     */
    private LocalDateTime signTime;

    /**
     * 是否需要报价
     */
    protected Boolean needQuote;

    /**
     * 报价轮次
     */
    protected Integer quoteRound;

    /**
     * 报价总金额
     */
    protected BigDecimal totalPrice;

    /**
     * 应答说明
     */
    protected String replyDescription;

    /**
     * 付款方式
     */
    protected String paymentDescription;

    /**
     * 供货期
     */
    protected String supplyDescription;

    /**
     * 税率说明
     */
    protected String taxRateDescription;

    /**
     * 其他说明
     */
    protected String otherDescription;

    /**
     * 业务项目
     */
//    private CgBusinessProjectDto project;

    /**
     * 项目名称
     */
    protected String projectName;

    /**
     * 备注
     */
    protected String note;

    /**
     *是否正常
     */
    protected Boolean normal;


    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Long getV() {
        return v;
    }

    public void setV(Long v) {
        this.v = v;
    }

    /**
     * 供应商列表
     */
    //private List<CgSupplierDto> cgSupplierDtos;

    //public List<CgSupplierDto> getCgSupplierDtos() {
    //    return cgSupplierDtos;
    //}

    //public void setCgSupplierDtos(List<CgSupplierDto> cgSupplierDtos) {
    //    this.cgSupplierDtos = cgSupplierDtos;
    //}



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReleaseId() {
        return releaseId;
    }

    public void setReleaseId(String releaseId) {
        this.releaseId = releaseId;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getReplyType() {
        return replyType;
    }

    public void setReplyType(String replyType) {
        this.replyType = replyType;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public LocalDateTime getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(LocalDateTime releaseTime) {
        this.releaseTime = releaseTime;
    }

    public LocalDateTime getOpenTime() {
        return openTime;
    }

    public void setOpenTime(LocalDateTime openTime) {
        this.openTime = openTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getFileDownloadTime() {
        return fileDownloadTime;
    }

    public void setFileDownloadTime(LocalDateTime fileDownloadTime) {
        this.fileDownloadTime = fileDownloadTime;
    }

    public LocalDateTime getSignTime() {
        return signTime;
    }

    public void setSignTime(LocalDateTime signTime) {
        this.signTime = signTime;
    }

    public Boolean getNeedQuote() {
        return needQuote;
    }

    public void setNeedQuote(Boolean needQuote) {
        this.needQuote = needQuote;
    }

    public Integer getQuoteRound() {
        return quoteRound;
    }

    public void setQuoteRound(Integer quoteRound) {
        this.quoteRound = quoteRound;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getReplyDescription() {
        return replyDescription;
    }

    public void setReplyDescription(String replyDescription) {
        this.replyDescription = replyDescription;
    }

    public String getPaymentDescription() {
        return paymentDescription;
    }

    public void setPaymentDescription(String paymentDescription) {
        this.paymentDescription = paymentDescription;
    }

    public String getSupplyDescription() {
        return supplyDescription;
    }

    public void setSupplyDescription(String supplyDescription) {
        this.supplyDescription = supplyDescription;
    }

    public String getTaxRateDescription() {
        return taxRateDescription;
    }

    public void setTaxRateDescription(String taxRateDescription) {
        this.taxRateDescription = taxRateDescription;
    }

    public String getOtherDescription() {
        return otherDescription;
    }

    public void setOtherDescription(String otherDescription) {
        this.otherDescription = otherDescription;
    }

//    public CgBusinessProjectDto getProject() {
//        return project;
//    }
//
//    public void setProject(CgBusinessProjectDto project) {
//        this.project = project;
//    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Boolean getNormal() {
        return normal;
    }

    public void setNormal(Boolean normal) {
        this.normal = normal;
    }
}
