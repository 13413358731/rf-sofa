package com.realfinance.sofa.cg.model.cg;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.realfinance.sofa.cg.model.BaseVo;
import com.realfinance.sofa.common.model.IdentityObject;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "资格性审查转换类")
public class CgAuditQualConvertVo extends BaseVo implements IdentityObject<Integer> {
    private Integer id;

    private List<Object> auditQualItems;

    /**
     * 资格性审查， 动态表单
     */
    @Schema(description = "报价信息")
    private Object auditQualInfo;

    @Override
    public Integer getId() {
        return id;
    }

    public List<Object> getAuditQualItems() {
        return auditQualItems;
    }

    public void setAuditQualItems(List<Object> auditQualItems) {
        this.auditQualItems = auditQualItems;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Object getAuditQualInfo() {
        return auditQualInfo;
    }

    public void setAuditQualInfo(Object auditQualInfo) {
        this.auditQualInfo = auditQualInfo;
    }
}
