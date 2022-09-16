package com.realfinance.sofa.cg.model.cg;

import com.realfinance.sofa.cg.core.model.CgExpertLabelQueryCriteria;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springdoc.api.annotations.ParameterObject;

@ParameterObject
public class CgExpertLabelQueryCriteriaRequest extends CgExpertLabelQueryCriteria {

    /**
     * id
     */
    @Parameter(description = "id", schema = @Schema(type = "string"))
    private String id;

    /**
     * 标签名称模糊搜索
     */
    @Parameter(description = "标签名称模糊搜索", schema = @Schema(type = "string"))
    private String nameLike;

}
