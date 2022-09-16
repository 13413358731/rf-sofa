package com.realfinance.sofa.cg.model.cg;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.realfinance.sofa.cg.core.model.CgRequirementOaDatumDto;
import com.realfinance.sofa.cg.model.BaseVo;
import com.realfinance.sofa.cg.model.flow.FlowInfoVo;
import com.realfinance.sofa.cg.model.flow.FlowTaskVo;
import com.realfinance.sofa.cg.model.flow.FlowableVo;
import com.realfinance.sofa.cg.model.system.DepartmentVo;
import com.realfinance.sofa.cg.model.system.UserVo;
import com.realfinance.sofa.common.model.IdentityObject;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CgRequirementFlowableVo {
    private CgRequirementVo cgRequirementVo;

    private FlowTaskVo flowTaskVo;

    public CgRequirementVo getCgRequirementVo() {
        return cgRequirementVo;
    }

    public void setCgRequirementVo(CgRequirementVo cgRequirementVo) {
        this.cgRequirementVo = cgRequirementVo;
    }

    public FlowTaskVo getFlowTaskVo() {
        return flowTaskVo;
    }

    public void setFlowTaskVo(FlowTaskVo flowTaskVo) {
        this.flowTaskVo = flowTaskVo;
    }
}
