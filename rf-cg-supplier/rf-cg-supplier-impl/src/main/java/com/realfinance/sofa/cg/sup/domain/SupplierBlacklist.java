package com.realfinance.sofa.cg.sup.domain;

import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 黑名单
 */
@Entity
@Table(name = "CG_SUP_SUPPLIER_BLACKLIST")
public class SupplierBlacklist extends BaseEntity implements IEntity<Integer> {

    @Version
    private Long v;

    /**
     * 法人（租户）
     */
    @Column(nullable = false, updatable = false)
    private String tenantId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 供应商
     */
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "supplier_id", updatable = false)
    private Supplier supplier;

    /**
     * 标题
     */
    @Column(nullable = false)
    private String title;

    /**
     * 黑名单原因
     */
    @Column(nullable = false, length = 4000)
    private String reason;

    /**
     * 处理状态
     */
    @Enumerated
    @Column(nullable = false)
    private FlowStatus status;

    /**
     * 审批通过时间
     */
    @Column
    private LocalDateTime passTime;

    /**
     * 是否生效
     */
    @Column(nullable = false)
    private Boolean valid;

    /**
     * 附件
     */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "supplier_blacklist_id")
    private List<SupplierBlacklistAttachment> attachments;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public FlowStatus getStatus() {
        return status;
    }

    public void setStatus(FlowStatus status) {
        this.status = status;
    }

    public LocalDateTime getPassTime() {
        return passTime;
    }

    public void setPassTime(LocalDateTime passTime) {
        this.passTime = passTime;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean invalid) {
        this.valid = invalid;
    }

    public List<SupplierBlacklistAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<SupplierBlacklistAttachment> attachments) {
        this.attachments = attachments;
    }
}
