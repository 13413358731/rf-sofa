package com.realfinance.sofa.cg.core.domain;


/**
 * 参会人员类型
 */
public enum ExpertType {
    CGJBR("采购经办人"),
    HGBJDR("合规部监督人"),
    JWJDR("纪委监督人"),
    PBZJ("评标专家");

    String zh;

    ExpertType(String zh) {
        this.zh = zh;
    }

    public String getZh() {
        return zh;
    }
}
