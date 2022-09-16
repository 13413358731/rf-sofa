package com.realfinance.sofa.cg.model.cg;

import com.realfinance.sofa.cg.model.BaseVo;
import com.realfinance.sofa.common.model.IdentityObject;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "采购目录对象")
public class CgPurchaseCatalogVo extends BaseVo implements IdentityObject<Integer> {

    private Integer id;

    private CgPurchaseCatalogVo parent;

    /**
     * 编码
     */
    @Schema(description = "编码")
    private String code;

    /**
     * 名称
     */
    @Schema(description = "名称")
    private String name;

    /**
     * 子页数
     */
    @Schema(description = "子叶数")
    private Integer leafCount;

    private BigDecimal centralizedPurchasingLimit;

    private Boolean enabled;

    private String projectCategory;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public CgPurchaseCatalogVo getParent() {
        return parent;
    }

    public void setParent(CgPurchaseCatalogVo parent) {
        this.parent = parent;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLeafCount() {
        return leafCount;
    }

    public void setLeafCount(Integer leafCount) {
        this.leafCount = leafCount;
    }

    public BigDecimal getCentralizedPurchasingLimit() {
        return centralizedPurchasingLimit;
    }

    public void setCentralizedPurchasingLimit(BigDecimal centralizedPurchasingLimit) {
        this.centralizedPurchasingLimit = centralizedPurchasingLimit;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getProjectCategory() {
        return projectCategory;
    }

    public void setProjectCategory(String projectCategory) {
        this.projectCategory = projectCategory;
    }

}
