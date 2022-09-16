package com.realfinance.sofa.cg.core.domain.proj;

import com.realfinance.sofa.cg.core.domain.BaseEntity;
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

/**
 * @author hhq
 * @date 2021/6/21 - 17:52
 */
@Entity
@Table(name = "CG_CORE_PROJ_SCHEDULE_USER")
public class ProjectScheduleUser extends BaseEntity implements IEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

   /* @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "schedule_id", updatable = false)
    private ProjectSchedule projectSchedule;*/

    @Column(nullable = false)
    private Boolean attention;

    @Column(nullable = false)
    private String tenantId;

    @Version
    private Long v;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    /*public ProjectSchedule getProjectSchedule() {
        return projectSchedule;
    }

    public void setProjectSchedule(ProjectSchedule projectSchedule) {
        this.projectSchedule = projectSchedule;
    }*/

    public Boolean getAttention() {
        return attention;
    }

    public void setAttention(Boolean attention) {
        this.attention = attention;
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
}
