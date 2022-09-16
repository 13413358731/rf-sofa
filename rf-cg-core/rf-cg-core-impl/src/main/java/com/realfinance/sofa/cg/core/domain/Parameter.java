package com.realfinance.sofa.cg.core.domain;


import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;

/**
 * 参数
 */
@Entity
@Table(name = "CG_CORE_PARAMETER")
public class Parameter extends BaseEntity implements IEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 法人ID
     */
    @Column(nullable = false)
    private String tenantId;


    /**
     * 参数编码
     */
    private String parameterCode;



    /**
     * 参数名称
     */
    private String parameterName;



    /**
     * 参数描述
     */
    private String parameterDescription;



    /**
     * 参数值
     */
    private String value;

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

    public String getParameterCode() {
        return parameterCode;
    }

    public void setParameterCode(String parameterCode) {
        this.parameterCode = parameterCode;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getParameterDescription() {
        return parameterDescription;
    }

    public void setParameterDescription(String parameterDescription) {
        this.parameterDescription = parameterDescription;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
