package com.realfinance.sofa.cg.model.cg;

import com.realfinance.sofa.cg.model.BaseVo;
import com.realfinance.sofa.common.model.IdentityObject;

public class ParameterVo extends BaseVo implements IdentityObject<Integer> {

    private Integer id;
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
