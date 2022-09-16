package com.realfinance.sofa.cg.core.domain.exec;

public enum ProjectExecutionStepType {
    /**
     * 征集/应答
     */
//    ZJYD,
    /**
     * 审查设置(制作标书)
     */
    ZZBS("审查设置"),
    /**
     * 发文与应答(发标/应答)
     */
    FBYD("发文与应答"),
    /**
     * 唱标
     */
    CB("唱标"),
    /**
     * 抽取专家
     */
    CQZJ("抽取专家"),
    /**
     * 评审(会议)
     */
    HY("评审"),
    /**
     * 澄清与报价(商务澄清，发布/应答)
     */
    SWCQ("澄清与报价"),
    /**
     * 结果审批(决策)
     */
    JC("结果审批"),

    /**
     * 招标公告
     */
    ZBGG("招标公告"),

    /**
     * 中标结果公告
     */
    ZBJGGG("中标结果公告"),

    /**
     * 结果通知
     */
    JGTZ("结果通知");

    String zh;

    ProjectExecutionStepType(String zh) {
        this.zh = zh;
    }

    public String getZh() {
        return zh;
    }
}
