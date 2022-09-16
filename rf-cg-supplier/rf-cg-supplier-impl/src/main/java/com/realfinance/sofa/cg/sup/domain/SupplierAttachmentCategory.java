package com.realfinance.sofa.cg.sup.domain;

/**
 * 供应商附件类型
 */
public enum SupplierAttachmentCategory {

    /**
     * 财务报告盖章扫描件
     */
    FINANCIAL_REPORT("财务报告盖章扫描件"),
    /**
     * 营业执照正本盖章扫描件
     */
    BUSINESS_LICENSE("营业执照正本盖章扫描件"),
    /**
     * 各类资质证书扫描件
     */
    QUALIFICATION_CERTIFICATE("各类资质证书扫描件"),
    /**
     * 经营场地产权证明或租赁合同
     */
    LEASE_CONTRACT("经营场地产权证明或租赁合同"),
    /**
     * 企业商事主体登记及备案信息
     */
    MAIN_BUSINESS("企业商事主体登记及备案信息"),
    /**
     * 法人身份证正面
     */
    ID_CARD_FRONT("法人身份证正面"),
    /**
     * 法人身份证反面
     */
    ID_CARD_BACK("法人身份证反面"),
    /**
     * 其他
     */
    OTHER("其他"),
    /**
     * 报价文件
     */
    QUOTE("报价文件"),
    /**
     * 技术商务文件
     */
    TECH_BIZ("商务文件"),

    SUP_IN_PROMISE("供应商入库承诺函"),

    AUTHID_CARD_FRONT("授权人身份证正面"),

    AUTHID_CARD_BACK("授权人身份证反面"),

    PUR_AUTH_ATTORNEY("采购授权委托书"),

    QUALITY_AUTH("资质授权证书"),


    TECHNOLOGY("技术文件");

    private String zh;


    SupplierAttachmentCategory(String zh) {
        this.zh = zh;
    }

    public String getZh() {
        return zh;
    }
}
