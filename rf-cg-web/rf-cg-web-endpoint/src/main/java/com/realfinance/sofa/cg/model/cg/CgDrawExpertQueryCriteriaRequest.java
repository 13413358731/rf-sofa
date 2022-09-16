package com.realfinance.sofa.cg.model.cg;

import com.realfinance.sofa.cg.core.model.CgDrawExpertQueryCriteria;
import com.realfinance.sofa.cg.core.model.CgExpertQueryCriteria;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springdoc.api.annotations.ParameterObject;

@ParameterObject
public class CgDrawExpertQueryCriteriaRequest extends CgDrawExpertQueryCriteria {

    /**
     * id
     */
    @Parameter(description = "id", schema = @Schema(type = "string"))
    private String id;


}
