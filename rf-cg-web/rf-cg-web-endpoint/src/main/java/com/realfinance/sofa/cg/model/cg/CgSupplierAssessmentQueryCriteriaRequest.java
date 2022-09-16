package com.realfinance.sofa.cg.model.cg;

import com.realfinance.sofa.cg.sup.model.CgSupplierAssessmentQueryCriteria;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springdoc.api.annotations.ParameterObject;

@ParameterObject
public class CgSupplierAssessmentQueryCriteriaRequest extends CgSupplierAssessmentQueryCriteria {

    /**
     * id
     */
    @Parameter(description = "id", schema = @Schema(type = "string"))
    private String id;


}
