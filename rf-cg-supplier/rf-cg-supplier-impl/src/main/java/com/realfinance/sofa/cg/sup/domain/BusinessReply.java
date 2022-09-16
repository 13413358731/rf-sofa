package com.realfinance.sofa.cg.sup.domain;

import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 商务应答
 */
@Entity
@Table(name = "CG_SUP_BIZ_REPLY")
public class BusinessReply extends BaseEntity implements IEntity<Integer> {

    @Version
    private Long v;

    /**
     * 法人ID
     */
    @Column(nullable = false)
    private String tenantId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    /**
     * 发布ID
     */
    @Column(nullable = false, length = 32)
    protected String releaseId;

    /**
     * 供应商ID
     */
    @Column(nullable = false)
    protected Integer supplierId;

    /**
     * 应答类型
     */
    @Column(nullable = false)
    protected String replyType;

    /**
     * 应答截止时间
     */
    @Column(nullable = false)
    protected LocalDateTime deadline;

    /**
     * 发布时间
     */
    @Column(nullable = false)
    protected LocalDateTime releaseTime;

    /**
     * 开启时间
     */
    @Column
    protected LocalDateTime openTime;

    /**
     * 名称、标题
     */
    @Column(nullable = false)
    protected String name;

    /**
     * 内容
     */
    @Column(length = 1024,nullable = false)
    protected String content;

    /**
     * 附件已下载
     */
    @Column
    private LocalDateTime fileDownloadTime;

    /**
     * 已签名
     */
    @Column
    private LocalDateTime signTime;

    /**
     * 是否需要报价
     */
    @Column(nullable = false)
    protected Boolean needQuote;

    /**
     * 报价轮次
     */
    @Column
    protected Integer quoteRound;

    /**
     * 报价总金额
     */
    @Column
    protected BigDecimal totalPrice;

    /**
     * 项目名称
     */
    @Column(nullable = false)
    protected String projectName;

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
     * 备注
     */
    protected String note;

    /**
     *
     */
    protected Boolean normal;

    /**
     * 业务项目
     */
    @ManyToOne
    @JoinColumn(name = "biz_proj_id", updatable = false)
    private BusinessProject project;

    /**
     * 供应商要下载的采购文档
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "biz_reply_id")
    protected List<BusinessReplyAttD> attDs;

    /**
     * 供应商上传的文档
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "biz_reply_id")
    protected List<BusinessReplyAttU> attUs;

    /**
     * 报价信息
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "biz_reply_id")
    protected List<BusinessReplyPrice> prices;

    public BusinessReply() {
        attDs = new ArrayList<>();
        attUs = new ArrayList<>();
        prices = new ArrayList<>();
    }

    public Long getV() {
        return v;
    }

    public void setV(Long v) {
        this.v = v;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public BusinessProject getProject() {
        return project;
    }

    public void setProject(BusinessProject project) {
        this.project = project;
    }

    public List<BusinessReplyAttD> getAttDs() {
        return attDs;
    }

    public void setAttDs(List<BusinessReplyAttD> attDs) {
        this.attDs = attDs;
    }

    public List<BusinessReplyAttU> getAttUs() {
        return attUs;
    }

    public void setAttUs(List<BusinessReplyAttU> attUs) {
        this.attUs = attUs;
    }

    public List<BusinessReplyPrice> getPrices() {
        return prices;
    }

    public void setPrices(List<BusinessReplyPrice> prices) {
        this.prices = prices;
    }
}
