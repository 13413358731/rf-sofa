package com.realfinance.sofa.cg.sup.model;

public class CgBusinessReplyQueryCriteria {

    private Integer id;

    /**
     * 供应商ID
     */
    private Integer supplierId;

    /**
     *
     */
    private String businessProjectId;

    /**
     * 应答类型
     */
    private String replyType;

    /**
     * 需要报价
     */
    private Boolean needQuote;

    /**
     * 发布ID
     */
    private String releaseId;

    /**
     * 商务应答Id
     * @return
     */
    private Integer businessReplyId;

    /**
     * 发布ID
     */
    private String username;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getBusinessProjectId() {
        return businessProjectId;
    }

    public void setBusinessProjectId(String businessProjectId) {
        this.businessProjectId = businessProjectId;
    }

    public String getReplyType() {
        return replyType;
    }

    public void setReplyType(String replyType) {
        this.replyType = replyType;
    }

    public Boolean getNeedQuote() {
        return needQuote;
    }

    public void setNeedQuote(Boolean needQuote) {
        this.needQuote = needQuote;
    }

    public String getReleaseId() {
        return releaseId;
    }

    public void setReleaseId(String releaseId) {
        this.releaseId = releaseId;
    }

    public Integer getBusinessReplyId() {
        return businessReplyId;
    }

    public void setBusinessReplyId(Integer businessReplyId) {
        this.businessReplyId = businessReplyId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
