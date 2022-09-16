package com.realfinance.sofa.cg.core.domain.exec;

import com.realfinance.sofa.cg.core.domain.BasePurSup;
import com.realfinance.sofa.cg.core.domain.DrawExpert;

import javax.persistence.*;

/**
 * 供应商看板
 */
@Entity
@Table(name = "CG_CORE_PROJ_EXEC_SUP")
public class ProjectExecutionSup extends BasePurSup {

    /**
     * 变更方式
     */
    public enum ModifyMode {
        XZ, // 新增
        TT, // 淘汰
        W, //无
    }

    /**
     * 原采购方案推荐ID
     */
    @Column
    protected Integer projSupId;

    @Column
    @Enumerated
    private ModifyMode modifyMode;

    /**
     * 变更时环节
     */
    private String modifyStep;

    @ManyToOne
    @JoinColumn(name = "proj_exec_id")
    private ProjectExecution projectExecution;

    public Integer getProjSupId() {
        return projSupId;
    }

    public void setProjSupId(Integer reqSupId) {
        this.projSupId = reqSupId;
    }

    public ModifyMode getModifyMode() {
        return modifyMode;
    }

    public void setModifyMode(ModifyMode modifyMode) {
        this.modifyMode = modifyMode;
    }

    public String getModifyStep() {
        return modifyStep;
    }

    public void setModifyStep(String modifyStep) {
        this.modifyStep = modifyStep;
    }

    public ProjectExecution getProjectExecution() {
        return projectExecution;
    }

    public void setProjectExecution(ProjectExecution projectExecution) {
        this.projectExecution = projectExecution;
    }
}
