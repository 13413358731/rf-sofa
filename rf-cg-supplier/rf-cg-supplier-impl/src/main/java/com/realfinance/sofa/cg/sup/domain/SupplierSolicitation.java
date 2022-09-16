package com.realfinance.sofa.cg.sup.domain;


import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 供应商意向征集
 */
@Entity
@Table(name = "CG_SUP_SUPPLIER_SOLICITATION")
public class SupplierSolicitation extends BaseEntity implements IEntity<Integer> {


    public SupplierSolicitation() {
        this.label = new ArrayList<>();
    }

    /**
     * 法人
     */
    @Column(updatable = false)
    private String tenantId;

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 版本
     */
    @Version
    private Long v;

    /**
     * 单据编号
     */
    @Column
    private String documentNumber;


    /**
     * 标题
     */
    @Column
    private String title;

    /**
     * 内容
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(length =1024)
    private String content;


    /**
     * 供应商标签
     */
    @ManyToMany
    @JoinTable(name = "CG_SUP_SUPPLIER_SOLICITATION_LABEL",
            joinColumns = {@JoinColumn(name = "supplier_solicitation_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "supplier_label_id", referencedColumnName = "id")})
    private List<SupplierLabel> label;


    /**
     * 开始时间
     */
    @Column
    private LocalDateTime startTime;

    /**
     * 截止时间
     */
    @Column
    private LocalDateTime endTime;




    /**
     * 审核状态
     */
    @Column
    @Enumerated
    private FlowStatus status;

    /**
     * 审核通过日期
     */
    @Column
    private LocalDateTime passDate;


    /**
     * 第二编辑人
     */
    @Column
    private Integer Editor;

    /**
     * 编辑时间
     */
    @Column
    private LocalDateTime editTime;


    /**
     * 发布状态 "0":未发布  "1":发布 "2":停用
     */
    @Column
    private String releaseStatus;


    /**
     * 意向征集附件
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "solicitation_id")
    private List<SupplierSolicitationAttachment> attachments;


    /**
     * 在此期间判断是否可见
     */
    public boolean getEnabled() {
        if (startTime == null && endTime == null) {
            return true;
        } else {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime start = startTime == null ? LocalDateTime.MIN : startTime;
            LocalDateTime end = endTime == null ? LocalDateTime.MAX : endTime;
            return now.isBefore(start) || now.isAfter(end);
        }
    }


    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Long getV() {
        return v;
    }

    public void setV(Long v) {
        this.v = v;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<SupplierLabel> getLabel() {
        return label;
    }

    public void setLabel(List<SupplierLabel> label) {
        this.label = label;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }


    public FlowStatus getStatus() {
        return status;
    }

    public void setStatus(FlowStatus status) {
        this.status = status;
    }

    public Integer getEditor() {
        return Editor;
    }

    public void setEditor(Integer editor) {
        Editor = editor;
    }

    public LocalDateTime getEditTime() {
        return editTime;
    }

    public void setEditTime(LocalDateTime editTime) {
        this.editTime = editTime;
    }

    public LocalDateTime getPassDate() {
        return passDate;
    }

    public void setPassDate(LocalDateTime passDate) {
        this.passDate = passDate;
    }

    public String getReleaseStatus() {
        return releaseStatus;
    }

    public void setReleaseStatus(String releaseStatus) {
        this.releaseStatus = releaseStatus;
    }

    public List<SupplierSolicitationAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<SupplierSolicitationAttachment> attachments) {
        this.attachments = attachments;
    }
}
