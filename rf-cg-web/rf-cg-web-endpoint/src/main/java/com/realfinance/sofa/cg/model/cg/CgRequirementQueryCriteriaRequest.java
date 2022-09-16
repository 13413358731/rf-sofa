package com.realfinance.sofa.cg.model.cg;

import com.realfinance.sofa.cg.core.model.CgRequirementQueryCriteria;
import com.realfinance.sofa.cg.sup.model.CgSupplierBlacklistQueryCriteria;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springdoc.api.annotations.ParameterObject;

import java.math.BigDecimal;

@ParameterObject
public class CgRequirementQueryCriteriaRequest extends CgRequirementQueryCriteria {

    @Parameter(description = "采购申请名称", schema = @Schema(type = "string"))
    private String name;
    @Parameter(description = "申请采购金额", schema = @Schema(type = "BigDecimal"))
    private BigDecimal reqTotalAmount;
    @Parameter(description = "市场参考价/市场控制总价", schema = @Schema(type = "BigDecimal"))
    private BigDecimal marketPrice;
    @Parameter(description = "申请部门", schema = @Schema(type = "integer"))
    private Integer departmentId;
    @Parameter(description = "申请人", schema = @Schema(type = "integer"))
    private Integer createdUserId;
    @Parameter(description = "采购部门", schema = @Schema(type = "integer"))
    private Integer purDepartmentId;
    @Parameter(description = "受理状态", schema = @Schema(type = "string"))
    private String acceptStatus;
    @Parameter(description = "单据状态", schema = @Schema(type = "string"))
    private String status;
}
