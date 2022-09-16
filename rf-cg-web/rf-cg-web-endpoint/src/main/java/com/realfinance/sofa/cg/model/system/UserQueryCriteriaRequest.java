package com.realfinance.sofa.cg.model.system;

import com.realfinance.sofa.system.model.UserQueryCriteria;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springdoc.api.annotations.ParameterObject;

@ParameterObject
public class UserQueryCriteriaRequest extends UserQueryCriteria {

    /**
     * 用户名模糊
     */
    @Parameter(description = "用户名模糊", schema = @Schema(type = "string"))
    private String usernameLike;
    /**
     * 真实姓名模糊
     */
    @Parameter(description = "真实姓名模糊", schema = @Schema(type = "string"))
    private String realnameLike;
    /**
     * 用户名或姓名模糊查询
     */
    @Parameter(description = "用户名或姓名模糊查询", schema = @Schema(type = "string"))
    private String usernameLikeOrRealnameLike;
    /**
     * 邮箱
     */
    @Parameter(description = "邮箱", schema = @Schema(type = "string"))
    private String email;
    /**
     * 手机号
     */
    @Parameter(description = "手机号", schema = @Schema(type = "string"))
    private String mobile;
    /**
     * 是否可用
     */
    @Parameter(description = "是否可用", schema = @Schema(type = "boolean"))
    private Boolean enabled;
    /**
     * 分类
     */
    @Parameter(description = "分类", schema = @Schema(type = "string"))
    private String classification;
    /**
     * 租户ID
     */
    @Parameter(description = "租户ID", schema = @Schema(type = "string"))
    private String tenantId;
    /**
     * 部门ID
     */
    @Parameter(description = "部门ID", schema = @Schema(type = "integer"))
    private Integer departmentId;
}
