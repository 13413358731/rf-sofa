package com.realfinance.sofa.cg.model.flow;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.realfinance.sofa.cg.model.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "流程业务对象")
public class BizVo extends BaseVo {
    @Schema(description = "ID")
    private Integer id;
    @Schema(description = "业务编码")
    private String code;
    @Schema(description = "名称")
    private String name;
    @Schema(description = "备注")
    private String note;
    @Schema(description = "跳转url")
    private String url;
    @Schema(description = "回调类型：RPC、HTTP")
    private String callbackType;
    @Schema(description = "业务回调URL")
    private String callbackUrl;

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

    public String getCallbackType() {
        return callbackType;
    }

    public void setCallbackType(String callbackType) {
        this.callbackType = callbackType;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }
}
