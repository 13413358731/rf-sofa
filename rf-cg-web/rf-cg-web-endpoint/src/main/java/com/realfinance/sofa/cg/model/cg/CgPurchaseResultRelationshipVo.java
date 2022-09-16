package com.realfinance.sofa.cg.model.cg;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.realfinance.sofa.cg.model.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "采购结果-供应商关联关系对象")
public class CgPurchaseResultRelationshipVo extends BaseVo {

    private Integer id;
    /**
     * 关联序号(组号)
     */
    @Schema(description = "关联序号(组号)")
    private Integer number;

    //关联类型
    @Schema(description = "关联类型(1-5)")
    private Integer type;

    //实际控股
    @Schema(description = "实际控股")
    private Double proportion;

    /**
     * 公司名称(企业名称)
     */
    @Schema(description = "公司名称(企业名称)")
    private String name;

    /**
     * 统一社会信用代码
     */
    @Schema(description = "统一社会信用代码")
    private String unifiedSocialCreditCode;

    /**
     * 法定代表人
     */
    @Schema(description = "法定代表人")
    private String statutoryRepresentative;

    /**
     *股东名称
     */
    @Schema(description = "股东名称")
    private String shareholderNames;

    /**
     *股东类型
     */
    @Schema(description = "股东类型")
    private String shareholderTypes;

    /**
     * 出资比例(股权占比)
     */
    @Schema(description = "出资比例(股权占比)")
    private String equityRatio;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Double getProportion() {
        return proportion;
    }

    public void setProportion(Double proportion) {
        this.proportion = proportion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnifiedSocialCreditCode() {
        return unifiedSocialCreditCode;
    }

    public void setUnifiedSocialCreditCode(String unifiedSocialCreditCode) {
        this.unifiedSocialCreditCode = unifiedSocialCreditCode;
    }

    public String getStatutoryRepresentative() {
        return statutoryRepresentative;
    }

    public void setStatutoryRepresentative(String statutoryRepresentative) {
        this.statutoryRepresentative = statutoryRepresentative;
    }

    public String getShareholderNames() {
        return shareholderNames;
    }

    public void setShareholderNames(String shareholderNames) {
        this.shareholderNames = shareholderNames;
    }

    public String getShareholderTypes() {
        return shareholderTypes;
    }

    public void setShareholderTypes(String shareholderTypes) {
        this.shareholderTypes = shareholderTypes;
    }

    public String getEquityRatio() {
        return equityRatio;
    }

    public void setEquityRatio(String equityRatio) {
        this.equityRatio = equityRatio;
    }
}
