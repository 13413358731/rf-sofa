package com.realfinance.sofa.cg.model.system;

import com.realfinance.sofa.system.model.RoleQueryCriteria;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springdoc.api.annotations.ParameterObject;

@ParameterObject
public class RoleQueryCriteriaRequest extends RoleQueryCriteria {
    /**
     * 编码
     */
    @Parameter(description = "编码", schema = @Schema(type = "string"))
    private String code;

    /**
     * 编码模糊
     */
    @Parameter(description = "编码模糊", schema = @Schema(type = "string"))
    private String codeLike;
    /**
     * 名字模糊
     */
    @Parameter(description = "名字模糊", schema = @Schema(type = "string"))
    private String nameLike;

    /**
     * 角色编码或名字模糊
     */
    @Parameter(description = "角色编码或名字模糊", schema = @Schema(type = "string"))
    private String codeLikeOrNameLike;
    /**
     * 是否可用
     */
    @Parameter(description = "是否可用", schema = @Schema(type = "string"))
    private Boolean enabled;
    /**
     * 法人ID
     */
    @Parameter(description = "法人ID", schema = @Schema(type = "string"))
    private String tenantId;
}
