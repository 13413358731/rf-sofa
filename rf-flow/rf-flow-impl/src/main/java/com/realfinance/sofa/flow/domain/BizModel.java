package com.realfinance.sofa.flow.domain;

import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;

/**
 * 业务绑定模型
 */
@Entity
@Table(name = "FLOW_BIZ_MODEL")
public class BizModel extends BaseEntity implements IEntity<Integer> {

    @Version
    private Long v;

    @Column(nullable = false, updatable = false)
    private String tenantId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 模型ID
     */
    @Column(nullable = false, updatable = false)
    private String modelId;

    /**
     * 部署ID
     */
    @Column
    private String deploymentId;

    /**
     * 业务ID
     */
    @ManyToOne
    @JoinColumn(name = "biz_id", nullable = false, updatable = false)
    private Biz biz;

    /**
     * 流程实例名称
     */
    @Column(nullable = false)
    private String processInstName;

    /**
     * 部门ID
     */
    @Column(nullable = false, updatable = false)
    private Integer departmentId;

    /**
     * 是否可用
     */
    @Column(nullable = false)
    private Boolean enabled;

    /**
     * 模型Key
     * @return
     */
    public String getModelKey() {
        return "BM_" + getBiz().getId() + "_" + departmentId;
    }

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

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public Biz getBiz() {
        return biz;
    }

    public void setBiz(Biz biz) {
        this.biz = biz;
    }

    public String getProcessInstName() {
        return processInstName;
    }

    public void setProcessInstName(String processInstName) {
        this.processInstName = processInstName;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
