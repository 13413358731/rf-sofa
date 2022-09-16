package com.realfinance.sofa.cg.model.cg;

import com.realfinance.sofa.cg.model.BaseVo;
import com.realfinance.sofa.common.model.IdentityObject;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "资质认证")
public class CgQualityAuthVo extends BaseVo implements IdentityObject<Integer> {

    @Schema(description = "ID")
    protected Integer id;

    @Schema(description = "资质名称")
    protected String name;

    @Schema(description = "生效日期")
    protected LocalDate validTime;

    @Schema(description = "失效日期")
    protected LocalDate invalidTime;

    @Schema(description = "发证单位")
    protected String authorizeUnit;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getValidTime() {
        return validTime;
    }

    public void setValidTime(LocalDate validTime) {
        this.validTime = validTime;
    }

    public LocalDate getInvalidTime() {
        return invalidTime;
    }

    public void setInvalidTime(LocalDate invalidTime) {
        this.invalidTime = invalidTime;
    }

    public String getAuthorizeUnit() {
        return authorizeUnit;
    }

    public void setAuthorizeUnit(String authorizeUnit) {
        this.authorizeUnit = authorizeUnit;
    }
}
