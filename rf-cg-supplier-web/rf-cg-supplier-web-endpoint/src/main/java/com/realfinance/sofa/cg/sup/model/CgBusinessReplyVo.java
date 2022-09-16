package com.realfinance.sofa.cg.sup.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class CgBusinessReplyVo {

    protected Integer id;

    /**
     * 供应商ID
     */
    protected Integer supplierId;

    /**
     *供应商名称
     */
    protected String supplierName;
    /**
     * 名称、标题
     */
    protected String name;

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
    private CgBusinessProjectDto project;

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

    /**
     * 供应商要下载的采购文档
     */
    protected List<AttachmentVo> attDs;

    /**
     * 供应商上传的文档
     */
    protected List<AttachmentVo> attUs;

    /**
     * 报价信息
     */
    protected List<CgBusinessReplyPriceDto> prices;

    /**
     *需要展示的 供应商数据 和报价
     */
    protected List<CgBusinessReplyDto> list;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Boolean getNeedQuote() {
        return needQuote;
    }

    public void setNeedQuote(Boolean needQuote) {
        this.needQuote = needQuote;
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

    public CgBusinessProjectDto getProject() {
        return project;
    }

    public void setProject(CgBusinessProjectDto project) {
        this.project = project;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<AttachmentVo> getAttDs() {
        return attDs;
    }

    public void setAttDs(List<AttachmentVo> attDs) {
        this.attDs = attDs;
    }

    public List<AttachmentVo> getAttUs() {
        return attUs;
    }

    public void setAttUs(List<AttachmentVo> attUs) {
        this.attUs = attUs;
    }

    public List<CgBusinessReplyPriceDto> getPrices() {
        return prices;
    }

    public void setPrices(List<CgBusinessReplyPriceDto> prices) {
        this.prices = prices;
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

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public List<CgBusinessReplyDto> getList() {
        return list;
    }

    public void setList(List<CgBusinessReplyDto> list) {
        this.list = list;
    }
}
