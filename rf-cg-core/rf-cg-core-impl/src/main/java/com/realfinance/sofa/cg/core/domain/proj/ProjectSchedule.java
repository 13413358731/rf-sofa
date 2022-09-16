package com.realfinance.sofa.cg.core.domain.proj;

import com.realfinance.sofa.cg.core.domain.BaseEntity;
import com.realfinance.sofa.cg.core.domain.req.Requirement;
import com.realfinance.sofa.common.model.IEntity;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import java.time.LocalDateTime;

/**
 * @author hhq
 * @date 2021/6/21 - 17:52
 */
@Entity
@Table(name = "CG_CORE_PROJ_SCHEDULE")
public class ProjectSchedule extends BaseEntity implements IEntity<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String tenantId;

    @Version
    private Long v;

    @Column
    private String projectName;

    @Column
    private Integer projectStage;

    @Column
    private Integer projectStatus;

    @Column
    private Integer contractStatus;

    @Column
    private LocalDateTime reqSubmitTime;

    @Column
    private LocalDateTime reqPassTime;

    @Column
    private Integer reqStandardDays;

    @Column
    private Integer reqDays;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "req_id", updatable = false)
    private Requirement requirement;

    @Column(nullable = false)
    private Boolean attention;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "projectScheduleUserId")
    private ProjectScheduleUser projectScheduleUser;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Long getV() {
        return v;
    }

    public void setV(Long v) {
        this.v = v;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getProjectStage() {
        return projectStage;
    }

    public void setProjectStage(Integer projectStage) {
        this.projectStage = projectStage;
    }

    public Integer getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(Integer projectStatus) {
        this.projectStatus = projectStatus;
    }

    public Integer getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(Integer contractStatus) {
        this.contractStatus = contractStatus;
    }

    public LocalDateTime getReqSubmitTime() {
        return reqSubmitTime;
    }

    public void setReqSubmitTime(LocalDateTime reqSubmitTime) {
        this.reqSubmitTime = reqSubmitTime;
    }

    public LocalDateTime getReqPassTime() {
        return reqPassTime;
    }

    public void setReqPassTime(LocalDateTime reqPassTime) {
        this.reqPassTime = reqPassTime;
    }

    public Integer getReqStandardDays() {
        return reqStandardDays;
    }

    public void setReqStandardDays(Integer reqStandardDays) {
        this.reqStandardDays = reqStandardDays;
    }

    public Integer getReqDays() {
        return reqDays;
    }

    public void setReqDays(Integer reqDays) {
        this.reqDays = reqDays;
    }

    public Requirement getRequirement() {
        return requirement;
    }

    public void setRequirement(Requirement requirement) {
        this.requirement = requirement;
    }

    public Boolean getAttention() {
        return attention;
    }

    public void setAttention(Boolean attention) {
        this.attention = attention;
    }

    public ProjectScheduleUser getProjectScheduleUser() {
        return projectScheduleUser;
    }

    public void setProjectScheduleUser(ProjectScheduleUser projectScheduleUser) {
        this.projectScheduleUser = projectScheduleUser;
    }
}
