package com.realfinance.sofa.cg.core.domain;

/**
 * 项目特征
 */
public enum ProjectFeatures {

    YXTWH("原系统维护"),
    YXTXKXQ("原系统许可续期"),
    YXTYHSJ("原系统优化升级"),
    JBZLTX("具备专利特性"),
    QT("其他");

    private String zh;

    ProjectFeatures(String zh) {
        this.zh = zh;
    }

    public String getZh() {
        return zh;
    }
}
