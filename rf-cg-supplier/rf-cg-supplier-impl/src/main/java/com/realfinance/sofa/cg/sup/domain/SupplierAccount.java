package com.realfinance.sofa.cg.sup.domain;

import com.realfinance.sofa.common.model.IEntity;

import javax.annotation.MatchesPattern;
import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 供应商账号
 */
@Entity
@Table(name = "CG_SUP_SUPPLIER_ACCOUNT")
public class SupplierAccount extends BaseEntity implements IEntity<Integer> {

    public SupplierAccount() {
        this.examinations = new ArrayList<>();
    }

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
     * 用户名
     */
    @Column(nullable = false, unique = true, length = 20)
    @Size(min = 6, max = 20)
    @Pattern(regexp = "[a-zA-Z0-9_]{6,20}")
    private String username;
    /**
     * 密码
     */
    @Column(nullable = false)
    private String password;

    /**
     * 账号类型（自主注册，邀请注册）
     */
    @Column(nullable = false, updatable = false, length = 20)
    private String type;

    /**
     * 账户绑定的手机
     */
    @Pattern(regexp = "(?:0|86|\\+86)?1[3456789]\\d{9}")
    @Column(length = 15)
    private String mobile;

    /**
     * 是否启用
     */
    @Column(nullable = false)
    private Boolean enabled;

    /**
     * 密码修改时间
     */
    @Column
    private LocalDateTime passwordModifiedTime;

    @OneToMany(mappedBy = "account")
    private List<SupplierExamination> examinations;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public LocalDateTime getPasswordModifiedTime() {
        return passwordModifiedTime;
    }

    public void setPasswordModifiedTime(LocalDateTime passwordModifiedTime) {
        this.passwordModifiedTime = passwordModifiedTime;
    }

    public List<SupplierExamination> getExaminations() {
        return examinations;
    }

    public void setExaminations(List<SupplierExamination> examinations) {
        this.examinations = examinations;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}
