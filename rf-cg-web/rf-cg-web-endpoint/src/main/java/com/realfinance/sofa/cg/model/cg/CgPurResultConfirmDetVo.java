package com.realfinance.sofa.cg.model.cg;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "采购方案执行-供应商对象")
public class CgPurResultConfirmDetVo {
    protected Integer id;

    @Schema(description = "采购目录")
    protected CgPurchaseCatalogVo purchaseCatalog;

    @Schema(description = "名称")
    protected String name;

    @Schema(description = "数量")
    protected Integer number;

    @Schema(description = "市场参考价/市场控制总价")
    protected BigDecimal marketPrice;

    @Schema(description = "质量和技术要求")
    protected String qualityRequirements;

    @Schema(description = "备注")
    protected String note;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CgPurchaseCatalogVo getPurchaseCatalog() {
        return purchaseCatalog;
    }

    public void setPurchaseCatalog(CgPurchaseCatalogVo purchaseCatalog) {
        this.purchaseCatalog = purchaseCatalog;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public String getQualityRequirements() {
        return qualityRequirements;
    }

    public void setQualityRequirements(String qualityRequirements) {
        this.qualityRequirements = qualityRequirements;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
