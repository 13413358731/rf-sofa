package com.realfinance.sofa.cg.model.system;

import com.realfinance.sofa.system.model.TenantQueryCriteria;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springdoc.api.annotations.ParameterObject;

@ParameterObject
public class TenantQueryCriteriaRequest extends TenantQueryCriteria {
    /**
     * ID
     */
    @Parameter(description = "ID（编码）", schema = @Schema(type = "string"))
    private String Id;
    /**
     * 名字模糊
     */
    @Parameter(description = "名字模糊", schema = @Schema(type = "string"))
    private String nameLike;
    /**
     * 是否可用
     */
    @Parameter(description = "是否可用", schema = @Schema(type = "boolean"))
    private Boolean enabled;
}
