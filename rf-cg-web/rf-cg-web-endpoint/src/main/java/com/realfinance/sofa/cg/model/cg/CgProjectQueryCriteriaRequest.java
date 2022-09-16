package com.realfinance.sofa.cg.model.cg;

import com.realfinance.sofa.cg.core.model.CgProjectQueryCriteria;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springdoc.api.annotations.ParameterObject;

@ParameterObject
public class CgProjectQueryCriteriaRequest extends CgProjectQueryCriteria {

    /**
     * 名字模糊查询
     */
    @Parameter(description = "nameLike", schema = @Schema(type = "string"))
    private String nameLike;

    /**
     * 编码模糊查询
     */
    @Parameter(description = "projectNoLike", schema = @Schema(type = "string"))
    private String projectNoLike;


}
