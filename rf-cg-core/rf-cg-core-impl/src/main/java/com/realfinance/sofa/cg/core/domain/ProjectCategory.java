package com.realfinance.sofa.cg.core.domain;

/**
 * 项目类别
 */
public enum ProjectCategory {

    GGXC("01","广告宣传"),
    AQBW("02","安全保卫"),
    JJZX("03","基建装修"),
    JFLP("04","积分礼品"),
    DZKJ("05","电子科技"),
    ZCPM("06","资产拍卖"),
    RCYP("07","日常用品"),
    QT("08","其他"),
    YW("09","运维"),
    ;
    String code;
    String zh;

    ProjectCategory(String code, String zh) {
        this.code = code;
        this.zh = zh;
    }

    public String getCode() {
        return code;
    }

    public String getZh() {
        return zh;
    }
}
