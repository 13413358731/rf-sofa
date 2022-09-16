package com.realfinance.sofa.cg.model.cg;

import com.realfinance.sofa.cg.sup.model.CgSupplierQueryCriteria;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springdoc.api.annotations.ParameterObject;

@ParameterObject
public class CgSupplierQueryCriteriaRequest extends CgSupplierQueryCriteria {

    /**
     * 统一社会信用代码
     */
    @Parameter(description = "统一社会信用代码", schema = @Schema(type = "string"))
    private String unifiedSocialCreditCode;

    /**
     * 供应商名称模糊
     */
    @Parameter(description = "供应商名称模糊", schema = @Schema(type = "string"))
    private String nameLike;

    /**
     * 供应商账号用户名
     */
    @Parameter(description = "供应商账号用户名", schema = @Schema(type = "string"))
    private String username;

}
