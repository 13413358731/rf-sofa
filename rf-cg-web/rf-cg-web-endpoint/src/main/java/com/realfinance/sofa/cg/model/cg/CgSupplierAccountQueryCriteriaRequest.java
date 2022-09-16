package com.realfinance.sofa.cg.model.cg;

import com.realfinance.sofa.cg.sup.model.CgSupplierAccountQueryCriteria;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springdoc.api.annotations.ParameterObject;

@ParameterObject
public class CgSupplierAccountQueryCriteriaRequest extends CgSupplierAccountQueryCriteria {
    @Parameter(description = "用户名模糊", schema = @Schema(type = "string"))
    private String usernameLike;
    @Parameter(description = "手机号", schema = @Schema(type = "string"))
    private String mobile;
    @Parameter(description = "账号类型（自主注册，邀请注册）", schema = @Schema(type = "string"))
    private String type;
    @Parameter(description = "是否启用", schema = @Schema(type = "boolean"))
    private Boolean enabled;
}
