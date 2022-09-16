package com.realfinance.sofa.cg.model.cg;

import com.realfinance.sofa.cg.sup.model.CgSupplierExaminationQueryCriteria;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springdoc.api.annotations.ParameterObject;

import java.util.Set;

@ParameterObject
public class CgSupplierExaminationQueryCriteriaRequest extends CgSupplierExaminationQueryCriteria {
    /**
     * 统一社会信用代码
     */
    @Parameter(description = "统一社会信用代码", schema = @Schema(type = "string"))
    private String unifiedSocialCreditCode;

    /**
     * 业务类型（新注册，门户信息修改，系统内信息修改）
     */
    @Parameter(description = "业务类型（新注册，门户信息修改，系统内信息修改）", schema = @Schema(type = "string"))
    private String category;

    /**
     * 供应商名称模糊
     */
    @Parameter(description = "供应商名称模糊", schema = @Schema(type = "string"))
    private String nameLike;

    /**
     * 供应商账号ID
     */
    @Parameter(description = "供应商账号ID", schema = @Schema(type = "integer"))
    private Integer accountId;

    /**
     * 供应商用户名
     */
    @Parameter(description = "供应商用户名", schema = @Schema(type = "string"))
    private String username;

    /**
     * 类型In
     */
    @Parameter(description = "类型范围", array = @ArraySchema(schema = @Schema(type = "string")))
    private Set<String> categoryIn;

    /**
     * 处理状态
     */
    @Parameter(description = "处理状态", schema = @Schema(type = "string"))
    private String status;
}
