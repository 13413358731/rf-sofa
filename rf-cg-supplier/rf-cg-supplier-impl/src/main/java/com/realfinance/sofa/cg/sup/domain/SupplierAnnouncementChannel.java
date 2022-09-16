package com.realfinance.sofa.cg.sup.domain;

import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 公告频道
 */
@Table(name = "CG_SUP_SUPPLIER_ANNOUNCEMENT_CHANNEL",indexes = {
        @Index(columnList = "channelNo,tenantId", unique = true),
})
@Entity
public class SupplierAnnouncementChannel extends BaseEntity implements IEntity<Integer> {
    /**
     * 法人（租户）
     */
    @Column(nullable = false, updatable = false)
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
     * 频道编号
     */
    @Column(nullable = false)
    private Integer channelNo;


    /**
     * 频道名称
     */
    @Column(nullable = false)
    private String channelName;

    /**
     * 频道类型
     */
    @Column(nullable = false)
    private String channelType;

    /**
     * 备注
     */
    @Column
    private String remarks;


    @OneToMany
    @JoinColumn(name = "channels_id", referencedColumnName = "id")
    private Set<SupplierAnnouncement> announcements = new HashSet<>();

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

    public Long getV() {
        return v;
    }

    public void setV(Long v) {
        this.v = v;
    }

    public Integer getChannelNo() {
        return channelNo;
    }

    public void setChannelNo(Integer channelNo) {
        this.channelNo = channelNo;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Set<SupplierAnnouncement> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(Set<SupplierAnnouncement> announcements) {
        this.announcements = announcements;
    }
}
