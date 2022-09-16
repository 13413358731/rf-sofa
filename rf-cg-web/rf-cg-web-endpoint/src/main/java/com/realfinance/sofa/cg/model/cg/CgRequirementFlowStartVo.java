package com.realfinance.sofa.cg.model.cg;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "采购申请启动流程vo类")
public class CgRequirementFlowStartVo  {
    private Map<String, String> formData;

    private CgRequirementVo cgRequirementVo;

    public Map<String, String> getFormData() {
        return formData;
    }

    public void setFormData(Map<String, String> formData) {
        this.formData = formData;
    }

    public CgRequirementVo getCgRequirementVo() {
        return cgRequirementVo;
    }

    public void setCgRequirementVo(CgRequirementVo cgRequirementVo) {
        this.cgRequirementVo = cgRequirementVo;
    }
}
