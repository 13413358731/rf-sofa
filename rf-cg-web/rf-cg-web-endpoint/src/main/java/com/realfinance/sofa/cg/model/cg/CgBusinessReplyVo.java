package com.realfinance.sofa.cg.model.cg;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "应答对象")
public class CgBusinessReplyVo {
    protected Integer id;

    /**
     * 供应商应答列表
     */
   // protected List<CgProjectExecutionSupVo> suppliers;

    /**
     * 供应商
     */
    protected CgSupplierVo supplier;

    /**
     * 名称、标题
     */
    protected String name;

    /**
     * 附件已下载
     */
    private LocalDateTime fileDownloadTime;

    /**
     * 已签名
     */
    private LocalDateTime signTime;

    /**
     * 报价轮次
     */
    private Integer quoteRound;

    /**
     * 报价总金额
     */
    private BigDecimal totalPrice;

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
     * 是否报价
     */
    protected Boolean needQuote;

    /**
     * 备注
     */
    protected String note;

    /**
     *是否正常
     */
    protected Boolean normal;

    /**
     * 供应商列表
     */
    protected List<CgBusinessReplyPriceVo> prices;

    protected List<CgSupplierAttachmentVo> attDs;

    /**
     * 供应商上传的文档
     */
    protected List<CgSupplierAttachmentVo> attUs;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CgSupplierVo getSupplier() {
        return supplier;
    }

    public void setSupplier(CgSupplierVo supplier) {
        this.supplier = supplier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CgSupplierAttachmentVo> getAttDs() {
        return attDs;
    }

    public void setAttDs(List<CgSupplierAttachmentVo> attDs) {
        this.attDs = attDs;
    }

    public List<CgSupplierAttachmentVo> getAttUs() {
        return attUs;
    }

    public void setAttUs(List<CgSupplierAttachmentVo> attUs) {
        this.attUs = attUs;
    }

    /*public List<CgProjectExecutionSupVo> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<CgProjectExecutionSupVo> suppliers) {
        this.suppliers = suppliers;
    }*/

    public Boolean getNeedQuote() {
        return needQuote;
    }

    public void setNeedQuote(Boolean needQuote) {
        this.needQuote = needQuote;
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

    public List<CgBusinessReplyPriceVo> getPrices() {
        return prices;
    }

    public void setPrices(List<CgBusinessReplyPriceVo> prices) {
        this.prices = prices;
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

    /*public CgSupplierVo getSupplier() {
        return supplier;
    }

    public void setSupplier(CgSupplierVo supplier) {
        this.supplier = supplier;
    }*/
}
