package com.realfinance.sofa.flow.domain;

import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * 业务
 * 需要审批的业务
 */
@Entity
@Table(name = "FLOW_BIZ")
public class Biz extends BaseEntity implements IEntity<Integer> {

    @Version
    private Long v;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 业务编码
     */
    @Column(nullable = false, unique = true, updatable = false)
    private String code;

    /**
     * 名称
     */
    @Column(nullable = false)
    private String name;

    /**
     * 备注
     */
    private String note;

    /**
     * 跳转url
     */
    private String url;

    /**
     * 回调类型
     */
    @Column(nullable = false)
    @Enumerated
    private CallbackType callbackType;

    /**
     * 业务回调URL
     * 通知业务系统流程状态
     */
    private String callbackUrl;

    @OneToMany(mappedBy = "biz")
    private Set<BizModel> bizModels;

    public Long getV() {
        return v;
    }

    public void setV(Long v) {
        this.v = v;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public CallbackType getCallbackType() {
        return callbackType;
    }

    public void setCallbackType(CallbackType callbackType) {
        this.callbackType = callbackType;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public Set<BizModel> getBizModels() {
        return bizModels;
    }

    public void setBizModels(Set<BizModel> bizModels) {
        this.bizModels = bizModels;
    }
}
