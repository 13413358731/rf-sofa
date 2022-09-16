package com.realfinance.sofa.cg.core.domain.exec;

import com.realfinance.sofa.cg.core.domain.BasePurAtt;

import javax.persistence.*;

@Entity
@Table(name = "CG_CORE_PROJ_EXEC_ATT")
public class ProjectExecutionAtt extends BasePurAtt {

    /**
     * 采购方案附件ID
     */
    @Column
    private Integer projAttId;

    /**
     * 环节类型
     */
    @Column
    private ProjectExecutionStepType stepType;

    /**
     * 环节数据ID
     */
    @Column
    private Integer stepDataId;

    /**
     * 是否已加密
     */
    private Boolean encrypted;

    /**
     * 文件标记
     */
    private String attSign;

    private Integer supplierId;

    @ManyToOne
    @JoinColumn(name = "proj_exec_id", updatable = false)
    private ProjectExecution projectExecution;

    public Integer getProjAttId() {
        return projAttId;
    }

    public void setProjAttId(Integer projAttId) {
        this.projAttId = projAttId;
    }

    public ProjectExecution getProjectExecution() {
        return projectExecution;
    }

    public void setProjectExecution(ProjectExecution projectExecution) {
        this.projectExecution = projectExecution;
    }

    public ProjectExecutionStepType getStepType() {
        return stepType;
    }

    public void setStepType(ProjectExecutionStepType stepType) {
        this.stepType = stepType;
    }

    public Integer getStepDataId() {
        return stepDataId;
    }

    public void setStepDataId(Integer stepDataId) {
        this.stepDataId = stepDataId;
    }

    public Boolean getEncrypted() {
        return encrypted;
    }

    public void setEncrypted(Boolean encrypted) {
        this.encrypted = encrypted;
    }

    public String getAttSign() {
        return attSign;
    }

    public void setAttSign(String attSign) {
        this.attSign = attSign;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }
}
