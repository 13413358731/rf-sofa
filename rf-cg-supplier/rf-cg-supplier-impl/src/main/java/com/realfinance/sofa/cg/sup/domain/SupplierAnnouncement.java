package com.realfinance.sofa.cg.sup.domain;


import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 公告
 */
@Entity
@Table(name = "CG_SUP_SUPPLIER_ANNOUNCEMENT")
public class SupplierAnnouncement extends BaseEntity implements IEntity<Integer> {


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
     * 是否置顶
     */
    @Column
    private Boolean isTop;

    /**
     * 标题
     */
    @Column
    private String title;

    /**
     * 生效时间
     */
    @Column
    private LocalDateTime takeEffectTime;

    /**
     * 结束时间
     */
    @Column
    private LocalDateTime endTime;

    /**
     * 公告类型  "0" 表示内网，"1" 表示外网
     */
    @Column
    private String type;


    /**
     * 公告频道
     */
    @ManyToOne
    @JoinColumn(name = "channels_id", referencedColumnName = "id")
    private SupplierAnnouncementChannel channels;


    /**
     * 公告状态 "0" 表示未发布，
     * "1"表示发布，
     * "2"表示停用
     */
    @Column
    private String releasestatus;


    /**
     * 发布日期
     */
    @Column
    private LocalDateTime releaseDate;


    /**
     * 停用人
     */
    @Column
    private String disabledMan;

    /**
     * 停用日期
     */
    @Column
    private LocalDateTime stopDate;


    /**
     * 法人，即制单人公司
     */
    @Column
    private String tenantId;

    /**
     * 制单人部门
     */
    @Column
    private Integer departmentId;

    /**
     * 审核通过日期
     */
    @Column
    private LocalDateTime passDate;

    /**
     * 审核状态
     */
    @Column
    @Enumerated
    private FlowStatus status;

    /**
     * 内容
     */
    @Column
    private String content;

    /**
     * 备注
     */
    @Column
    private String remarks;

    /**
     * 附件
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "announcement_id")
    private List<SupplierAnnouncementAttachment> attachments;

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

    public Boolean getTop() {
        return isTop;
    }

    public void setTop(Boolean top) {
        isTop = top;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getTakeEffectTime() {
        return takeEffectTime;
    }

    public void setTakeEffectTime(LocalDateTime takeEffectTime) {
        this.takeEffectTime = takeEffectTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public SupplierAnnouncementChannel getChannels() {
        return channels;
    }

    public void setChannels(SupplierAnnouncementChannel channels) {
        this.channels = channels;
    }

    public String getReleasestatus() {
        return releasestatus;
    }

    public void setReleasestatus(String releasestatus) {
        this.releasestatus = releasestatus;
    }

    public LocalDateTime getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDateTime releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDisabledMan() {
        return disabledMan;
    }

    public void setDisabledMan(String disabledMan) {
        this.disabledMan = disabledMan;
    }

    public LocalDateTime getStopDate() {
        return stopDate;
    }

    public void setStopDate(LocalDateTime stopDate) {
        this.stopDate = stopDate;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public LocalDateTime getPassDate() {
        return passDate;
    }

    public void setPassDate(LocalDateTime passDate) {
        this.passDate = passDate;
    }

    public FlowStatus getStatus() {
        return status;
    }

    public void setStatus(FlowStatus status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public List<SupplierAnnouncementAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<SupplierAnnouncementAttachment> attachments) {
        this.attachments = attachments;
    }
}
