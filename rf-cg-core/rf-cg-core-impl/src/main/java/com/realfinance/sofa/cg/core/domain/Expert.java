package com.realfinance.sofa.cg.core.domain;

import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 专家
 */
@Entity
@Table(name = "CG_CORE_EXPERT")
public class Expert extends BaseEntity implements IEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 法人ID
     */
    @Column(nullable = false)
    private String tenantId;

    /**
     * 专家编码
     */
    @Column(nullable = false)
    private String expertCode;

    /**
     * 是否内部专家
     */
    @Column(nullable = false)
    private Boolean isInternalExpert;

    /**
     * 人员编码
     */
    @Column(nullable = false,unique = true)
    private Integer memberCode;

    /**
     * 姓名
     */
    @Column(nullable = false)
    private String name;

    /**
     * 联系电话
     */
    @Column(nullable = false)
    private String phone;

    /**
     * 邮箱
     */
    @Column(nullable = false)
    private String email;

    /**
     * 专家来源
     */
    @Column(nullable = false)
    private Boolean expertSource;

    /**
     * 专家类别
     */
    @Column(nullable = false)
    private ExpertType expertType;

    /**
     * 专家状态
     */
    @Enumerated
    @Column(nullable = false)
    private FlowStatus expertStatus;

    /**
     * 是否生效
     */
    @Column(nullable = false)
    private Boolean valid;

    /**
     * 审批通过时间
     */
    @Column
    private LocalDateTime passTime;

    /**
     * 部门
     */
    @Column(nullable = false)
    private Integer expertDepartment;

    /**
     * 制单人部门
     */
    @Column(nullable = false)
    private Integer departmentId;

    /**
     * 专家标签
     */
    @ManyToMany
    @JoinTable(name = "CG_CORE_EXPERT_EXPERT_LABEL",
            inverseJoinColumns = {@JoinColumn(name = "expert_label_id", referencedColumnName = "id")},
            joinColumns = {@JoinColumn(name = "expert_id", referencedColumnName = "id")})
    private Set<ExpertLabel> expertLabels;

    public String getExpertCode() {
        return expertCode;
    }

    public void setExpertCode(String expertCode) {
        this.expertCode = expertCode;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Boolean getInternalExpert() {
        return isInternalExpert;
    }

    public void setInternalExpert(Boolean internalExpert) {
        isInternalExpert = internalExpert;
    }

    public Integer getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(Integer memberCode) {
        this.memberCode = memberCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getExpertDepartment() {
        return expertDepartment;
    }

    public void setExpertDepartment(Integer expertDepartment) {
        this.expertDepartment = expertDepartment;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getExpertSource() {
        return expertSource;
    }

    public void setExpertSource(Boolean expertSource) {
        this.expertSource = expertSource;
    }

    public ExpertType getExpertType() {
        return expertType;
    }

    public void setExpertType(ExpertType expertType) {
        this.expertType = expertType;
    }

    public FlowStatus getExpertStatus() {
        return expertStatus;
    }

    public void setExpertStatus(FlowStatus expertStatus) {
        this.expertStatus = expertStatus;
    }

    public Set<ExpertLabel> getExpertLabels() {
        return expertLabels;
    }

    public void setExpertLabels(Set<ExpertLabel> expertLabels) {
        this.expertLabels = expertLabels;
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

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Integer getId() {
       return id;
   }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Expert{" +
                "id=" + id +
                ", tenantId='" + tenantId + '\'' +
                ", expertCode='" + expertCode + '\'' +
                ", isInternalExpert=" + isInternalExpert +
                ", memberCode=" + memberCode +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", expertSource=" + expertSource +
                ", expertType='" + expertType + '\'' +
                ", expertStatus=" + expertStatus +
                ", valid=" + valid +
                ", passTime=" + passTime +
                ", expertDepartment=" + expertDepartment +
                ", departmentId=" + departmentId +
                ", expertLabels=" + expertLabels +
                '}';
    }
}
