package com.realfinance.sofa.cg.model.system;

import com.realfinance.sofa.system.model.RoleGroupQueryCriteria;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springdoc.api.annotations.ParameterObject;

@ParameterObject
public class RoleGroupQueryCriteriaRequest extends RoleGroupQueryCriteria {
    /**
     * 租户ID
     */
    @Parameter(description = "法人ID", schema = @Schema(type = "string"))
    private String tenantId;

    /**
     * 角色组编码
     */
    @Parameter(description = "角色组编码", schema = @Schema(type = "string"))
    private String code;
    /**
     * 名称
     */
    @Parameter(description = "名称", schema = @Schema(type = "string"))
    private String name;

    /**
     * 角色组编码或角色组名称模糊
     */
    @Parameter(description = "角色组编码或角色组名称模糊", schema = @Schema(type = "string"))
    private String codeLikeOrNameLike;

    /**
     * 是否可用
     */
    @Parameter(description = "是否可用", schema = @Schema(type = "boolean"))
    private Boolean enabled;
}
