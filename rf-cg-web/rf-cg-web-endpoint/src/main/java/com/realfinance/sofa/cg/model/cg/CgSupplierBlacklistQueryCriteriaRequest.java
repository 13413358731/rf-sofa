package com.realfinance.sofa.cg.model.cg;

import com.realfinance.sofa.cg.sup.model.CgSupplierBlacklistQueryCriteria;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springdoc.api.annotations.ParameterObject;

@ParameterObject
public class CgSupplierBlacklistQueryCriteriaRequest extends CgSupplierBlacklistQueryCriteria {

    @Parameter(description = "供应商ID", schema = @Schema(type = "integer"))
    private Integer supplierId;
    @Parameter(description = "标题模糊", schema = @Schema(type = "string"))
    private String titleLike;
    @Parameter(description = "处理状态", schema = @Schema(type = "string"))
    private String status;
}
