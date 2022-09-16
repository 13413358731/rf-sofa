package com.realfinance.sofa.cg.core.domain.exec;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 采购方案执行-环节对象
 */
@Entity
@Table(name = "CG_CORE_PROJ_EXEC_STEP")
public class ProjectExecutionStep {

    @Version
    private Long v;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "proj_exec_id", updatable = false)
    private ProjectExecution projectExecution;

    @Enumerated
    @Column(nullable = false)
    private ProjectExecutionStepType type;

    /**
     * 环节开始时间
     */
    @Column
    private LocalDateTime startTime;

    /**
     * 环节结束时间
     */
    @Column
    private LocalDateTime endTime;

    public Long getV() {
        return v;
    }

    public void setV(Long v) {
        this.v = v;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProjectExecution getProjectExecution() {
        return projectExecution;
    }

    public void setProjectExecution(ProjectExecution projectExecution) {
        this.projectExecution = projectExecution;
    }

    public ProjectExecutionStepType getType() {
        return type;
    }

    public void setType(ProjectExecutionStepType type) {
        this.type = type;
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
}
