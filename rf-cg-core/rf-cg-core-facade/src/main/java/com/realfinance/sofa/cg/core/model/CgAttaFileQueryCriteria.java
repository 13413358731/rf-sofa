package com.realfinance.sofa.cg.core.model;

/**
 * 采购方案执行文件查询（评审会文件）
 */
public class CgAttaFileQueryCriteria {

    /**
     * 方案执行Id
     */
    private Integer projectExecution;

    /**
     * 文件类型
     */
    private String attSign;

    public Integer getProjectExecution() {
        return projectExecution;
    }

    public void setProjectExecution(Integer projectExecution) {
        this.projectExecution = projectExecution;
    }

    public String getAttSign() {
        return attSign;
    }

    public void setAttSign(String attSign) {
        this.attSign = attSign;
    }
}
